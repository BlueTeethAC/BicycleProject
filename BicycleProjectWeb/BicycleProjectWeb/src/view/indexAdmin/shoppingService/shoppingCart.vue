<template>


  <div>

    <!-- 购物车标题 -->
    <div style="float: left;margin-left: 10px;margin-top: -25px;margin-bottom: -25px"><h1>购物车</h1></div>

    <!-- 按钮 -->
    <el-button type="info" style="float: right;margin-right: 10px" v-on:click="$router.go(0)">刷新</el-button>

    <hr style="float: right;width: 1600px;margin-top: 10px;margin-bottom: 10px"/>

    <!--表格部分-->
    <div id="table1">
      <el-table
        :header-cell-style="{background:'#909399',color:'#ffd04b'}"
        :data="shoppingCart"
        :row-style="{background:'#E4E7ED'}"
        :summary-method="getSummaries"
        show-summary
        style="width: 100%">

        <el-table-column
          prop="commodityId"
          label="商品Id"
          width="200px"
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

        <el-table-column
          prop="price"
          label="单价"
          width="200px"
          align="center"
          :formatter="rounding"
        >
        </el-table-column>

        <el-table-column
          prop="sum"
          label="数量"
          width="250px"
          align="center"
        >
        </el-table-column>

        <!-- :formatter="rounding"：绑定下方展示两位小数的方法 -->
        <el-table-column
          prop="sumPrice"
          label="价格"
          width="350px"
          align="center"
          :formatter="rounding"
        >
        </el-table-column>

        <el-table-column
          fixed="right"
          label="操作"
          width="200px"
          align="center"
        >
          <template slot-scope="scope">
            <el-button type="danger" size="small"  v-on:click="removeButton(scope.row.commodityId)">移除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 确认购买按钮 -->
    <el-button type="primary" style="font-size: 18px;width: 130px;height:45px;margin-left: 10%;margin-top: 15px;margin-right: 10%" v-on:click="buyButton()">确定购买</el-button>

    <!--  提示商品不足的弹窗  -->
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible3"
      width="30%">
      <span>{{notEnoughMessage}}</span>
      <span slot="footer" class="dialog- footer">
        <el-button type="primary" @click="confirmButton2()">确定</el-button>
      </span>
    </el-dialog>

    <!--  移除用的弹窗  -->
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible1"
      width="30%">
      <span>{{removeMessage}}</span>
      <span slot="footer" class="dialog- footer">
        <el-button type="primary" @click="confirmButton()">确定</el-button>
        <el-button type="primary" @click="cancelButton()">取消</el-button>
      </span>
    </el-dialog>

    <!-- 确定下单的弹窗 -->
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible2"
      width="30%">
      <span>{{buyMessage}}</span>
      <span slot="footer" class="dialog- footer">
        <el-button type="primary" @click="confirmButton1()">确定</el-button>
        <el-button type="primary" @click="cancelButton1()">取消</el-button>
      </span>
    </el-dialog>

  </div>

</template>

<script>
//引入 vuex 的配置
import vuex from "@/vuex"

