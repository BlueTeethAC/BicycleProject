<template>

  <div>

    <!-- 按钮 -->
    <el-button type="info" style="float: right;margin-right: 10px" v-on:click="selectListByStatus(input1)">搜索维修清单信息</el-button>

    <!-- 多选框 -->
    <el-select v-model="input1" style="width: 200px;float: right;margin-right: 0px" placeholder="请选择清单状态">
      <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value">
      </el-option>
    </el-select>

    <!-- 搜索框 -->
    <!--<el-input id="select1" style="width: 200px;float: right;margin-right: 0px"  v-model="input1" placeholder="请选择维修单状态"></el-input>-->

    <hr style="float: right;width: 1600px;margin-top: 10px;margin-bottom: 10px"/>

    <!--表格部分-->
    <div id="table1">
      <el-table
        :header-cell-style="{background:'#909399',color:'#ffd04b'}"
        :data="repairList"
        :row-style="{background:'#E4E7ED'}"
        style="width: 100%">

        <el-table-column
          fixed
          prop="repairId"
          label="维修单Id"
          width="150px"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="userId1"
          label="申请维修的用户id"
          width="150px"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="username1"
          label="申请维修的用户名"
          width="250px"
          align="center"
        >
        </el-table-column>

        <el-table-column
          fixed
          prop="userId2"
          label="受理维修的管理员的id"
          width="150px"
          align="center"
          v-if="false"
        >
        </el-table-column>

        <el-table-column
          prop="username2"
          label="受理人"
          width="250px"
          align="center"
        >
        </el-table-column>

        <!-- :formatter="rounding"：绑定下方展示两位小数的方法 -->
        <el-table-column
          prop="date"
          label="维修单申请日期"
          width="200px"
          align="center"
        >
        </el-table-column>

        <!-- :formatter="rounding"：绑定下方展示两位小数的方法 -->
        <el-table-column
          prop="sumPrice"
          label="维修单总价"
          width="150px"
          align="center"
          :formatter="rounding"
        >
        </el-table-column>

        <el-table-column
          prop="repairStatus"
          label="维修单状态"
          width="150px"
          align="center"
        >
          <!--在 el-table-column 中做判断-->
          <template slot-scope="itemsInfo">
            <span v-show="itemsInfo.row.repairStatus==0">申请中</span>
            <span v-show="itemsInfo.row.repairStatus==1">已撤销</span>
            <span v-show="itemsInfo.row.repairStatus==2">已受理</span>
            <span v-show="itemsInfo.row.repairStatus==3">维修中</span>
            <span v-show="itemsInfo.row.repairStatus==4">待领取</span>
            <span v-show="itemsInfo.row.repairStatus==5">已领取</span>
          </template>
        </el-table-column>

        <el-table-column
          fixed="right"
          label="操作"
          width="200px"
          align="center"
        >
          <template slot-scope="scope">
            <el-button type="primary" size="small" v-on:click="$router.push({name:'repairListInfo',params:{repairId:scope.row.repairId}})">查看</el-button>
            <el-button type="success" size="small" v-on:click="goUpdate(scope.row.repairId,userId,username,2)" v-if="scope.row.repairStatus==0">受理</el-button>
            <el-button type="success" size="small" v-on:click="goUpdate(scope.row.repairId,null,null,3)" v-if="scope.row.repairStatus==2">进入维修</el-button>
            <el-button type="success" size="small" v-on:click="goUpdate(scope.row.repairId,null,null,4)" v-if="scope.row.repairStatus==3">维修完毕</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!--  撤销用的弹窗  -->
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible1"
      width="30%">
      <span>{{updateMessage}}</span>
      <span slot="footer" class="dialog- footer">
        <el-button type="primary" @click="confirmButton(repairId1,userId1,username1,repairStatus1)">确定</el-button>
        <el-button type="primary" @click="cancelButton()">取消</el-button>
      </span>
    </el-dialog>

  </div>

</template>

