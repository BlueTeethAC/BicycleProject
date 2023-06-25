<template>

  <div>

    <!--<add-commodity></add-commodity>-->
    <!-- 按钮 -->
    <el-button type="primary" icon="el-icon-plus" round style="float: left" v-on:click="$router.push('/userAdmin/commodityController/addCommodity')">新增商品</el-button>

    <!-- 按钮 -->
    <el-button type="info" v-model="input1" style="float: right;margin-right: 10px" v-on:click="getCommodityInfo(input1)">搜索商品信息</el-button>

    <!-- 搜索框 -->
    <el-input id="select1" style="width: 200px;float: right;margin-right: 0px"  v-model="input1" placeholder="请输入商品名称"></el-input>

    <hr style="float: right;width: 1600px;margin-top: 10px;margin-bottom: 10px"/>

    <!--表格部分-->
    <div id="table1">
      <el-table
        :header-cell-style="{background:'#909399',color:'#ffd04b'}"
        :data="commodityInfo"
        :row-style="{background:'#E4E7ED'}"
        style="width: 100%">
        <el-table-column
          fixed
          prop="commodityId"
          label="商品Id"
          width="150px"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="commodityName"
          label="商品名称"
          width="350px"
          align="center"
        >
        </el-table-column>

        <!-- style="display: none"：隐藏 -->
        <el-table-column
          prop="commodityType"
          label="商品类型"
          width="250px"
          align="center"
          style="display: none"
          v-if="false"
        >
        </el-table-column>

        <!-- style="display: none"：隐藏 -->
        <el-table-column
          prop="pictureId"
          label="图片id"
          width="250px"
          align="center"
          style="display: none"
          v-if="false"
        >
        </el-table-column>

        <!-- style="display: none"：隐藏 -->
        <el-table-column
          prop="introduce"
          label="商品简介"
          width="250px"
          align="center"
          style="display: none"
          v-if="false"
        >
        </el-table-column>

        <!-- :formatter="rounding"：绑定下方展示两位小数的方法 -->
        <el-table-column
          prop="price"
          label="商品单价"
          width="250px"
          align="center"
          :formatter="rounding"
        >
        </el-table-column>

        <el-table-column
          prop="stock"
          label="商品存量"
          width="250px"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="sold"
          label="是否在售"
          width="250px"
          align="center"
        >
          <!--在 el-table-column 中做判断-->
          <template slot-scope="soldInfo">
            <span v-show="soldInfo.row.sold==0">下架</span>
            <span v-show="soldInfo.row.sold==1">销售中</span>
            <span v-show="soldInfo.row.sold==2">即将销售</span>
          </template>
        </el-table-column>

        <el-table-column
          fixed="right"
          label="操作"
          width="250px"
          align="center"
        >
          <template slot-scope="scope">
            <el-button type="success" size="small" v-if="scope.row.role != 2" v-on:click="$router.push({name:'updateCommodity',params:{commodityId:scope.row.commodityId,commodityName:scope.row.commodityName,commodityType:scope.row.commodityType,pictureId:scope.row.pictureId,introduce:scope.row.introduce,stock:scope.row.stock,sold:scope.row.sold,price:scope.row.price}})">修改</el-button>
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


  </div>

</template>

<script>
//import addCommodity from "@/view/userAdmin/commodityController/components/addCommodityPicture.vue"

import axios from "axios";

export default {
  name: "commodityController",
  data(){
    return{
      input1:"",
      commodityInfo:null
    }
  },
  methods:{
    //让展示的数据保留两位小数用
    rounding(row,column) {
      return parseFloat(row[column.property]).toFixed(2)
    },
    //搜索商品信息
    async getCommodityInfo(selectInfo){
      await this.axios({
        method:"get",
        params:{
          commodityName:selectInfo
        },
        // url:"http://127.0.0.1:8081/Bicycle/commodity/selectCommodityByName"
        url:"/BicycleProject/Bicycle/commodity/selectCommodityByName"
      }).then(response=>{
        this.commodityInfo=response.data;
      })
    }
  },

  //进入路由时查询商品信息
  async beforeRouteEnter(to,from,next){ //在进入路由执行

    let repairItems1;

    await axios({
      method:"get",
      params:{ //参数
      },
      url:"http://127.0.0.1:8081/Bicycle/commodity/selectCommodityByName"
    }).then(response=>{
      repairItems1 = response.data;
    })

    //对该vue中的data操作的话就在 next 里操作，vm就相当于平时的 this
    next((vm)=>{
      vm.commodityInfo = repairItems1;
    });
  }

}
</script>

<style scoped>

</style>