export default {
  name: "shoppingCart",
  data(){
    return{
      removeMessage:"请问是否确认移除",
      commodityId1:null,//列表中要移除的商品id
      shoppingCart:null,
      commoditySumPrice:0,
      dialogVisible1:false,
      dialogVisible2:false,
      dialogVisible3:false,
      buyMessage:"请问是否确认下单",
      notEnoughMessage:"",
    }
  },
  methods:{
    //移除按钮
    removeButton(commodityId){
      this.commodityId1=commodityId;//赋值
      this.dialogVisible1=true;//弹出弹窗
    },
    //确定移除功能
    async confirmButton(){
      for (let i in this.shoppingCart){
        if (this.shoppingCart[i].commodityId === this.commodityId1){
          let a=this.shoppingCart.slice(0,i);//取出要被移除的元素的前面的所有元素
          let b=this.shoppingCart.slice(i+1,this.shoppingCart.length);//取出要被移除的元素后面的所有元素
          this.shoppingCart=a.concat(b);//拼接成新的元素并赋值给 this.shoppingCart
        }
      }
      //提交给后端刷新购物车信息
      await this.updateShoppingCart(vuex.state.userId,this.shoppingCart);
      //覆盖 vuex 中的购物车信息
      vuex.commit("updateShoppingCart",this.shoppingCart);
      //刷新页面
      this.$router.go(0);
    },

    //取消移除
    cancelButton(){
      this.commodityId1=null;//重置
      this.dialogVisible1=false;//关闭弹窗
    },

    //让展示的数据保留两位小数用
    rounding(row,column) {
      return parseFloat(row[column.property]).toFixed(2)
    },

    //总和表格方法
    getSummaries(param) {
      const { columns, data } = param;
      const sums = [];
      columns.forEach((column, index) => {
        if (index === 0) { //第一列变成总价
          sums[index] = '总价';
          return;
        }else if(index != 4){ //不是第四列的话就不计算总值
          return;
        }

        sums[4]=parseFloat(this.commoditySumPrice).toFixed(2);

      });

      return sums;
    },

    //确定下单按钮
    buyButton(){
      this.dialogVisible2=true;
    },
    //取消下单按钮
    cancelButton1(){
      this.dialogVisible2=false;
    },
    //提示商品不足
    notEnough(commodityName){
      this.notEnoughMessage=commodityName+"余量不足";
      this.dialogVisible3=true;//开启弹窗
    },
    //商品不足确认按钮
    confirmButton2(){
      this.notEnoughMessage="";
      this.dialogVisible3=false;//关闭弹窗
      this.$router.go(0);//刷新页面
    },
    //修改商品状态
    async updateCommodityStatus(commodityId,stock,sold){
      this.axios({
        method:"post",
        params:{
          commodityId:commodityId,
          stock:stock,
          sold:sold
        }
      }).then(response=>{
        let i=response.data;
        if (i === 1){
          console.log("修改成功！");
        }else {
          alert("修改失败！");
        }
      })
    },
    //新增销售清单
    async addSaleList(userId,username,sumPrice,saleTime,saleList){
      await this.axios({
        method:"post",
        params:{
          userId:userId,
          username:username,
          sumPrice:sumPrice,
          saleTime:saleTime
        },
        data:saleList,
        // url:"http://127.0.0.1:8081/Bicycle/commodity/addSaleList"
        url:"/BicycleProject/Bicycle/commodity/addSaleList"
      }).then(response=>{
        let i = response.data;
        if (i === 1){
          console.log("下单成功!");
        }else{
          alert("下单失败！");
        }
      })
    },
    //确认下单按钮
    async confirmButton1(){
      let j=0;//j默认1

      //获得当前日期
      let saleTime=this.currentTime();
      //遍历检测仓库中存量是否足够
      for (let i in this.shoppingCart){
        let id = this.shoppingCart[i].commodityId;//取得id
        let info = this.getCommodityInfo(id);//取得商品信息
        if (info.stock < this.shoppingCart[i].sum){ //如果存量小于要购买的数量
          this.notEnough(this.shoppingCart[i].commodityName);//提示商品不足
          j=1;//如果有商品存量不足，j就变成1
          break;
        }
      }

      if (j != 1){ //如果商品都足够，就开始遍历扣除商品余量并且下单
        for (let i in this.shoppingCart){
          let shopping = this.shoppingCart[i];
          let info = this.getCommodityInfo(shopping.commodityId);//取得商品信息
          let num = info.stock-shopping.sum;//库存减去购物车里的数量
          if (num === 0){ //如果余量正好等于 0，修改时一起把状态改为 0
            await this.updateCommodityStatus(info,num,0)//商品信息改为下架
          }else{
            await this.updateCommodityStatus(info,num,info.sold)//商品信息改为下架
          }
        }
      }

      //新增销售清单数据
      await this.addSaleList(vuex.state.userId,vuex.state.username,this.commoditySumPrice,saleTime,this.shoppingCart);

      let test=[];

      //清空购物车
      await this.updateShoppingCart(vuex.state.userId,test);

      //覆盖 vuex 中的购物车信息
      vuex.commit("updateShoppingCart",test);

      this.$router.go(0);
    },
    //按照id查询商品信息
    async getCommodityInfo(commodityId){
      let commodityInfo=null;
      this.axios({
        method:"get",
        params:{
        },
        // url:"http://127.0.0.1:8081/Bicycle/commodity/selectCommodityById/"+commodityId
        url:"/BicycleProject/Bicycle/commodity/selectCommodityById/"+commodityId
      }).then(response=>{
        commodityInfo=response.data;
      });
      return commodityInfo;
    },
    //获取当前日期
    currentTime() {
      var date = new Date();
      var year = date.getFullYear(); //月份从0~11，所以加一
      let month = date.getMonth();
      console.log("month",month);
      var dateArr = [
        date.getMonth() + 1,
        date.getDate(),
        date.getHours(),
        date.getMinutes(),
        date.getSeconds(),
      ];
      //如果格式是MM则需要此步骤，如果是M格式则此循环注释掉
      for (var i = 0; i < dateArr.length; i++) {
        if (dateArr[i] >= 1 && dateArr[i] <= 9) {
          dateArr[i] = "0" + dateArr[i];
        }
      }
      var strDate =
        year +
        "-" +
        dateArr[0] +
        "-" +
        dateArr[1];
      //此处可以拿外部的变量接收  strDate:2022-05-01 13:25:30
      //this.date = strDate;
      //console.log("strDate",strDate);
      return strDate;
    },
    //修改购物车
    async updateShoppingCart(userId,shoppingCart){
      await this.axios({
        method:"post",
        params:{
          userId:userId
        },
        data:shoppingCart,
        // url:"http://127.0.0.1:8081/Bicycle/commodity/updateShoppingCart"
        url:"/BicycleProject/Bicycle/commodity/updateShoppingCart"
      }).then(response=>{
        let i=response.data;
        if (i === 1 || i === 2){
          console.log("修改成功！");
        }else {
          alert("炸了~");
        }
      })
    },
  },

  //路由守卫
  beforeRouteEnter(to,from,next){
    next((vm)=>{
      vm.shoppingCart = vuex.state.shoppingCart;//给 vm.shoppingCart 赋值

      if (vuex.state.username == null){
        vm.$router.push("/loginView/login")
      };

      //遍历购物车数据，叠加总金额
      for (let item in vm.shoppingCart){
          vm.commoditySumPrice+=vm.shoppingCart[item].sumPrice;
      }
    })
  }
}
</script>

<style scoped>

</style>
