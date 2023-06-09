<template>

  <div>

    <el-dialog
      title="修改维修项目"
      :visible.sync="dialogVisible"
      width="30%" @close="$router.push('/userAdminView/repairItemsController')">
      <!-- @close="$router.push('/userAdminView/repairItemsController')"：关闭弹窗的动作换成这个跳转路由 -->

      <form action="#" method="post" >
        <div class="div_tip">&nbsp;&nbsp;&nbsp;&nbsp;维修项目Id：<el-input v-model="repairItemsId" style="width: 200px" placeholder="请输入维修项目Id" :disabled="true"></el-input></div>

        <div class="div_tip">&nbsp;&nbsp;&nbsp;&nbsp;维修项目名：<el-input v-model="repairItemsName" style="width: 200px" placeholder="请输入维修项目名"></el-input></div>

        <!-- onkeyup="value=value.match(/\d+\.?\d{0,2}/,'')" 限制输入框只能输入两位小数 -->
        <div class="div_tip">维修项目价格：<el-input v-model="repairItemsPrice" style="width: 200px" placeholder="请输入维修项目价格" onkeyup="value=value.match(/\d+\.?\d{0,2}/,'')"></el-input></div>

        <br/>

        <el-button type="primary" v-on:click="updateRepairItems(repairItemsName,repairItemsPrice,repairItemsId,$route.params.repairItemsStatus)">修改项目</el-button>

      </form>

    </el-dialog>

  </div>

</template>

<script>
//修改 维修项目信息
export default {
  name: "updateRepairItems",
  props:["repairItemsId","repairItemsName","repairItemsPrice","repairItemsStatus"],
  data(){
    return{
      dialogVisible:true
    }
  },
  methods:{

    //修改 维修项目信息
    async updateRepairItems(itemsName1,itemsPrice1,itemsId1,status1){
      let i;
      await this.axios({
        method:"get",
        params:{
          itemsName:itemsName1,
          itemsPrice:itemsPrice1,
          itemsId:itemsId1,
          status:status1
        },
        url:"http://127.0.0.1:8081/Bicycle/repair/updateRepairItems"
      }).then(response=>{
        i=response.data;

        if (i === 1){
          console.log("修改成功");
        }else {
          console.log("修改失败");
        }
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
