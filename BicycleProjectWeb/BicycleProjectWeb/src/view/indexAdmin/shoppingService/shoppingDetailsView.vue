<template>

  <div>

    <!--  导航栏模块  -->
    <div class="head">
      <!--head-->
      <head1></head1>
    </div>

    <!--Shop商品列表-->
    <div class="shop">

      <!-- 商品图片 -->
      <div style="float: left;padding-top: 40px;margin-left:580px ">
        <el-image
          v-if="commodityPicture !== undefined"
          :src="commodityPicture"
          style="background-color: pink;width: 330px;height: 230px"
        >
        </el-image>
      </div>

      <div style="float: right;margin-top: 40px;margin-right: 580px;width: 330px;height: 230px;color: #ffd04b;text-align: left">
        <div style="font-size: 30px;float: left;width: 330px;margin-left: 0px;margin-top: 5px;border-style: solid;border-width:0px 0px 1px 0px;border-color: #ffd04b">{{commodityName}}</div>
        <div style="font-size: 30px;float: left;width: 330px;margin-left: 0px;margin-top: 5px;">单价：</div>
        <br/>
        <div style="font-size: 30px;float: right;margin-left: 0px;margin-top: 5px;">{{towNumber(price)}}元</div>
        <div style="font-size: 30px;float: left;width: 330px;margin-left: 0px;margin-top: 5px;border-style: solid;border-width:0px 0px 1px 0px;border-color: #ffd04b"></div>

        <!-- 计数器 -->
        <div style="float:left;margin-left: 0px;margin-top: 12px">
          <el-input-number style="float: right;" v-model="sum"  :min="1" :max="10"></el-input-number>
        </div>

        <div style="float: right;margin-top: 10px">


          <!-- 按钮 -->
          <el-button type="warning" style="width: 120px;height: 45px;font-size: 20px;font-size: 23px;padding-left: 10%;padding-right: 10%;padding-top: 10px" v-if="sold===1" @click="addToShoppingCart(commodityId,commodityName,price,sum)">购买</el-button>
          <el-button type="warning" disabled="true" style="width: 120px;height: 45px;font-size: 23px;padding-left: 10%;padding-right: 10%;padding-top: 10px" v-else-if="sold ===0" >下架</el-button>
          <el-button type="warning" disabled="true" style="width: 120px;height: 45px;font-size: 23px;padding-left: 10%;padding-right: 10%;padding-top: 10px" v-else-if="sold ===2" >即将销售</el-button>
        </div>
      </div>

      <!-- 简介部分 -->
      <div style="float: left;margin-left:-325px;margin-top: 20px;width: 680px;height: 300px;border-style: solid;border-width:1px 0px 0px 0px;border-color: #ffd04b;color: #ffd04b">

        <div style="float: left;margin-top: 3px;font-size: 30px">简介：</div><br/><br/><br/>
        <div style="float: left;font-size: 20px;text-align: left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{introduce}}</div>

      </div>

    </div>

    <!--  购买的弹窗  -->
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible1"
      width="30%">
      <span>已添加到购物车</span>
      <span slot="footer" class="dialog- footer">
        <el-button type="primary" @click="$router.push('/shoppingView')">确定</el-button>
      </span>
    </el-dialog>

  </div>

</template>

<script>
//引入组件
import head1 from "@/view/indexAdmin/components/head1.vue";
import axios from "axios";
//引入 vuex 的配置
import vuex from "@/vuex";

