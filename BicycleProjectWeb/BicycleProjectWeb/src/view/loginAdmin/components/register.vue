<template>

  <div class="div2">

    <el-form ref="registerForm" :model="form" :rules="rules" label-width="100px" class="login-box">
      <h3 class="login-title">欢迎 注册</h3>
      <el-form-item label=" 账号" prop="username">
        <el-input type="text" placeholder="请输入用户名" v-model="form.username"/>
      </el-form-item>
      <el-form-item label=" 手机号码" prop="phoneNumber">
        <el-input type="text" placeholder="请输入手机号码" v-model="form.phoneNumber"/>
      </el-form-item>
      <el-form-item label=" 密码" prop="password">
        <el-input type="password" placeholder=" 请输入密码" v-model="form.password"/>
      </el-form-item>
      <el-form-item label=" 确认密码" prop="againPassword">
        <el-input type="password" placeholder=" 请输入确认密码" v-model="form.againPassword"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" v-on:click="onSubmit( 'registerForm')" style="margin-left: -80px">注册</el-button>
        <el-button type="primary" v-on:click="goLoginView()">返回登录</el-button>
      </el-form-item>
    </el-form>

    <!--  信息缺少时弹窗配置  -->
    <el-dialog
      title="温馨提示"
      :visible.sync="dialogVisible1"
      width="30%">
      <!--<span>请输入注册信息</span>-->
      <!--error_message 为错误信息文本-->
      <span>{{error_message}}</span>
      <span slot="footer" class="dialog- footer">
            <el-button type="primary" @click="dialogVisible1 = false">确定</el-button>
          </span>
    </el-dialog>

    <!--  注册成功时弹窗配置  -->
    <el-dialog
      title="温馨提示"
      :visible.sync="dialogVisible2"
      width="30%">
      <span>注册成功，点击确认跳转到登陆页面</span>
      <span slot="footer" class="dialog- footer">
            <!--<el-button type="primary" @click="dialogVisible2 = false">确定</el-button>-->
            <el-button type="primary" @click="$router.push('/LoginView/login')">确定</el-button>
          </span>
    </el-dialog>

  </div>

</template>

<script>

//引入路由
import router from "@/router"

export default {
  name: "register",
  props:["username","phoneNumber","password","againPassword","user","i"],
  data() {
    return {
      form: {
        username: '',
        phoneNumber: '',
        password: '',
        againPassword: '',
      },

      //用于接收从后端查询的用户名
      user:{
        userId:null,
        username:null,
        password:null,
        phoneNumber:null,
        role:null,
        status:null
      },

      //i：用于接收比如新增用户这种的返回值
      i:null,

      //报错信息
      error_message:null,

      //表单验证，需要在el-form-item 元素中增加prop 属性
      rules: {
        username: [
          {required: true, message: " 账号不可为空", trigger: 'blur'}
        ],
        phoneNumber: [
          {required: true, message: " 手机号码不可为空", trigger: 'blur'}
        ],
        password: [
          {required: true, message: " 密码不可为空 ", trigger: 'blur'}
        ],
        againPassword:[
          {required: true, message:" 确认密码不可为空", trigger: 'blur'}
        ]
      },
      //对话框显示和隐藏
      dialogVisible1: false,
      dialogVisible2: false,
    }
  },
  methods: {
    //按钮点击方法
    async onSubmit(formName) {
      //为表单绑定验证功能
      //this.$refs [传入的表单名]：读取（表单名）这个表单
      //并使用该引用调用 validate 方法来进行表单验证。validate 方法是 Vue.js 中用于表单验证的方法
      //如果表单验证通过（即 valid 为 true），则输出表单中的用户名和密码，并使用编程式导航方式（this.$router.push）跳转到 /main 页面。如果表单验证不通过（valid 为 false），则将 dialogVisible 变量设为 true，提示用户表单验证不通过，并返回 false。
      this.$refs [formName].validate(async (valid) => {

        if (valid) {  //if (valid) ：只要 valid 不为空，就用 router push（跳转） 到 /main
          //使用vue-router路由到指定页面，该方式称之为编程式导航

          if (this.passwordCheck(formName)) { //验证确认密码与密码是否一致

            if (await this.usernameCheck(formName)) { //判断用户名是否已经存在


              if (await this.addUser(formName)) { //注册成功

                console.log("注册成功了拉")
                this.dialogVisible2 = true; //跳出注册成功的弹窗

              } else { //注册失败
                this.error_message = "注册失败！联系管理员";
                this.dialogVisible1 = true;
                return false;
              }

            } else { //用户名已经存在


              this.error_message = "用户名已经存在！";
              this.dialogVisible1 = true;
              return false;
            }

          } else { //确认密码与密码不一致
            this.error_message = "确认密码与密码不一致！";
            this.dialogVisible1 = true;
            return false;
          }


          //this.$router.push("/LoginView/login");//按钮点击后跳转的路径
        } else {
          this.error_message = "请输入注册信息！";
          this.dialogVisible1 = true;
          return false;
        }
      });
    },

    //用户名检查是否已经存在
    //想要返回 axios 中的值需要在方法名前加上 async，并且 this.axios 前加上 return await
    async usernameCheck(formName){
      let bol;
      await this.axios({
        method:"post",
        params:{
          username:this.form.username
        },
        // url:"http://127.0.0.1:8081/Bicycle/user/selectUserByName1"
        url:"/BicycleProject/Bicycle/user/selectUserByName1"
      }).then(
        response=>{
          this.user=response.data;//赋值
          if (this.user.username == null){//如果为空，就是没有用户名已存在
            bol=true;
          }else {
            bol=false;
          }
        }
      );
      return bol;
    },

    //判断密码与确认密码是否相同
    passwordCheck(formName){
      //如果密码与确认密码相同
      if (this.form.password === this.form.againPassword){
        return true;
      }else {
        return false;
      }
    },

    //新增用户信息
    async addUser(formName){
      let bol;

      //注册信息
      await this.axios({
        //请求方式
        method:"post",
        //参数
        params:{
          username:this.form.username,
          password:this.form.password,
          phoneNumber:this.form.phoneNumber
        },
        // url:"http://127.0.0.1:8081/Bicycle/user/addUser"
        url:"/BicycleProject/Bicycle/user/addUser"
      }).then(
        response=>{
          this.i=response.data;
          if (this.i === 2){ //注册成功
            bol = true;
          }else { //注册失败
            bol = false;
          }
        }
      );
      return bol;
    },

    //跳转回登陆页面
    goLoginView(){
      router.push("/loginView/login");
    }
  }
}
</script>

<!-- element-ui 的配置 -->
<style lang="scss" scoped>
.login-box {
  border: 1px solid #DCDFE6;
  width: 350px;
  margin: 250px auto;
  padding: 35px 35px 15px 35px;
  border-radius: 5px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  box-shadow: 0 0 25px #909399;
}

.login-title {
  text-align: center;
  margin: 0 auto 40px auto;
  color: #303133;
}

.div2 {
  margin: 0;
  position: relative;
  width: 50%;
  height: 100%;
  /*padding: 20px;*/
  /*border: 1px solid black;*/
  border: 0;
  /*background-color: #34495e;*/
  box-sizing: border-box;
  float: right;
}

</style>
