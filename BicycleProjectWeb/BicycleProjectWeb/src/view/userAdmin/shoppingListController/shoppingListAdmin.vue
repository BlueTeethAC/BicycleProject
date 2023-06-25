<template>

  <div>

    <!-- 按钮 -->
    <el-button type="info" style="float: right;margin-right: 10px" v-on:click="selectListByStatus(input1)">搜索维修清单信息</el-button>

    <!-- 多选框 -->
    <el-select v-model="input1" style="width: 200px;float: right;margin-right: 5px" placeholder="请选择清单状态">
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
        :data="saleListInfo"
        :row-style="{background:'#E4E7ED'}"
        style="width: 100%">

        <el-table-column
          fixed
          prop="saleId"
          label="消费单Id"
          width="200px"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="userId"
          label="消费者Id"
          width="150px"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="username"
          label="消费者用户名"
          width="250px"
          align="center"
        >
        </el-table-column>

        <el-table-column
          fixed
          prop="detailsId"
          label="消费清单Id"
          width="150px"
          align="center"
          v-if="false"
        >
        </el-table-column>

        <el-table-column
          prop="sumPrice"
          label="清单总价"
          width="250px"
          align="center"
          :formatter="rounding"
        >
        </el-table-column>

        <!-- :formatter="rounding"：绑定下方展示两位小数的方法 -->
        <el-table-column
          prop="saleTime"
          label="消费清单日期"
          width="200px"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="listStatus"
          label="维修单状态"
          width="150px"
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
            <el-button type="primary" size="small" v-on:click="$router.push({name:'shoppingListDetails',params:{detailsId:scope.row.detailsId}})">查看</el-button>
            <el-button type="success" size="small" v-on:click="updateStatusButton(0,scope.row.saleId)" v-if="scope.row.listStatus==0">备货</el-button>
            <el-button type="success" size="small" v-on:click="updateStatusButton(1,scope.row.saleId)" v-if="scope.row.listStatus==1">备货完毕</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!--  更改状态用的弹窗  -->
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible1"
      width="30%">
      <span>{{updateMessage}}</span>
      <span slot="footer" class="dialog- footer">
        <el-button type="primary" @click="confirmButton()">确定</el-button>
        <el-button type="primary" @click="cancelButton()">取消</el-button>
      </span>
    </el-dialog>

  </div>

</template>


<script>
import axios from "axios";
import vuex from "@/vuex"

//消费清单处理
export default {
  name: "shoppingListAdmin",
  data(){
    return{
      updateMessage:"",
      dialogVisible1:false,
      saleListInfo:null,
      nowStatus:null,
      nowSaleId:null,
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
      }, {
        value: '4',
        label: '已撤销'
      }],
    }
  },
  methods:{
    //让展示的数据保留两位小数用
    rounding(row,column) {
      return parseFloat(row[column.property]).toFixed(2);
    },
    //修改状态
    async updateStatue(saleId,listStatus){
      //console.log(saleId);
      //console.log(listStatus);
      await this.axios({
        method:"post",
        params:{
          saleId:saleId,
          listStatus:listStatus
        },
        // url:"http://127.0.0.1:8081/Bicycle/commodity/updateStatus"
        url:"/BicycleProject/Bicycle/commodity/updateStatus"
      }).then(response=>{
        let i = response.data;
        console.log(i);
        if (i === 0){
          alert("修改失败！");
        }else {
          console.log("修改成功！");
        }
      })
    },
    //取消按钮
    cancelButton(){
      this.nowSaleId=null;
      this.updateMessage="";
      this.dialogVisible1=false;
    },
    //确认按钮
    async confirmButton(){
      await this.updateStatue(this.nowSaleId,this.nowStatus);
      this.$router.go(0);
      this.dialogVisible1 = false;
    },
    //开启修改状态弹窗
    async updateStatusButton(i,saleId){
      this.nowSaleId=saleId;
      if (i===0){
        this.updateMessage="请确认是否备货";
        this.nowStatus=1;
      }else if(i===1){
        this.updateMessage="请确认是否备货完毕";
        this.nowStatus=2;
      };
      this.dialogVisible1=true;//开启弹窗
    },
    //查询消费清单数据
    async selectListByStatus(listStatus){
      await axios({
        methods:"get",
        params:{
          listStatus:listStatus
        },
        // url:"http://127.0.0.1:8081/Bicycle/commodity/selectSaleListByInfo"
        url:"/BicycleProject/Bicycle/commodity/selectSaleListByInfo"
      }).then(response=>{
        this.saleListInfo=response.data;
      })
    }
  },
  //路由守卫
  async beforeRouteEnter(to,from,next){
    next(async (vm)=>{
      await axios({
        methods: "post",
        params:{
        },
        // url:"http://127.0.0.1:8081/Bicycle/commodity/selectSaleListByInfo"
        url:"/BicycleProject/Bicycle/commodity/selectSaleListByInfo"
      }).then(response=>{
        vm.saleListInfo=response.data;//查询到数据
      })
    })
  }
}
</script>

<style scoped>

</style>
