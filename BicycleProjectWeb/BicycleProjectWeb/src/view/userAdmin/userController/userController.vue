<template>

  <div id="body1">


    <!-- 按钮 -->
    <el-button type="info" style="float: right;margin-right: 10px" v-on:click="getUserInfo(input1)">搜索用户信息</el-button>

    <!-- 搜索框 -->
    <el-input id="select1" style="width: 200px;float: right;margin-right: 30px"  v-model="input1" placeholder="请输入用户名"></el-input>

    <hr style="float: right;width: 1600px;margin-top: 10px;margin-bottom: 10px"/>

    <!--表格部分-->
    <div id="table1">
      <el-table
        :header-cell-style="{background:'#909399',color:'#ffd04b'}"
        :data="userInfo"
        :row-style="{background:'#E4E7ED'}"
        style="width: 100%">
        <el-table-column
          fixed
          prop="userId"
          label="用户id"
          width="250px"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="username"
          label="用户名"
          width="300px"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="phoneNumber"
          label="电话号码"
          width="500"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="role"
          label="角色"
          width="200"
          align="center"
        >
          <!--在 el-table-column 中做判断-->
          <template slot-scope="userInfo">
            <span v-show="userInfo.row.role==0">普通用户</span>
            <span v-show="userInfo.row.role==1">管理员</span>
            <span v-show="userInfo.row.role==2">最高管理员</span>
          </template>
        </el-table-column>

        <el-table-column
          prop="status"
          label="状态"
          width="150"
          align="center"
        >
          <!--在 el-table-column 中做判断-->
          <template slot-scope="userInfo">
            <span v-show="userInfo.row.status==0">未启用</span>
            <span v-show="userInfo.row.status==1">启用中</span>
          </template>
        </el-table-column>

        <el-table-column
          fixed="right"
          label="操作"
          width="150px"
          align="center"
        >
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row)" type="success" size="small" v-if="scope.row.role != 2" v-on:click="$router.push({name:'updateUser',params:{userId1:scope.row.userId,username1:scope.row.username,phoneNumber1:scope.row.phoneNumber,role1:scope.row.role,status1:scope.row.status}})">修改</el-button>
            <!-- 启用按钮 -->
            <el-button type="danger" size="small" v-if="scope.row.status==1 && scope.row.role != 2"  v-on:click="updaeteButton1(scope.row.userId,scope.row.username,0)">禁用</el-button>
            <!-- 禁用按钮 -->
            <el-button type="primary" size="small" v-if="scope.row.status==0 && scope.row.role != 2" v-on:click="updaeteButton1(scope.row.userId,scope.row.username,1)">启用</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog
      title="提示"
      :visible.sync="dialogVisible1"
      width="30%">
      <span>{{updateMessage}}</span>
      <span slot="footer" class="dialog- footer">
        <el-button type="primary" @click="updateConfirm()">确定</el-button>
        <el-button type="primary" @click="updateCancel()">取消</el-button>
      </span>
    </el-dialog>

  </div>

</template>

<script>

import axios from "axios";

//用户管理
export default {
  name: "userController",
  data() {
    return {
      input1:'',//搜索框的数据
      updateMessage:"",//提示框文本
      userInfo:null,
      updateuserId:null,//用于存放修改用户状态时暂存用户id的属性
      updateStatus:null,//用于暂时存放要修改状态
      updateUsername:null,//用于暂时存放要修改的状态
      user:{ //用户信息
        userId:null,
        username:null,
        phoneNumber:null,
        role:null,
        status:null
      }
    }
  },
  methods: { //方法
    //查询用户信息
    async getUserInfo(input1){
      axios({
        method:"get",
        params:{ //参数
          username:this.input1
        },
        // url:"http://127.0.0.1:8081/Bicycle/user/selectUserByName"
        url:"/BicycleProject/Bicycle/user/selectUserByName"
      }).then(response=>{
        this.user = response.data;
        this.userInfo = this.user;
      })
    },

    //启用与禁用按钮的方法 userId：用户id，status：要修改成的状态
    updaeteButton1(userId,username,status){
      this.updateuserId = userId;//给 this.updateuserId 赋值
      this.updateUsername = username;//给 this.updateuserId 赋值
      this.updateStatus = status;//给 this.updateStatus 赋值
      //开启弹窗
      if (status === 1){ //启用用户
        this.updateMessage="请确认启用用户"
      }else {//禁用用户
        this.updateMessage="请确认禁用用户"
      }
      this.dialogVisible1 = true;
    },

    //修改确认弹窗中的确认按钮
    async updateConfirm(){
      //执行修改用户状态方法
      await this.updateUserStatus(this.updateuserId,this.updateUsername,this.updateStatus);
      //修改后初始化 updateuserId 和 updateStatus
      this.updateuserId=null;
      this.updateUsername=null;
      this.updateStatus=null;
      this.dialogVisible1=false;//关闭弹窗
      //初始化弹窗信息
      this.updateMessage="";
      //刷新一下查询数据
      this.getUserInfo("");
    },

    //修改确认弹窗中的取消按钮
    updateCancel(){
      //初始化 updateuserId 和 updateStatus
      this.updateuserId=null;
      this.updateUsername=null;
      this.updateStatus=null;
      //关闭弹窗
      this.dialogVisible1=false;
      //初始化弹窗信息
      this.updateMessage="";
    },

    //修改用户状态
    async updateUserStatus(userId1,username1,status1){
      let i;
      await this.axios({
        method:"post",
        params:{
          userId:userId1,
          useranme:username1,
          status:status1,
        },
        // url:"http://127.0.0.1:8081/Bicycle/user/updateUser"
        url:"/BicycleProject/Bicycle/user/updateUser"
      }).then(response=>{
        i=response.data;
        if (i === 1){
          console.log("修改成功");
        }
      })
    }
  },

  //进入路由时查询数据
  async beforeRouteEnter(to,from,next){ //在进入路由执行

    let userData;

    await axios({
      method:"get",
      params:{ //参数
      },
      // url:"http://127.0.0.1:8081/Bicycle/user/selectUserByName"
      url:"/BicycleProject/Bicycle/user/selectUserByName"
    }).then(response=>{
      userData = response.data;
    })

    //对该vue中的data操作的话就在 next 里操作，vm就相当于平时的 this
    next((vm)=>{
      vm.userInfo = userData;
    });
  }
}

</script>

<style scoped>

#select1{
  width: 100px;
}
</style>
