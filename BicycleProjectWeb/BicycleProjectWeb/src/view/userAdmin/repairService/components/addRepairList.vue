<template>

  <div>

    <!-- 按钮 -->
    <el-button type="info" style="float: left;margin-left: 10px" v-on:click="$router.push('/userAdmin/repairService')">返回上一页</el-button>

    <hr style="float: right;width: 1600px;margin-top: 10px;margin-bottom: 10px"/>


    <div id="container">
      <div id="content">
        <form action="#" method="post" >
          <!--@input="(v)=>(aaa=v.replace(/[^\d.]/g,''))"-->
          <br/>
          <br/>
          <br/>
          <br/>

          <!-- 多选框 -->
          <!--<el-checkbox-group v-model="checkList" >
            <el-checkbox
              v-for="items in repairInfo"
              :key="items.itemsId"
              :label="items.itemsName"
              :value="items.itemsId"></el-checkbox>
          </el-checkbox-group>-->

          <!-- @select="onTableSelect" ：设置被选中和被取消时触发的函数   当用户手动勾选数据行的 Checkbox 时触发的事件-->
          <!-- selection-change ：当选择项发生变化时会触发该事件-->
          <!-- @select-all：当用户勾选全选时触发的函数 -->
          <!-- :header-cell-class-name="cellClass"：去掉表头全选框用的 -->
          <el-table
            :header-cell-style="{background:'#909399',color:'#ffd04b'}"
            ref="multipleTable"
            :data="repairInfo"
            tooltip-effect="dark"
            style="width: 60%;margin-left: 20%;margin-right: 20%;"
            :summary-method="getSummaries"
            show-summary
            @select-all="selectAll"
            @select="onTableSelect"
            @selection-change="handleSelectionChange">

            <el-table-column
              type="selection"
              width="70px"
            >
            </el-table-column>

            <el-table-column
              prop="itemsId"
              label="维修项目Id"
              width="300"
              align="center"
            >
            </el-table-column>

            <el-table-column
              prop="itemsName"
              label="维修项目名称"
              width="300"
              align="center"
            >
            </el-table-column>

            <el-table-column
              prop="itemsPrice"
              label="维修项目价格"
              width="290px"
              align="center"
              :formatter="rounding"
            >
            </el-table-column>

            <el-table-column
              prop="status"
              label="维修项目状态"
              show-overflow-tooltip
              v-if="false"
            >
            </el-table-column>

          </el-table>

          <div style="margin-top: 20px">
            <!--<el-button @click="toggleSelection([tableData[1], tableData[2]])">切换第二、第三行的选中状态</el-button>-->
            <el-button type="info" @click="toggleSelection()">取消选择</el-button>
            <el-button type="primary" @click="subbmitList(userId1,username1,currentTime(),checkList,sumPrice)">新增申请</el-button>
          </div>

          <br/>

          <!--<el-button type="info" v-on:click="dialogVisible1Button">新增商品</el-button>-->

        </form>
      </div>
    </div>


    <!--<el-dialog
      title="提示"
      :visible.sync="dialogVisible1"
      width="30%">
      <span>{{infoMessage}}</span>
      <span slot="footer" class="dialog- footer">
        <el-button type="primary"  @click="addCommodity()">确定</el-button>
        <el-button type="primary"  @click="cancelButton()">取消</el-button>
      </span>
    </el-dialog>-->


  </div>

</template>

<script>
//引入 axios
import axios from "axios";
//引入 vuex 的配置
import vuex from "@/vuex";

//新增未维修申请
export default {
  name: "addRepairList",
  data(){
    return{
      checkList:null,//用于接收选中的数据
      repairInfo:null,//维修项目信息
      sumPrice:0,//总价格
      userId1:vuex.state.userId,
      username1:vuex.state.username
    }
  },

  async beforeRouteEnter(to,from,next){
    let repairItemsInfo;
    await axios({
      path:"get",
      params:{
        status:1
      },
      // url:"http://127.0.0.1:8081/Bicycle/repair/selectRepairItemsByInfo"
      url:"/BicycleProject/Bicycle/repair/selectRepairItemsByInfo"
    }).then(response=>{
      repairItemsInfo=response.data;
    })

    next((vm)=>{
      //没登陆，就跳到登陆页面
      if (vuex.state.username == null){
        vm.$router.push("/loginView/login")
      };
      vm.repairInfo=repairItemsInfo;//获得维修项目数据
      console.log(vm.repairInfo);
      let set = new Set;
      vm.checkList=set;
    });
  },

  methods:{
    //全选改成全清除
    selectAll() {
      this.sumPrice=0;//全部取消的话就把总价变为0
      this.checkList=null;//清空 checkList
      this.$refs.multipleTable.clearSelection();
    },

    //去掉全选框用的
    cellClass(row, column, rowIndex, columnIndex) {
      if (row.columnIndex === 0) {
        return 'seltAllbtnDis';
      }
    },

    //让展示的数据保留两位小数用
    rounding(row,column) {
      return parseFloat(row[column.property]).toFixed(2);
    },

    //被选中时的方法
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.sumPrice=0;//全部取消的话就把总价变为0
        this.$refs.multipleTable.clearSelection();
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },

    //总和表格方法
    getSummaries(param) {
      const { columns, data } = param;
      const sums = [];
      columns.forEach((column, index) => {
        if (index === 0) { //第一列变成总价
          sums[index] = '总价';
          return;
        }else if(index != 3){ //不是第四列的话就不计算总值
          return;
        }

        sums[3]=parseFloat(this.sumPrice).toFixed(2);

      });

      return sums;
    },

    //获得被选中的数据价格（当当前行被选中时，会触发这个函数）
    onTableSelect(rows, row) {
      let selected = rows.length && rows.indexOf(row) !== -1

      //let set = new Set;//定义一个set集合
      //set=this.checkList;
      //console.log(selected)  // true就是选中，0或者false是取消选中
      //console.log(row)
      if (selected===true){ //为 true 是被选中
        let itemsInfo={ //实例化一个类
          itemsId:row.itemsId,
          itemsName:row.itemsName,
          price:row.itemsPrice
        }

        this.checkList.add(itemsInfo);
        console.log("当前行被选中了");
        this.sumPrice+=row.itemsPrice;
        console.log("当前总价格为"+this.sumPrice);
      }else if (selected===0){ //为 0 是取消选中
        this.checkList.delete(itemsInfo);
        console.log("当前行被取消了");
        this.sumPrice-=row.itemsPrice;
        console.log("当前总价格为"+this.sumPrice);
      }
      console.log(this.checkList);
      return selected;
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
      console.log("strDate",strDate);
      return strDate;
    },

    //提交维修申请
    async subbmitList(userId1,username1,date,checkList,sumPrice){
      let i;
      await this.axios({
        method:"post",
        params:{
          userId1:userId1,
          username1:username1,
          date:date,
          sumPrice:sumPrice
        },
        data:
          checkList
        ,
        // url:"http://127.0.0.1:8081/Bicycle/repair/addRepairOrders"
        url:"/BicycleProject/Bicycle/repair/addRepairOrders"
      }).then(response=>{
        i=response.data;
        if (i===1){
          console.log("新增成功！");
          this.$router.push("/userAdmin/repairService");
        }else{
          alert("新增失败！");
        }
      })
    }
  }
}
</script>

<style scoped>


</style>
