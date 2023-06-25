<template>

  <div id="body1">

    <!-- 按钮 -->
    <el-button type="primary" icon="el-icon-plus" round style="float: left" v-on:click="$router.push('/userAdminView/repairItemsController/addRepairItems')">新增维修项目</el-button>

    <!-- 按钮 -->
    <el-button type="info" style="float: right;margin-right: 10px" v-on:click="getrepairItems(input1)">搜索维修项目信息</el-button>

    <!-- 搜索框 -->
    <el-input id="select1" style="width: 200px;float: right;margin-right: 0px"  v-model="input1" placeholder="请输入维修项目名称"></el-input>

    <hr style="float: right;width: 1600px;margin-top: 10px;margin-bottom: 10px"/>

    <!--表格部分-->
    <div id="table1">
      <el-table
        :header-cell-style="{background:'#909399',color:'#ffd04b'}"
        :data="repairItems"
        :row-style="{background:'#E4E7ED'}"
        style="width: 100%">
        <el-table-column
          fixed
          prop="itemsId"
          label="维修项目Id"
          width="250px"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="itemsName"
          label="维修项目名"
          width="450px"
          align="center"
        >
        </el-table-column>

        <!-- :formatter="rounding"：绑定下方展示两位小数的方法 -->
        <el-table-column
          prop="itemsPrice"
          label="维修项目价格"
          width="450px"
          align="center"
          :formatter="rounding"
        >
        </el-table-column>

        <el-table-column
          prop="status"
          label="状态"
          width="150"
          align="center"
        >
          <!--在 el-table-column 中做判断-->
          <template slot-scope="itemsInfo">
            <span v-show="itemsInfo.row.status==0">未启用</span>
            <span v-show="itemsInfo.row.status==1">启用中</span>
          </template>
        </el-table-column>

        <el-table-column
          fixed="right"
          label="操作"
          width="150px"
          align="center"
        >
          <template slot-scope="scope">
            <el-button type="success" size="small" v-if="scope.row.role != 2" v-on:click="$router.push({name:'updateRepairItems',params:{repairItemsId:scope.row.itemsId,repairItemsName:scope.row.itemsName,repairItemsPrice:scope.row.itemsPrice,repairItemsStatus:scope.row.status}})">修改</el-button>
            <!-- 启用按钮 -->
            <el-button type="danger" size="small" v-if="scope.row.status==1"  v-on:click="statusButton(scope.row.itemsId,0)">禁用</el-button>
            <!-- 禁用按钮 -->
            <el-button type="primary" size="small" v-if="scope.row.status==0"  v-on:click="statusButton(scope.row.itemsId,1)">启用</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!--  禁用与启用的弹窗  -->
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

    <!--  新增维修项目的弹窗  -->
    <!--  修改维修项目的弹窗  -->
    <router-view></router-view>

  </div>

</template>

<script>
//维修项目管理
import axios from "axios";
import addRepairItems from "@/view/userAdmin/repairItemsController/components/addRepairItems.vue";

export default {
  components:{
    addRepairItems
  },
  name: "repairitemsController",
  data(){
    return{
      repairItemsId1:null,
      repairItemsName1:null,
      repairItemsPrice1:null,
      repairItemsStatus1:null,
      //修改状态提示框状态
      dialogVisible1:false,
      //搜索文本
      input1:null,
      //弹窗文本
      updateMessage:null,
      //接收的维修项目信息
      repairItems:null,
      //新增提示框状态
      dialogVisible2:false,
      //修改提示框状态
      dialogVisible3:false
    }
  },
  methods:{
    //让展示的数据保留两位小数用
    rounding(row,column) {
      return parseFloat(row[column.property]).toFixed(2)
    },
    //搜索维修项目信息
    async getrepairItems(input){
      this.axios({
        method:"get",
        params:{
          itemsName:input
        },
        // url:"http://127.0.0.1:8081/Bicycle/repair/selectRepairItemsByInfo"
        url:"/BicycleProject/Bicycle/repair/selectRepairItemsByInfo"
      }).then(response=>{
        this.repairItems=response.data;
      });
    },

    //禁用启用按钮功能（弹窗出现，询问是否确认修改）
    statusButton(itemsId,itemsStatus){
      //赋值
      this.repairItemsId1=itemsId;
      this.repairItemsStatus1=itemsStatus;
      //修改提示文本
      this.updateMessage = "确认是否修改状态";
      //开启弹窗
      this.dialogVisible1=true;
    },

    //确认修改状态按钮
    async confirmButton(){
      //执行修改状态
      await this.updateRepairItemsStatus(null,null,this.repairItemsStatus1,this.repairItemsId1);
      //重新初始化值
      this.repairItemsId1=null;
      this.repairItemsStatus1=null;
      //关闭弹窗
      this.dialogVisible1=false;
      //再次搜错
      await this.getrepairItems(null);
    },

    //取消修改状态按钮
    cancelButton(){
      //重新初始化值
      this.repairItemsId1=null;
      this.repairItemsStatus1=null;
      //关闭弹窗
      this.dialogVisible1=false;
    },

    //修改维修项目
    async updateRepairItemsStatus(itemsName,itemsPrice,status,itemsId){
      let i;
      await this.axios({
        method:"get",
        params:{
          itemsName:itemsName,
          itemsPrice:itemsPrice,
          status:status,
          itemsId:itemsId
        },
        // url:"http://127.0.0.1:8081/Bicycle/repair/updateRepairItems"
        url:"/BicycleProject/Bicycle/repair/updateRepairItems"
      }).then(response=>{
        i=response.data;
        if (i === 1){
          console.log("修改成功");
        }else {
          console.log("修改失败");
        }
      });
    }
  },
  //进入路由时查询维修项目数据
  async beforeRouteEnter(to,from,next){ //在进入路由执行

    let repairItems1;

    await axios({
      method:"get",
      params:{ //参数
      },
      // url:"http://127.0.0.1:8081/Bicycle/repair/selectRepairItemsByInfo"
      url:"/BicycleProject/Bicycle/repair/selectRepairItemsByInfo"
    }).then(response=>{
      repairItems1 = response.data;
    })

    //对该vue中的data操作的话就在 next 里操作，vm就相当于平时的 this
    next((vm)=>{
      vm.repairItems = repairItems1;
    });
  }
}
</script>

<style scoped>

#select1{
  width: 100px;
}

</style>
