<template>

  <div>
    <!-- 按钮 -->
    <el-button type="info" style="float: left;margin-left: 10px" v-on:click="$router.push('/shoppingList')" v-if="nowRole === 0">返回上一页</el-button>
    <el-button type="info" style="float: left;margin-left: 10px" v-on:click="$router.push('/shoppingListAdmin')" v-if="nowRole !=0">返回上一页</el-button>

    <!-- 消费清单明细 -->
    <div style="margin-left: 10%;margin-right:10%;margin-top: -25px;margin-bottom: -25px;"><h1>购物清单明细</h1></div>
    <!-- 分割线 -->
    <hr style="float: right;width: 1600px;margin-top: 10px;margin-bottom: 10px"/>

    <el-table
      :header-cell-style="{background:'#909399',color:'#ffd04b'}"
      :data="shoppingListDetails"
      :row-style="{background:'#E4E7ED'}"
      style="width:60%;margin-left: 20%;margin-right: 20%">
      <el-table-column
        prop="commodityId"
        label="商品Id"
        width="200"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="commodityName"
        label="商品名称"
        width="220"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="price"
        label="单价"
        width="180"
        align="center"
        :formatter="rounding"
      >
      </el-table-column>

      <el-table-column
        prop="sum"
        label="数量"
        width="180"
        align="center"
      >
      </el-table-column>

      <el-table-column
        prop="sumPrice"
        label="总和"
        width="180"
        align="center"
        :formatter="rounding"
      >
      </el-table-column>
    </el-table>

  </div>

</template>

<script>
import axios from "axios";
import vuex from "@/vuex"

//消费清单明细
export default {
  name: "shoppingListDetails",
  props:["detailsId"],
  data(){
    return{
      nowRole:null,//当前权限等级
      shoppingListAllDetails:null,
      shoppingListDetails:null
    }
  },
  methods:{
    //让展示的数据保留两位小数用
    rounding(row,column) {
      return parseFloat(row[column.property]).toFixed(2)
    },
  },

  //路由守卫
  async beforeRouteEnter(to,from,next){
    next(async (vm)=>{
      //用消费清单明细Id 去查询明细数据
      await axios({
        methods:"get",
        url:"http://127.0.0.1:8081/Bicycle/commodity/selectSaleListDetails/"+vm.detailsId
      }).then(response=>{
        vm.shoppingListDetails=response.data;
        ///console.log(vm.shoppingListDetails.listAllDetails)
        vm.shoppingListDetails=vm.shoppingListDetails.listAllDetails;//获得明细数据
        //console.log(vm.shoppingListDetails);
        vm.nowRole=vuex.state.role;//获得当前权限等级
      })
    })

  }

}
</script>

<style scoped>

</style>
