import Vue from "vue"
import Router from "vue-router"

import LoginView from "../view/loginAdmin/LoginView"
import login from "../view/loginAdmin/components/login"
import register from "../view/loginAdmin/components/register"
import indexView from "../view/indexAdmin/indexView"
import userAdminView from "../view/userAdmin/userAdminView"
import userController from "../view/userAdmin/userController/userController"
import updateUser from "../view/userAdmin/userController/components/updateUser"
import repairItemsController from "../view/userAdmin/repairItemsController/repairitemsController"
import addRepairItems from "../view/userAdmin/repairItemsController/components/addRepairItems"
import updateRepairItems from "../view/userAdmin/repairItemsController/components/updateRepairItems"
import commodityController from "../view/userAdmin/commodityController/commodityController"
import addCommodity from "../view/userAdmin/commodityController/components/addCommodity"
import updateCommodity from "../view/userAdmin/commodityController/components/updateCommodity"
import repairService from "../view/userAdmin/repairService/repairService"
import addRepairList from "../view/userAdmin/repairService/components/addRepairList"
import repairListInfo from "../view/userAdmin/repairService/components/repairListInfo"
import repairServiceAdmin from "../view/userAdmin/repairService/repairServiceAdmin"
import shoppingCart from "../view/indexAdmin/shoppingService/shoppingCart"
import shoppingList from "../view/indexAdmin/shoppingService/shoppingList"
import shoppingListDetails from "../view/indexAdmin/shoppingService/components/shoppingListDetails"
import shoppingView from "../view/indexAdmin/shoppingService/shoppingView"
import shoppingDetailsView from "../view/indexAdmin/shoppingService/shoppingDetailsView"
import shoppingListAdmin from "../view/userAdmin/shoppingListController/shoppingListAdmin"

Vue.use(Router)


export default new Router(
  {

    mode:"history",

    routes:[
      {
        //登陆界面 router
        path:"/loginView",
        component:LoginView,
        name:"LoginView",
        props:true,//开启 props 接收参数名
        children:[ //配置嵌套路由
          {
            //登录组件 router
            path:"/loginView/login",
            component:login,
            name:"login",
            props:true
          },
          {
            //注册组件 router
            path:"/loginView/register",
            component: register,
            name:"register",
            props: true
          }
        ]
      },
      {
        //主页 router
        path:"/indexView",
        component:indexView,
        name:"indexView",
        props:true
      },
      {
        //用户功能首页
        path:"/userAdminView",
        component:userAdminView,
        name:"userAdminView",
        props:true,
        children:[
          {
            //用户管理
            path:"/userAdminView/userController",
            component:userController,
            name:"userController",
            props:true,
          },
          { //具体修改用户信息
            path:"/userAdminView/userController/updateUser/:userId1/:username1/:phoneNumber1/:role1/:status1",
            component:updateUser,
            name:"updateUser",
            props:true
          },
          {
            //维修项目管理
            path: "/userAdminView/repairItemsController",
            component:repairItemsController,
            name:"repairItemsController",
            props:true,
            children:[
              {
                //新增维修项目的弹窗
                path:"/userAdminView/repairItemsController/addRepairItems",
                component:addRepairItems,
                name:"addRepairItems",
                props:true
              },
              {
                //修改维修项目的弹窗
                path: "/userAdminView/repairItemsController/updateRepairItems/:repairItemsId/:repairItemsName/:repairItemsPrice/:repairItemsStatus",
                component:updateRepairItems,
                name: "updateRepairItems",
                props: true
              }
            ]
          },
          {
            //商品管理页面
            path:"/userAdminView/commodityController",
            component: commodityController,
            name:"commodityController",
            props: true,
          },
          {
            //新增商品页面
            path:"/userAdmin/commodityController/addCommodity",
            component:addCommodity,
            name:"addCommodity",
            props:true
          },
          {
            //修改商品页面
            path:"/userAdmin/commodityController/updateCommodity/:commodityId,/:commodityName/:commodityType/:pictureId/:introduce/:stock/:sold/:price",
            component:updateCommodity,
            name:"updateCommodity",
            props:true
          },
          {
            //维修服务页面
            path: "/userAdmin/repairService",
            component:repairService,
            name:"repairService",
            props:true
          },
          {
            //申请维修服务页面
            path:"/userAdmin/addRepairList",
            component: addRepairList,
            name:"addRepairList",
            props: true
          },
          {
            //查询维修清单
            path: "/userAdmin/repairListInfo/:repairId",
            component: repairListInfo,
            name:"repairListInfo",
            props:true
          },
          {
            //维修清单审批
            path:"/userAdmin/repairServiceAdmin",
            component:repairServiceAdmin,
            name:"repairServiceAdmin",
            props:true
          },
          {
            //购物车页面
            path:"/shoppingCart",
            component: shoppingCart,
            name:"shoppingCart",
            props:true
          },
          {
            //消费清单界面
            path:"/shoppingList",
            component: shoppingList,
            name:"shoppingList",
            props: true
          },
          {
            //消费清单明细
            path:"/shoppingListDetails/:detailsId",
            component:shoppingListDetails,
            name:"shoppingListDetails",
            props:true
          },
          {
            //消费清单管理页面
            path: "/shoppingListAdmin",
            component: shoppingListAdmin,
            name:"shoppingListAdmin",
            props: true
          }
        ]
      },
      {
        //商品页面
        path:"/shoppingView",
        component:shoppingView,
        name:"shoppingView",
        props:true
      },
      {
        //商品详细信息页面
        path:"/shoppingDetailsView/:commodityId",
        component:shoppingDetailsView,
        name:"shoppingDetailsView",
        props:true
      }
    ]
  }
)
