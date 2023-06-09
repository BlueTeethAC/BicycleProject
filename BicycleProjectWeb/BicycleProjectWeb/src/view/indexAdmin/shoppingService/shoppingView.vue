<template>

  <div>

    <!--  导航栏模块  -->
    <div class="head">
      <!--head-->
      <head1></head1>
    </div>

    <div style="margin-bottom: -35px;margin-top: -70px">
      <h1 style="font-size: 50px">商品页面</h1>
    </div>

    <!--Shop商品列表-->
    <div class="shop">

      <div class="product" v-for="(item,index) in commodityInfo" :key="item.commodityId" v-on:click="$router.push({name:'shoppingDetailsView',params:{commodityId:item.commodityId}})">
        <!-- v-if="imageData[index] !== undefined"：如果图片没加载到就再次请求 -->
        <el-image
          v-if="imageData[index] !== undefined"
          :src="imageData[index]"
          :preview-src-list="srcList"
          style="background-color: pink;width: 330px;height: 230px"
        >
        </el-image>
        <hr style="border: none;height: 1px;background:#ffd04b "/>
        <span style="float: left;margin-top: 0px;margin-bottom:0px;margin-left: 10px">{{item.commodityName}}</span><br/>
        <span style="float: right;margin-top: 5px;margin-right: 10px">售价：{{towNumber(item.price)}}元</span>
      </div>

    </div>
  </div>

</template>

<script>
//引入组件
import head1 from "@/view/indexAdmin/components/head1.vue";
import axios from "axios";

//商品页面
export default {
  name: "shoppingView",
  components:{ //注册组件
    head1, //注册导航栏
  },
  data(){
    return{
      commodityInfo:null,
      imageData:[],
    }
  },
  methods: {
    //查询商品图片方法
    async getCommodityPicture(pictureId) {
      let picture;
      await this.axios({
        method: "get",
        params: {
          pictureId: pictureId
        },
        url: "http://127.0.0.1:8081/Bicycle/commodity/selectCommodityPictureById"
      }).then(response => {
        picture = response.data;
        //this.imageData.push(picture);
        //console.log(picture);
        //console.log(picture);
      });
      console.log(this.imageData);
      console.log("================================");
      return picture;
    },
    //套娃测试用的
    /*getPicture(){
    console.log("cnm!!!!!!!!!!");
  },*/


    //转换为两位小数
    towNumber(val) {
      return val.toFixed(2)
    },
  },
  //进入页面时开始查询
  async beforeRouteEnter(to,from,next){

    let commodityInfo=null;

    next(async (vm) => {

      await axios({
        methods: "get",
        params: {},
        url: "http://127.0.0.1:8081/Bicycle/commodity/selectCommodityByName"
      }).then(async response => {
        commodityInfo = response.data;
        vm.commodityInfo = commodityInfo;
        //console.log(vm.commodityInfo);

        //接收到数据后，遍历 接受的商品信息，循环请求图片信息，请求到的图片信息由数组 imageData 用 push 全部添加进去，到时候要用就用一样的下标获取就行
        for (let i in vm.commodityInfo) {
          let image = await vm.getCommodityPicture(vm.commodityInfo[i].pictureId);//请求获得base64图片
          vm.imageData.push(image);//把base64图片添加到数组
        };

      });



      //console.log(vm.imageData);

    })

  }
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
  margin-top: 50px;
  height: 2000px;
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
}
.foot{
  margin-top:50px;
  height: 300px;
  background-color: #323539;
}


</style>
