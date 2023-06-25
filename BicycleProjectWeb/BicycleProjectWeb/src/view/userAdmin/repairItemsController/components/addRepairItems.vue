<template>

  <div>

    <el-dialog
      title="新增维修项目"
      :visible.sync="dialogVisible"
      width="30%" @close="$router.push('/userAdminView/repairItemsController')">
      <!-- @close="$router.push('/userAdminView/repairItemsController')"：关闭弹窗的动作换成这个跳转路由 -->

      <form action="#" method="post" >

        <div class="div_tip">&nbsp;&nbsp;&nbsp;&nbsp;维修项目名：<el-input v-model="repairItemsName" style="width: 200px" placeholder="请输入维修项目名"></el-input></div>

        <!-- onkeyup="value=value.match(/\d+\.?\d{0,2}/,'')" 限制输入框只能输入两位小数 -->
        <div class="div_tip">维修项目价格：<el-input v-model="repairItemsPrice" style="width: 200px" placeholder="请输入维修项目价格" onkeyup="value=value.match(/\d+\.?\d{0,2}/,'')"></el-input></div>

        <br/>

        <el-button type="primary" v-on:click="addRepairItems()">新增项目</el-button>

      </form>

    </el-dialog>



  </div>


</template>

<script>

//新增 维修项目信息
export default {
  name: "addRepairItems",
  components:{
  },
  data(){
    return{
      dialogVisible:true,
      repairItemsName:null,
      repairItemsPrice:null
    }
  },
  methods:{
    //新增维修项目
    async addRepairItems(){
      let i;
      await this.axios({
        method:"get",
        params:{
          itemsName:this.repairItemsName,
          itemsPrice:this.repairItemsPrice
        },
        // url:"http://127.0.0.1:8081/Bicycle/repair/addRepairItems"
        url:"/BicycleProject/Bicycle/repair/addRepairItems"
      }).then(repsonse=>{
        i=repsonse.data;
        if (i === 1){
          console.log("新增成功");
        }else {
          console.log("新增失败");
        }
        //初始化值
        this.repairItemsName=null;
        this.repairItemsPrice=null;
        //关闭弹窗
        this.dialogVisible=false;
        //调用父路由中的方法
        this.$parent.getrepairItems(null);
        //跳转路由
        this.$router.push("/userAdminView/repairItemsController");
      });
    }
  }
}
</script>

<style scoped>

.div_tip{
  margin-left: 10px;
  margin-bottom: 10px;
  font-family: "Hiragino Sans GB";
  color: black;
}

</style>
