<template>


  <div>

      <!-- 按钮 -->
      <el-button type="info" style="float: left;margin-left: 10px" v-on:click="$router.push('/userAdmin/repairService')" v-if="nowRole === 0">返回上一页</el-button>
      <el-button type="info" style="float: left;margin-left: 10px" v-on:click="$router.push('/userAdmin/repairServiceAdmin')" v-if="nowRole !=0">返回上一页</el-button>

      <hr style="float: right;width: 1600px;margin-top: 10px;margin-bottom: 10px"/>


      <div id="container">
        <div id="content">
          <form action="#" method="post" >
            <!--@input="(v)=>(aaa=v.replace(/[^\d.]/g,''))"-->
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <div class="div_tip">维修清单Id：{{repairId}}<el-input v-model="repairId" style="width: 200px" placeholder="请输入维修清单Id" :disabled="true" v-if="false"></el-input></div><br/>

            <div class="div_tip" v-if="false">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;申请人Id：<el-input v-model="userId1" style="width: 200px;" placeholder="申请人Id" :disabled="true"></el-input></div>

            <div class="div_tip" v-if="false">&nbsp;&nbsp;&nbsp;&nbsp;申请人用户名：<el-input v-model="username1" style="width: 200px" placeholder="申请人用户名" :disabled="true"></el-input></div>

            <div class="div_tip" v-if="false">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;承接人Id：<el-input v-model="userId2" style="width: 200px;" placeholder="承接人Id" :disabled="true"></el-input></div>

            <div class="div_tip">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;承接人用户名：{{username2}}<el-input v-model="username2" style="width: 200px" placeholder="承接人用户名" :disabled="true" v-if="false"></el-input></div><br/>

            <div class="div_tip">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;申请日期：{{date}}<el-input v-model="date" style="width: 200px;" placeholder="申请日期" :disabled="true" v-if="false"></el-input></div><br/>

            <!-- onkeyup="value=value.match(/\d+\.?\d{0,2}/,'')" 限制输入框只能输入两位小数 -->
            <div class="div_tip" :formatter="rounding">&nbsp;&nbsp;&nbsp;&nbsp;维修单总金额：{{sumPrice}}<el-input  style="width: 200px" placeholder="维修单总金额" v-model="sumPrice" onkeyup="value=value.replace(/[^\d]/g,'')" :disabled="true" v-if="false"></el-input></div><br/>

            <div class="div_tip">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;申请状态：
              <span v-if="repairStatus === 0">申请中</span>
              <span v-else-if="repairStatus === 1">已撤销</span>
              <span v-else-if="repairStatus === 2">已受理</span>
              <span v-else-if="repairStatus === 3">维修中</span>
              <span v-else-if="repairStatus === 4">待领取</span>
              <span v-else-if="repairStatus === 5">已领取</span>
              <el-input v-model="repairStatus"  style="width: 200px" placeholder="申请状态" :disabled="true" v-if="false"></el-input>
            </div>

            <div class="div_tip" v-if="false">维修清单明细Id：<el-input v-model="repairOrdersDetailsId" style="width: 200px" placeholder="维修清单明细Id" :disabled="true"></el-input></div>

            <br/>
            <br/>

            <!--表格部分-->
            <div id="table1">
              <el-table
                :header-cell-style="{background:'#909399',color:'#ffd04b'}"
                :data="repairOrdersDetails"
                :row-style="{background:'#E4E7ED'}"
                style="width: 100%">
                <el-table-column
                  fixed
                  prop="itemsId"
                  label="维修项目Id"
                  width="500px"
                  align="center"
                >
                </el-table-column>
                <el-table-column
                  prop="itemsName"
                  label="维修项目名"
                  width="600px"
                  align="center"
                >
                </el-table-column>

                <!-- :formatter="rounding"：绑定下方展示两位小数的方法 -->
                <el-table-column
                  prop="price"
                  label="维修项目价格"
                  width="500px"
                  align="center"
                  :formatter="rounding"
                >
                </el-table-column>

              </el-table>
            </div>



            <br/>

            <!--<el-button type="info" v-on:click="dialogVisible1Button">新增商品</el-button>-->

          </form>
        </div>
      </div>

  </div>

</template>

<script>
import vuex from "@/vuex"
import axios from "axios";

//查看维修单
export default {
  name: "repairListInfo",
  props:["repairId"],
  data(){
    return{
      nowRole:null,//当前权限等级
      repairOrdersInfo:null, //维修清单数据
      repairOrdersDetailsId:null, //维修清单明细Id
      repairOrdersDetails:null, //维修清单明细数据
      repairId:null, //维修清单Id
      userId1:null, //申请人Id
      username1:null, //申请人用户名
      userId2:null, //承接人Id
      username2:null, //承接人用户名
      date:null, //申请日期
      repairStatus:null, //申请单状态
      sumPrice:null, //维修清单总金额

      itemsId:null, //维修项目Id
      itemsName:null, //维修项目名称
      price:null //维修项目价格

    }
  },
  methods:{
    //让展示的数据保留两位小数用
    rounding(row,column) {
      return parseFloat(row[column.property]).toFixed(2)
    },
  },

  //进入路由时执行
  async beforeRouteEnter(to,from,next){
    let repairOrdersDetailsId;

    next((vm)=>{
      let repairId = vm.repairId;

      //通过 repairId 查询出维修清单数据
      axios({
        method:"get",
        params:{
          repairId:repairId
        },
        url:"http://127.0.0.1:8081/Bicycle/repair/selectRepairOrdersByInfo"
      }).then(response=>{
        //返回的数据是只有一个 RepairOrders元素 的 RepairOrders集合
        //所以需要从用 RepairOrders[0] 才能获得其中的 RepairOrders 元素
        vm.repairOrdersInfo=response.data;

        vm.repairOrdersDetailsId = vm.repairOrdersInfo[0].repairOrdersDetailsId;
        repairOrdersDetailsId=vm.repairOrdersDetailsId;
        //console.log(repairOrdersDetailsId)

        //给元素赋值
        vm.userId1 = vm.repairOrdersInfo[0].userId1;

        vm.username1 = vm.repairOrdersInfo[0].username1;

        vm.userId2 = vm.repairOrdersInfo[0].userId2;

        vm.username2 = vm.repairOrdersInfo[0].username2;

        vm.date = vm.repairOrdersInfo[0].date;

        vm.repairStatus = vm.repairOrdersInfo[0].repairStatus;

        vm.sumPrice = vm.repairOrdersInfo[0].sumPrice;


        //通过 id 查询出维修清单明细数据
        axios({
          method:"get",
          params:{
            id:repairOrdersDetailsId
          },
          url:"http://127.0.0.1:8081/Bicycle/repair/selectRepairOrdersDetails"
        }).then(response=>{
          vm.repairOrdersDetails = response.data;
          vm.nowRole=vuex.state.role;
          //vm.repairOrdersDetails：查询得到的维修清单明细
        });

      });

    });

  }
}
</script>

<style scoped>

</style>
