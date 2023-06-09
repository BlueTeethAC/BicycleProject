<template>

  <div>

    <!-- 按钮 -->
    <el-button type="primary" icon="el-icon-plus" round style="float: left" v-on:click="$router.push('/userAdmin/addRepairList')">发送维修申请单</el-button>

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
          width="200px"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="userId1"
          label="申请维修的用户id"
          width="450px"
          align="center"
          v-if="false"
        >
        </el-table-column>

        <el-table-column
          prop="username1"
          label="申请维修的用户名"
          width="450px"
          align="center"
          v-if="false"
        >
        </el-table-column>

        <el-table-column
          fixed
          prop="userId2"
          label="受理维修的管理员的id"
          width="250px"
          align="center"
          v-if="false"
        >
        </el-table-column>

        <el-table-column
          prop="username2"
          label="受理人"
          width="350px"
          align="center"
        >
        </el-table-column>

        <!-- :formatter="rounding"：绑定下方展示两位小数的方法 -->
        <el-table-column
          prop="date"
          label="维修单申请日期"
          width="300px"
          align="center"
        >
        </el-table-column>

        <!-- :formatter="rounding"：绑定下方展示两位小数的方法 -->
        <el-table-column
          prop="sumPrice"
          label="维修单总价"
          width="200px"
          align="center"
          :formatter="rounding"
        >
        </el-table-column>

        <el-table-column
          prop="repairStatus"
          label="维修单状态"
          width="200"
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
            <el-button type="warning" size="small" v-if="scope.row.repairStatus === 0 || scope.row.repairStatus === 2" v-on:click="quashButton(scope.row.repairId)">撤销</el-button>
            <el-button type="success" size="small" v-if="scope.row.repairStatus === 4" v-on:click="getButton(scope.row.repairId)">已领取</el-button>
            <el-button type="primary" size="small" v-on:click="$router.push({name:'repairListInfo',params:{repairId:scope.row.repairId}})">查看</el-button>
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
        <el-button type="primary" @click="confirmButton(i)">确定</el-button>
        <el-button type="primary" @click="cancelButton()">取消</el-button>
      </span>
    </el-dialog>

  </div>

</template>

<script>
//引入 vuex 的配置
import vuex from "@/vuex"
import axios from "axios";

export default {
  name: "repairService",
  data(){
    return{
      i:null,//用于确认是撤销还是领取 0:撤销  1:领取
      updateRepairId:null,//要撤销的未维修单Id
      updateMessage:"请问是否确认要撤销维修单",//弹窗文本
      dialogVisible1:false,//弹窗设置
      input1:null,//输入框
      repairList:null,
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
      },{
        value: '5',
        label: '待领取'
      }
      ],
    }
  },
  methods:{
    //领取按钮
    getButton(repairId){
      this.i=1;
      //选中需要领取的维修单id
      this.updateRepairId=repairId;
      this.updateMessage="请问是否已经领取";
      this.dialogVisible1=true;
    },

    //撤销按钮
    quashButton(repairId){
      this.i=0;
      //选中需要撤销的维修单id
      this.updateRepairId=repairId;
      this.updateMessage="请问是否确认要撤销维修单";
      this.dialogVisible1=true;
    },

    //弹窗的取消按钮
    cancelButton(){
      this.i=null;
      this.updateMessage="";
      //取消赋值
      this.updateRepairId=null;
      this.dialogVisible1=false;
    },

    //确认撤销按钮
    async confirmButton(i){
      if (i === 0){
        //执行撤销
        await this.quashRepairOrder(this.updateRepairId,1);
      }else if (i === 1){
        //执行领取
        await this.quashRepairOrder(this.updateRepairId,5);
      }
      //取消赋值
      this.i=null;
      this.updateRepairId=null;
      //刷关闭弹窗
      this.dialogVisible1=false;
      //再次查询
      //this.$router.go(0);
      this.selectListByStatus(null);
    },

    //撤销维修单方法
    async quashRepairOrder(repairId,repairStatus){
      let i;
      await this.axios({
        method:"post",
        params:{
          repairId:repairId,
          repairStatus:repairStatus,
        },
        url:"http://127.0.0.1:8081/Bicycle/repair/updateRepairOrders"
      }).then(response=>{
        i=response.data;
        if (i === 1){
          console.log("修改成功");
        }else {
          alert("修改出现异常！");
        }
      });
    },

    //让展示的数据保留两位小数用
    rounding(row,column) {
      return parseFloat(row[column.property]).toFixed(2)
    },

    //查询清单（按照状态）
    async selectListByStatus(input){
      await this.axios({
        method:"get",
        params:{
          userId1:vuex.state.userId,
          repairStatus:input
        },
        url:"http://127.0.0.1:8081/Bicycle/repair/selectRepairOrdersByInfo"
      }).then(response=>{
        this.repairList=response.data
      })
    }

  },

  //进入该路由时查询数据
  async beforeRouteEnter(to,from,next){

    let repairList;

    await axios({
      method:"get",
      params:{
        userId1:vuex.state.userId
      },
      url:"http://127.0.0.1:8081/Bicycle/repair/selectRepairOrdersByInfo"
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
