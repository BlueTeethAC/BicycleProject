<template>

  <div class="div2">

    <el-form ref="loginForm" :model="form" :rules="rules" label-width="80px" class="login-box">
      <h3 class="login-title">欢迎 登录</h3>
      <el-form-item label=" 账号" prop="username">
        <el-input type="text" placeholder="请输入账号" v-model="form.username"/>
      </el-form-item>
      <el-form-item label=" 密码" prop="password">
        <el-input type="password" placeholder=" 请输入密码" v-model="form.password"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" v-on:click="onSubmit2()" style="margin-left: -80px">注册</el-button>
        <el-button type="primary" v-on:click="getUserIfo( ' loginForm' )">登录</el-button>
      </el-form-item>
    </el-form>

    <el-dialog
      title="温馨提示"
      :visible.sync="dialogVisible"
      width="30%">
      <span>{{error_messgae}}</span>
      <span slot="footer" class="dialog- footer">
            <el-button type="primary" @click="dialogVisible = false">确定</el-button>
          </span>
    </el-dialog>

    <el-dialog
      title="登陆成功"
      :visible.sync="dialogVisible1"
      width="30%">
      <span>登陆成功，请点击确定跳转</span>
      <span slot="footer" class="dialog- footer">
            <el-button type="primary" @click="goHome()">确定</el-button>
          </span>
    </el-dialog>

  </div>

</template>

<script>
//引入路由
import router from "@/router"
//引入 vuex 的配置
import vuex from "@/vuex"

export default {
  name: "login",
  props:["user"],
  data() {
    return {
      form: {
        username: '',
        password: ''
      },
      error_messgae:null,
      //表单验证，需要在el-form-item 元素中增加prop 属性
      rules: {
        username: [
          {required: true, message: " 账号不可为空", trigger: 'blur'}
        ],
        password: [
          {required: true, message: " 密码不可为空 ", trigger: 'blur'}
        ]
      },
      //对话框显示和隐藏
      dialogVisible: false,
      dialogVisible1:false
    }
  },
  methods: {
    onSubmit1(formName) {
      //为表单绑定验证功能
      //this.$refs [传入的表单名]：读取（表单名）这个表单
      //并使用该引用调用 validate 方法来进行表单验证。validate 方法是 Vue.js 中用于表单验证的方法
      //如果表单验证通过（即 valid 为 true），则输出表单中的用户名和密码，并使用编程式导航方式（this.$router.push）跳转到 /main 页面。如果表单验证不通过（valid 为 false），则将 dialogVisible 变量设为 true，提示用户表单验证不通过，并返回 false。
      this.$refs [formName].validate((valid) => {
        if (valid) {  //if (valid) ：只要 valid 不为空，就用 router push（跳转） 到 /main
          //使用vue-router路由到指定页面，该方式称之为编程式导航

          //输出表单中的用户名
          //console.log(this.form.username);

          //输出表单中的密码
          //console.log(this.form.password);

          this.$router.push("/main");
        } else {
          this.error_messgae = "请输入账号和密码";
          this.dialogVisible = true;
          return false;
        }
      });
    },

    //跳转到注册页面
    onSubmit2() {
      router.push("/LoginView/register");
    },

    //查找用户信息
    async getUserIfo(formName){
      await this.axios({
        method:"post",
        params:{ //参数
          username:this.form.username
        },
        url:"http://127.0.0.1:8081/Bicycle/user/selectUserByName1"
      }).then(
        async response => {
          this.user = response.data;
          if (this.user.username == null) { //用户名不存在
            this.error_messgae = "用户名不存在";
            this.dialogVisible = true;
            return false;
          } else if (this.user.status == 0) { //账户未启用
            this.error_messgae = "账户未启用";
            this.dialogVisible = true;
            return false;
          } else if (this.user.password === this.form.password) { //密码与用户正确
            vuex.commit("updateUserId", this.user.userId);//将userId添加到状态
            console.log( this.user.userId)
            console.log( this.user.username)
            console.log( this.user.role)
            vuex.commit("updateUsername", this.user.username);//将userId添加到状态
            vuex.commit("updateRole", this.user.role);//将userId添加到状态

            //获得购物车
            let shoppingCart = await this.getShoppingCart(this.user.userId);
            // console.log("=======================");
            // console.log(shoppingCart);
            // console.log("=======================");
            vuex.commit("updateShoppingCart",shoppingCart);
            this.dialogVisible1 = true;
          } else { //密码错误
            this.error_messgae = "密码错误";
            this.dialogVisible = true;
            return false;
          }
        }
      );
    },

    //查询购物车信息
    async getShoppingCart(userId){
      let shoppingCart;
      await this.axios({
        method:"get",
        url:"http://127.0.0.1:8081/Bicycle/commodity/selectShoppingCart/"+userId
      }).then(response=>{
        shoppingCart=response.data;
      });
      return shoppingCart;// 返回购物车信息
    },

    //点击确认，跳转到首页
    goHome(){
      router.push("/indexView");
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
