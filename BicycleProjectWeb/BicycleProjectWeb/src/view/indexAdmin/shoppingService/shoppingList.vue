<template>

  <div>

    <!-- 购物车标题 -->
    <div style="float: left;margin-left: 10px;margin-top: -25px;margin-bottom: -25px"><h1>购物记录</h1></div>

    <!-- 按钮 -->
    <el-button type="info" style="float: right;margin-right: 10px" v-on:click="getShoppingList(userId1,input1)">搜索清单信息</el-button>

    <!-- 多选框 -->
    <el-select v-model="input1" style="width: 200px;float: right;margin-right: 10px" placeholder="请选择清单状态">
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
        :data="shoppingList"
        :row-style="{background:'#E4E7ED'}"
        style="width: 90%;margin-left: 5%;margin-right: 5%">

        <el-table-column
          prop="saleId"
          label="消费清单Id"
          width="250px"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="userId"
          label="用户Id"
          width="200px"
          align="center"
          v-if="false"
        >
        </el-table-column>

        <el-table-column
          fixed
          prop="username"
          label="申请者用户名"
          width="250px"
          align="center"
          v-if="false"
        >
        </el-table-column>

        <el-table-column
          prop="detailsId"
          label="消费清单明细Id"
          width="350px"
          align="center"
          v-if="false"
        >
        </el-table-column>

        <!-- :formatter="rounding"：绑定下方展示两位小数的方法 -->
        <el-table-column
          prop="sumPrice"
          label="总价"
          width="300px"
          align="center"
          :formatter="rounding"
        >
        </el-table-column>

        <el-table-column
          prop="saleTime"
          label="消费清单申请日期"
          width="300px"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="listStatus"
          label="消费单状态"
          width="250px"
          align="center"
        >
          <!--在 el-table-column 中做判断-->
          <template slot-scope="itemsInfo">
            <span v-show="itemsInfo.row.listStatus==0">申请中</span>
            <span v-show="itemsInfo.row.listStatus==1">备货中</span>
            <span v-show="itemsInfo.row.listStatus==2">待领取</span>
            <span v-show="itemsInfo.row.listStatus==3">已完成</span>
            <span v-show="itemsInfo.row.listStatus==4">已撤销</span>
          </template>
        </el-table-column>

        <el-table-column
          fixed="right"
          label="操作"
          width="200px"
          align="center"
        >
          <template slot-scope="scope">
            <el-button type="warning" size="small" v-if="scope.row.listStatus === 0" v-on:click="onButton(scope.row.saleId,0)">撤销</el-button>
            <el-button type="success" size="small" v-if="scope.row.listStatus === 2" v-on:click="onButton(scope.row.saleId,2)">已领取</el-button>
            <el-button type="primary" size="small" v-on:click="$router.push({name:'shoppingListDetails',params:{detailsId:scope.row.detailsId}})">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!--  修改用的弹窗  -->
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible1"
      width="30%">
      <span>{{updateMessage}}</span>
      <span slot="footer" class="dialog- footer">
        <el-button type="primary" @click="confirmButton(saleId1,i)">确定</el-button>
        <el-button type="primary" @click="cancelButton()">取消</el-button>
      </span>
    </el-dialog>

  </div>

</template>

<script>
//引入 vuex 的配置
import vuex from "@/vuex"
import axios from "axios";

//消费清单列表
export default {
  name: "shoppingList",
  data(){
    return{
      i:null,//判断是撤销还是领取用的
      saleId1:null,//消费清单Id
      userId1:null,
      updateMessage:"",
      dialogVisible1:false,
      shoppingList:null,
      input1:null,
      options: [{
        value: null,
        label: '全部'
      },{
        value: '0',
        label: '申请中'
      }, {
        value: '1',
        label: '备货中'
      }, {
        value: '2',
        label: '待领取'
      }, {
        value: '3',
        label: '已完成'
      },{
        value:'4',
        label:'已撤销'
      }],
    }
  },
  methods:{
    //让展示的数据保留两位小数用
    rounding(row,column) {
      return parseFloat(row[column.property]).toFixed(2)
    },
    //搜索清单信息
    async getShoppingList(userId,listStats){
      await this.axios({
        method:"get",
        params:{
          userId:userId,
          listStatus:listStats
        },
        url:"http://127.0.0.1:8081/Bicycle/commodity/selectSaleListByInfo"
      }).then(response=>{
        this.shoppingList = response.data;
      })
    },
    //开启修改弹窗
    onButton(saleId,i){
      if (i===0){//i为0就是撤销
        this.i=4;
        this.updateMessage="请问是否确认撤销！";
      }else if(i===2){//i为2就是领取
        this.i=3;
        this.updateMessage="请问是否确认领取！";
      }
      this.saleId1=saleId;//获得消费清单Id
      this.dialogVisible1=true;//开启弹窗
    },
    //确认修改
    async confirmButton(saleId,listStatus){
      //执行修改状态
      await this.updateShoppingList(saleId,listStatus);
      //重置状态
      this.i=null;//重置
      this.updateMessage="";//重置
      this.dialogVisible1=false;//关闭弹窗
      this.$router.go(0);//刷新页面
    },

    //取消修改
    cancelButton(){
      this.i=null;//重置
      this.updateMessage="";//重置
      this.dialogVisible1=false;//关闭弹窗
    },

    //修改清单状态
    async updateShoppingList(saleId,listStatus){
      this.axios({
        method:"post",
        params:{
          saleId:saleId,
          listStatus:listStatus
        },
        url:"http://127.0.0.1:8081/Bicycle/commodity/updateStatus"
      }).then(response=>{
        let i=response.data;
        if (i === 1){
          console.log("修改成功！");
        }else{
          alert("修改失败！");
        }
      })
    }
  },
  //路由守卫
  async beforeRouteEnter(to,from,next){
    next(async (vm) => {
      //查询消费清单数据
      await axios({
        methods: "get",
        params: {
          userId: vuex.state.userId
        },
        url: "http://127.0.0.1:8081/Bicycle/commodity/selectSaleListByInfo"
      }).then(response => {
        vm.shoppingList=response.data;//获得数据
        vm.userId1 = vuex.state.userId;
      })
    })
  }
}
</script>

<style scoped>

</style>