<script>
//维修清单承接
import axios from "axios";
import vuex from "@/vuex"

export default {
  name: "repairServiceAdmin",
  data(){
    return{
      updateMessage:"请问是否确认要撤销维修单",//弹窗文本
      input1:null,
      repairList:null,
      username:vuex.state.username,
      userId:vuex.state.userId,
      repairId1:null,
      userId1:null,
      username1:null,
      repairStatus1:null,
      options: [{
        value: null,
        label: '全部'
      },{
        value: '0',
        label: '申请中'
      }, {
        value: '1',
        label: '已撤销'
      }, {
        value: '2',
        label: '已受理'
      }, {
        value: '3',
        label: '维修中'
      }, {
        value: '4',
        label: '待领取'
      }
      ],
    }
  },
  //进入该路由时查询数据
  async beforeRouteEnter(to,from,next){

    let repairList;

    await axios({
      method:"get",
      params:{
      },
      // url:"http://127.0.0.1:8081/Bicycle/repair/selectRepairOrdersByInfo"
      url:"/BicycleProject/Bicycle/repair/selectRepairOrdersByInfo"
    }).then(response=>{
      repairList=response.data;
      console.log(repairList)
    })

    next((vm)=>{
      if (vuex.state.username == null){
        vm.$router.push("/loginView/login")
      };
      vm.repairList=repairList;
    })
  },

  methods:{
    //让展示的数据保留两位小数用
    rounding(row,column) {
      return parseFloat(row[column.property]).toFixed(2)
    },
    //查询清单（按照状态）
    async selectListByStatus(input){
      await this.axios({
        method:"get",
        params:{
          repairStatus:input
        },
        // url:"http://127.0.0.1:8081/Bicycle/repair/selectRepairOrdersByInfo"
        url:"/BicycleProject/Bicycle/repair/selectRepairOrdersByInfo"
      }).then(response=>{
        this.repairList=response.data
      })
    },

    //把要修改的值存入内存并开启弹窗
    goUpdate(repairId,userId,username,repairStatus){
      this.repairId1=repairId;
      this.userId1=userId;
      this.username1=username;
      this.repairStatus1=repairStatus;
      //开启弹窗
      this.updateStatus(repairStatus);
    },

    //受理方法 进入维修 维修完毕
    async acceptance(repairId,userId,username,repairStatus){
      this.axios({
        method:"post",
        params:{
          repairId:repairId,
          userId2:userId,
          username2:username,
          repairStatus:repairStatus
        },
        // url:"http://127.0.0.1:8081/Bicycle/repair/updateRepairOrders"
        url:"/BicycleProject/Bicycle/repair/updateRepairOrders"
      }).then(response=>{
        let i = response.data;
        if (i === 1){
          console.log("修改成功!");
          this.selectListByStatus(null);
        }else {
          alert("修改失败!");
        }
      });
    },

    //按钮触发弹窗
    updateStatus(repairStatus){
      //设置文本
      if (repairStatus===2){
        this.updateMessage="请确认是否要受理";
      }else if (repairStatus===3){
        this.updateMessage="请确认是否已进入维修";
      }else {
        this.updateMessage="请确认是否已经维修完成";
      }
      //弹出窗口
      this.dialogVisible1=true;
    },

    //取消按钮
    cancelButton(){
      //重新初始化数据
      this.repairId1=null;
      this.userId1=null;
      this.username1=null;
      this.repairStatus1=null;
      this.updateMessage="";
      //关闭窗口
      this.dialogVisible1=false;
    },

    //确认按钮
    async confirmButton(repairId,userId,username,repairStatus){
      await this.acceptance(repairId,userId,username,repairStatus);
      //关闭弹窗
      this.dialogVisible1=false;
      await selectListByStatus(null);
    }


  }
}
</script>

<style scoped>

.div_tip{
  margin-left: -60px;
  margin-bottom: 10px;
  font-family: "Hiragino Sans GB";
  color: black;
}

</style>
