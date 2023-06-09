<template>

  <div class="block">
    <!--<span class="demonstration">默认 Hover 指示器触发</span>-->
    <el-carousel height="500px" type="card">
      <el-carousel-item v-for="item in imgList" :key="item">
        <img :src="item.idView" class="image">
        <!--<h3 class="small">{{ item.idView }}</h3>-->
      </el-carousel-item>
    </el-carousel>
  </div>

</template>



<script>
//走马灯模块

export default {
  name: "Carousel",
  data(){
    return{
      imgList:[
        {id:0,idView:require("@/assets/index1.jpeg")},
        {id:1,idView:require("@/assets/index2.jpeg")},
        {id:2,idView:require("@/assets/index3.jpeg")},
        {id:3,idView:require("@/assets/index4.jpeg")},
        {id:3,idView:require("@/assets/index5.jpg")},
      ],
    }
  }
}
</script>


<style scoped>
.block{
  margin-top: -50px;
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}

.image{
  width: 780px;
  height: 500px;
}

</style>
