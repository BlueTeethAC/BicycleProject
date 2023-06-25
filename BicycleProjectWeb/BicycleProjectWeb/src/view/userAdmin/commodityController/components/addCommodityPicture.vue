<template>

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

</template>

<script>
//新增商品图片的组件
export default {
  name: "addCommodity",
  data() {
    return {
      url1: "",//图片路径
      filelist:[],
      dialogMode:'create',//create、edit
      pictureId:"",//图片存放在数据库中的id
      imgResult:"",
      pictureName:""
    };
  },
  methods: {
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
    async addPicture(picture){
      let pictureId;//定义图片id
      //let testResult;
      await this.axios({
        method:"post",
        data:{ //在body中存放将 图片的 base64字符串作为 picture 的值来发送
            pictureName:this.pictureName,
            fileBase64:this.imgResult
        },
        header:{
          'Content-Type':'application/json' //规定传递的参数格式为json
        },
        // url:"http://127.0.0.1:8081/Bicycle/commodity/addCommodityPicture"
        url:"/BicycleProject/Bicycle/commodity/addCommodityPicture"
      }).then(response=>{
        this.pictureId=response.data;//将返回的图片id赋值回去
        console.log(this.pictureId);//输出 返回的图片id
        pictureId=this.pictureId;
      });
      return pictureId;
    },

    //获得imgResult
    getImgResult(){
      return this.imgResult;
    }

  }
}
</script>

<style scoped>


</style>
