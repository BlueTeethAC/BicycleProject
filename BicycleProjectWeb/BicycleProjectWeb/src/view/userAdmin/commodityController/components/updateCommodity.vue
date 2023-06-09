<template>

  <div>

    <!-- 按钮 -->
    <el-button type="info" style="float: left;margin-left: 10px" v-on:click="$router.push('/userAdminView/commodityController')">返回上一页</el-button>

    <hr style="float: right;width: 1600px;margin-top: 10px;margin-bottom: 10px"/>


    <div id="container">
      <div id="content">
        <form action="#" method="post" >

          <div class="div_tip">&nbsp;&nbsp;&nbsp;&nbsp;商品Id：<el-input v-model="commodityId"  style="width: 200px" placeholder="请输入商品id" :disabled="true"></el-input></div>

          <!--@input="(v)=>(aaa=v.replace(/[^\d.]/g,''))"-->
          <div class="div_tip">&nbsp;&nbsp;&nbsp;&nbsp;商品名：<el-input v-model="commodityName"  style="width: 200px" placeholder="请输入商品名"></el-input></div>

          <div class="div_tip">商品类型：
            <el-select v-model="commodityType" placeholder="请选择" value-key="role1" style="width: 200px" :disabled="true">
              <el-option
                v-for="item in roleInfo"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </div>

          <!-- style="vertical-align:top;" ：设置文本域 textarea 顶格对齐 -->
          <div class="div_tip" style="margin-left: 140px" vertical-align:top>商品简介：<el-input v-model="introduce" type="textarea" :autosize="{ minRows: 3, maxRows: 5}" style="width: 400px;vertical-align:top;" placeholder="请输入商品简介"></el-input></div>

          <!-- onkeyup="value=value.replace(/[^\d]/g,'')"：设置输入框只能输入正整数 -->
          <div class="div_tip">商品存量：<el-input v-model="stock" style="width: 200px" placeholder="请输入商品存量" onkeyup="value=value.replace(/[^\d]/g,'')"></el-input></div>

          <!-- onkeyup="value=value.match(/\d+\.?\d{0,2}/,'')" 限制输入框只能输入两位小数 -->
          <div class="div_tip">商品单价：<el-input v-model="price" style="width: 200px" placeholder="请输入商品单价" onkeyup="value=value.match(/\d+\.?\d{0,2}/,'')"></el-input></div>

          <!-- 销售状态 -->
          <div class="div_tip">销售状态：
            <el-select v-model="sold" placeholder="请选择" value-key="role1" style="width: 200px" >
              <el-option
                v-for="item in soldStatus"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </div>


          <!-- 新增图片 -->
          <div>
            <input type="file" accept="image/png,image/jpeg,image/jpg,image/gif"
                   ref="selectFile"
                   name="wenjian"
                   @change="checkFile($event)"
                   hidden />

            <el-button v-on:click="selectFile()" type="primary" style="float: left;margin-left: 580px;margin-right: -680px">点击上传图片</el-button>

            <!-- style="display: none"：隐藏按钮 -->
            <el-button v-on:click="addPicture(imgResult)" type="primary" style="display: none">点击提交图片</el-button>

            <div>
              <el-image
                style="width: 200px; height: 200px"
                :src="url1"
                :preview-src-list="srcList">
              </el-image>
            </div>
          </div>


          <br/>

          <el-button type="info" v-on:click="dialogVisible1Button">修改商品</el-button>

        </form>
      </div>
    </div>


    <el-dialog
      title="提示"
      :visible.sync="dialogVisible1"
      width="30%">
      <span>{{infoMessage}}</span>
      <span slot="footer" class="dialog- footer">
        <el-button type="primary"  @click="updateCommodity()">确定</el-button>
        <el-button type="primary"  @click="cancelButton()">取消</el-button>
      </span>
    </el-dialog>


  </div>

</template>

<script>
import axios from "axios";