//商品页面
export default {
  name: "shoppingDetailsView",
  components:{ //注册组件
    head1, //注册导航栏
    shoppingCart:null,
  },
  props:["commodityId"],
  data(){
    return{
      dialogVisible1:false,
      commodityInfo:null,
      pictureId:null,
      commodityPicture:null,
      commodityName:null,
      price:null,
      sold:null,
      sum:1,
      introduce:""
    }
  },
  methods: {
    //提交到购物车
    async addToShoppingCart(commodityId,commodityName,price,sum){
      let sumPrice=price * sum;//计算总值
      //获得购物车
      this.shoppingCart = vuex.state.shoppingCart;
      console.log()
      //实例化一个明细类
      class shoppingListDetails{
        constructor(commodityId,commodityName,price,sum,sumPice) {
          this.commodityId=commodityId;
          this.commodityName=commodityName;
          this.price=price;
          this.sum=sum;
          this.sumPrice=sumPrice;
        }
      };
      //console.log(new  shoppingListDetails(commodityId,commodityName,price,num,sumPrice));
      //给购物车添加一条明细
      this.shoppingCart.push(new  shoppingListDetails(commodityId,commodityName,price,sum,sumPrice));
      //添加商品明细到vuex
      vuex.commit("updateShoppingCart",this.shoppingCart);
      console.log(vuex.state.shoppingCart);

      //修改购物车信息
      await this.updateShoppingCart(vuex.state.userId,this.shoppingCart);

      //开启弹窗
      this.dialogVisible1=true;
    },
    //修改购物车信息
    async updateShoppingCart(userId,shoppingCart){
      await this.axios({
        method:"post",
        params:{
          userId:userId
        },
        data:shoppingCart,
        url:"http://127.0.0.1:8081/Bicycle/commodity/updateShoppingCart"
      }).then(response=>{
        let i=response.data;
        if (i === 0){
          alert("添加失败！");
        }else {
          console.log("添加成功！")
        }
      })
    },

    //转换为两位小数
    towNumber(val) {
      return val.toFixed(2)
    },
    //查询商品图片方法
    async getCommodityPicture(pictureId) {
      let picture;
      await this.axios({
        method: "get",
        params: {
          pictureId: pictureId,
        },
        url: "http://127.0.0.1:8081/Bicycle/commodity/selectCommodityPictureById"
      }).then(response => {
        picture = response.data;
        //this.imageData.push(picture);
        //console.log(picture);
        //console.log(picture);
      });
      //console.log(this.imageData);
      console.log("================================");
      return picture;
    },
    //转换为两位小数
    towNumber(val) {
      return val.toFixed(2)
    },
  },
  //进入页面时开始查询
  async beforeRouteEnter(to,from,next){

    //查询商品详细信息
    await next(async (vm)=>{
      await axios({
        methods:"get",
        params:{
        },
        url:"http://127.0.0.1:8081/Bicycle/commodity/selectCommodityById/"+vm.commodityId
      }).then(async response => {
        vm.commodityInfo = response.data;//获得商品详细信息
        console.log(vm.commodityInfo[0]);
        vm.commodityInfo=vm.commodityInfo[0];
        vm.pictureId = vm.commodityInfo.pictureId;
        vm.commodityPicture = await vm.getCommodityPicture(vm.commodityInfo.pictureId);//获得图片
        vm.commodityName = vm.commodityInfo.commodityName;
        vm.price = vm.commodityInfo.price;
        vm.sold = vm.commodityInfo.sold;
        vm.introduce = vm.commodityInfo.introduce;
        console.log(vm.commodityPicture);
      })
    })
  },
}
</script>

<style scoped>

html,body{
  padding: 0;
  margin: 0;
  border: 0;
  width: 100%;
  height: 100%;
}
.head{
  width: 100%;
  height: 100px;
  position: relative;
  margin-top: 80px;
  /*background-color: whitesmoke;*/
  width: auto;
}
.nav{
  float: right;
}
.content{
  width: 80%;
  margin: 0 auto;
}
.shop{
  width: 100%;
  margin-top: 10px;
  height: 750px;
  width: 100%;
  background-color: #323539;
}

.banner{
  width: 80%;
  height: 500px;
  background-color: #E4E7ED;
  margin-left: 10%;
  margin-right: 10%;
  margin-top: 50px;
  margin-bottom: 50px;
}

.btn:hover{
  background: grey;
}
.btn_banner{
  margin: 300px;
  margin-bottom: 0;
}
.product{
  width: 18%;
  height: 280px;
  padding: 5px 5px 40px 5px;
  margin: 35px;
  margin-bottom: 10px;
  display: inline-block;
  background-color: #545c64;
  font-size: 20px;
  color: #ffd04b;
  text-align: left;
}
.foot{
  margin-top:50px;
  height: 300px;
  background-color: #323539;
}


</style>
