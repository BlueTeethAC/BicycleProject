<template>

  <div>

    <!-- 按钮 -->
    <el-button type="info" style="float: left;margin-left: 10px" v-on:click="$router.push('/userAdminView/userController')">返回上一页</el-button>

    <hr style="float: right;width: 1600px;margin-top: 10px;margin-bottom: 10px"/>


    <div id="container">
      <div id="content">
        <form action="#" method="post" >

          <div class="div_tip">&nbsp;&nbsp;&nbsp;&nbsp;用户id：<el-input v-model="userId1" style="width: 200px" placeholder="请输入用户id" :disabled="true"></el-input></div>

          <div class="div_tip">&nbsp;&nbsp;&nbsp;&nbsp;用户名：<el-input v-model="username1" style="width: 200px" placeholder="请输入用户名" :disabled="true"></el-input></div>

          <div class="div_tip">手机号码：<el-input v-model="phoneNumber1" style="width: 200px" placeholder="请输入手机号码"></el-input></div>

          <div class="div_tip">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;角色：
            <el-select v-model="role1" placeholder="请选择" value-key="role1" style="width: 200px">
              <el-option
                v-for="item in roleInfo"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </div>

          <br/>

          <el-button type="info" v-on:click="updateButton">修改</el-button>

        </form>
      </div>
    </div>


    <el-dialog
      title="提示"
      :visible.sync="dialogVisible1"
      width="30%">
      <span>{{infoMessage}}</span>
      <span slot="footer" class="dialog- footer">
        <el-button type="primary"  @click="confirmButton()">确定</el-button>
        <el-button type="primary"  @click="cancelButton()">取消</el-button>
      </span>
    </el-dialog>


  </div>

</template>

<script>
//修改用户信息界面
export default {
  name: "updateUser",
  props:["userId1","username1","phoneNumber1","role1","status1"],
  data(){
    return{
      dialogVisible1:false,
      infoMessage:"",
      //testRole:this.role1,
      roleInfo:[
        {
          value:0,
          label:"普通用户"
        },
        {
          value:1,
          label:"管理员"
        }],
    }
  },
  methods:{
    //弹出窗口
    updateButton(){
      console.log(this.role1)
      //修改展示文本
      this.infoMessage="是否确认修改"
      //弹出弹窗
      this.dialogVisible1=true;
    },
    //确认按钮
    async confirmButton(){
      //执行更新请求
      await this.updateUser(this.userId1,this.phoneNumber1,this.role1);
      //关闭弹窗
      this.dialogVisible1=false;
      //跳转
      this.$router.push("/userAdminView/userController");
    },
    //取消按钮
    cancelButton(){
      //关闭弹窗
      this.dialogVisible1=false;
    },
    async updateUser(userId1,phoneNumber1,role1){
      let i;
      await this.axios({
        method:"post",
        params:{
          userId:userId1,
          phoneNumber:phoneNumber1,
          role:role1,
          status:this.status1
        },
        // url:"http://127.0.0.1:8081/Bicycle/user/updateUser"
        url:"/BicycleProject/Bicycle/user/updateUser"
      }).then(response=>{
        i = response.data;
        if (i===1){
          console.log("修改成功");
        }else {
          console.log("修改失败");
        }
      });
    },
  }
}
</script>



<style scoped>

#container{width: 100%;height: 810px; margin: 0 auto; text-align: center;}

#content{width: 100%;float: right;
  height: 558px;
  padding-top: 20px;
  text-align-last: center;
}

.div_tip{
  margin-left: -60px;
  margin-bottom: 10px;
  font-family: "Hiragino Sans GB";
  color: black;
}

#content h1{
  color: #323539;
  font-size: 30px;
}

</style>