export default {
  //修改商品模块
  name: "updateCommodity",
  components:{
  },
  props:["commodityId","commodityName","commodityType","pictureId","introduce","stock","sold","price"],
  data(){
    return{
      dialogVisible1:false,
      infoMessage:"",
      //testRole:this.role1,
      roleInfo:[
        {
          value:0,
          label:"商品类型0"
        },
        {
          value:1,
          label:"商品类型1"
        },
        {
          value:2,
          label:"商品类型2"
        }],
      soldStatus:[ //销售状态
        {
          value:0,
          label:"下架"
        },
        {
          value:1,
          label:"销售中"
        },
        {
          value:2,
          label:"即将销售"
        }],
      url1: "",//图片路径
      filelist:[],
      dialogMode:'create',//create、edit
      imgResult:"",
      pictureName:"pictureName"
    }
  },
  methods:{



    //弹出窗口
    dialogVisible1Button(){
      //修改展示文本
      this.infoMessage="是否确认修改商品"
      //弹出弹窗
      this.dialogVisible1=true;
    },

    //取消按钮
    cancelButton(){
      //关闭弹窗
      this.dialogVisible1=false;
    },

    //点击修改后的方法
    async updateCommodity(){
      this.pictureId = await this.addPicture();//获得 图片 id
      console.log(this.price);
      await this.axios({
        method:"post",
        params:{
          commodityId:this.commodityId,
          commodityName:this.commodityName,
          commodityType:this.commodityType,
          pictureId:this.pictureId,
          introduce:this.introduce,
          stock:this.stock,
          sold:this.sold,
          price:this.price
        },
        url:"http://127.0.0.1:8081/Bicycle/commodity/updateCommodity"
      }).then(response=>{
        let i;
        i=response.data;
        if (i === 1){
          console.log("修改成功！");
          this.$router.push("/userAdminView/commodityController");
        }else {
          alert("新增失败！");
        }
      });
    },


    //============== 新增图片的部分 =======================
    //把文件转换为 base64 并给 imgResult 赋值
    forImgResult(file){

      //放入 Promise 中
      return new Promise(function (resolve, reject){

        //创建了一个 FileReader 对象
        let reader = new FileReader();

        //定义结果
        let imgResult = "";

        //发送转换为 base64 的请求
        reader.readAsDataURL(file);

        //获得转换为 base64 的字符串
        reader.onload = function (){
          imgResult = reader.result;
        };

        //请求失败的代码
        reader.onerror = function (){
          reject(imgResult);
        }

        //请求成功的代码
        reader.onloadend = function() {
          resolve(imgResult);
          console.log(imgResult);
        };
      })
    },

    //div 的点击事件函数
    selectFile() {
      //此时就用到了我们上面所说的
      //this.$refs
      this.$refs.selectFile.click();  // 这样就会触发 文件的选择
    },

    //input 的 change 事件函数
    async checkFile (e) {

      //得到你选择的那个文件
      const file = e.target.files[0];
      console.log(file);
      //得到文件名称
      const fileName = file.name;
      this.pictureName = fileName;//给图片名赋值
      console.log(fileName);
      //得到文件后缀
      const fileExt = file.name.split('.').pop().toLocaleLowerCase();
      console.log(fileExt);

      this.imgResult = await this.forImgResult(file);//获得把上传的图片转换成 base64 的字符串
      this.url1=this.imgResult;//给展示的图片赋予base64格式的值

    },

    //请求后端接收 base64 的api接口（将 base64 的图片传输给后端）
    async addPicture(){
      let pictureId;//定义图片id
      //let testResult;
      await this.axios({
        method:"post",
        data:{ //在body中存放将 图片的 base64字符串作为 picture 的值来发送
          pictureName:this.pictureName,
          fileBase64:this.url1,
        },
        header:{
          'Content-Type':'application/json' //规定传递的参数格式为json
        },
        url:"http://127.0.0.1:8081/Bicycle/commodity/addCommodityPicture"
      }).then(response=>{
        this.pictureId=response.data;//将返回的图片id赋值回去
        console.log(this.pictureId);//输出 返回的图片id
        pictureId=this.pictureId;
      });
      return pictureId;
    },

  },
  //================== 从数据库中查询到 base64 图片编码（进入路由时查询） ======================
  beforeRouteEnter(to,from,next){
    //对该vue中的data操作的话就在 next 里操作，vm就相当于平时的 this
    next((vm)=>{

      let pictureBase64;
       axios({
        method:"get",
        params:{
          pictureId:vm.pictureId
        },
        url:"http://127.0.0.1:8081/Bicycle/commodity/selectCommodityPictureById"
      }).then(response=>{
        pictureBase64=response.data;
        vm.url1 = pictureBase64;
        console.log("获得图片了");
      });
    });
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
