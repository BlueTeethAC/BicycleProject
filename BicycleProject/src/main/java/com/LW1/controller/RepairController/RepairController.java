package com.LW1.controller.RepairController;

import com.LW1.entities.Repair.RepairItems;
import com.LW1.entities.Repair.RepairOrders;
import com.LW1.entities.Repair.RepairOrdersDetails;
import com.LW1.service.RepairService.RepairNoSqlService.RepairNoSqlService;
import com.LW1.service.RepairService.RepairSQLService.RepairSQLService;
import com.LW1.service.RepairService.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//repair 维修模块 controller 层
@CrossOrigin
@Controller
@RequestMapping("/repair")
public class RepairController {

    //获得 RepairSQLService 维修模块SQL服务层
    @Autowired
    RepairSQLService repairSQLService;

    //获得 RepairNoSQLService 维修模块NoSQL服务层
    @Autowired
    RepairNoSqlService repairNoSqlService;

    //获得 RepairService 维修模块（整合 nosql 和 sql）服务层
    @Autowired
    RepairService repairService;


    //维修模块

    //维修项目模块
    //新增维修项目  0：新增失败    1：新增成功
    @RequestMapping(value = "/addRepairItems",method = RequestMethod.GET)
    @ResponseBody
    public Integer addRepairItems(@PathParam("itemsName") String itemsName, @PathParam("itemsPrice") Float itemsPrice){
        Map<String,Object> map = new HashMap();
        map.put("itemsName",itemsName);
        map.put("itemsPrice",itemsPrice);

        Integer i = repairSQLService.addRepairItems(map);
        return i;
    }

    //修改维修项目    0：没修改到      1：修改成功      2：修改了多条数据，有异常
    @RequestMapping(value = "/updateRepairItems",method = RequestMethod.GET)
    @ResponseBody
    public Integer updateRepairItems(@PathParam("itemsName") String itemsName,
                                     @PathParam("itemsPrice") Float itemsPrice,
                                     @PathParam("status") Integer status,
                                     @PathParam("itemsId") Integer itemsId){


        Map<String,Object> map = new HashMap();
        map.put("itemsName",itemsName);
        map.put("itemsPrice",itemsPrice);
        map.put("status",status);
        map.put("itemsId",itemsId);

        Integer i = repairSQLService.updateRepairItems(map);

        return i;
    }

    //删除维修项目 0：没删除到，有问题     1：删除成功      2：删除了多条数据，有异常
    @RequestMapping(value = "/deleteRepairItems/{repairId}",method = RequestMethod.GET)
    @ResponseBody
    Integer deleteRepairItems(@PathVariable("repairId") Integer repairId){
        Integer i = repairSQLService.deleteRepairItems(repairId);
        return i;
    }

    //查询维修项目 按照 启用状态，项目名（模糊查询），项目id 来查询
    @RequestMapping("/selectRepairItemsByInfo")
    @ResponseBody
    List<RepairItems> selectRepairItemsByInfo(@PathParam("status") Integer status,@PathParam("itemsName") String itemsName,@PathParam("itemsId") Integer itemsId){
        Map<String,Object> map = new HashMap();
        map.put("status",status);
        map.put("itemsName",itemsName);
        map.put("itemsId",itemsId);
        List<RepairItems> repairItems = repairSQLService.selectRepairItemsByInfo(map);
        return repairItems;
    }


    //维修清单模块
    //新增维修清单（配合 mongodb）    0：新增失败      1：新增成功
    @RequestMapping(value = "/addRepairOrders",method = RequestMethod.POST)
    @ResponseBody
    public Integer addRepairOrders(@PathParam("userId1") Integer userId1,
                                  @PathParam("username1") String username1,
                                  @PathParam("userId2") Integer userId2,
                                  @PathParam("username2") String username2,
                                  @PathParam("date") Date date,
                                  @PathParam("sumPrice") Float sumPrice,
                                  @RequestBody List<RepairOrdersDetails> repairOrdersDetails){
        Map<String,Object> map = new HashMap();
        map.put("userId1",userId1);
        map.put("username1",username1);
        map.put("userId2",userId2);
        map.put("username2",username2);
        map.put("date",date);
        map.put("sumPrice",sumPrice);
        map.put("repairOrdersDetails",repairOrdersDetails);

        Integer i = repairService.addRepairOrders(map);
        return i;
    }

    //修改维修清单（不配合 mongodb，要加维修项目就申请新工单去）  0：没修改到     1；修改成功      2：修改了多条数据，有异常
    @RequestMapping(value = "/updateRepairOrders",method = RequestMethod.POST)
    @ResponseBody
    public Integer updateRepairOrders(@PathParam("repairId") Integer repairId,
                                      @PathParam("userId1") Integer userId1,
                                      @PathParam("username1") String username1,
                                      @PathParam("userId2") Integer userId2,
                                      @PathParam("username2") String username2,
                                      @PathParam("date") Date date,
                                      @PathParam("sumPrice") Float sumPrice,
                                      @PathParam("repairStatus") Integer repairStatus){

        Map<String,Object> map = new HashMap();
        map.put("repairId",repairId);
        map.put("userId1",userId1);
        map.put("username1",username1);
        map.put("userId2",userId2);
        map.put("username2",username2);
        map.put("date",date);
        map.put("sumPrice",sumPrice);
        map.put("repairStatus",repairStatus);

        Integer i = repairSQLService.updateRepairOrders(map);
        return i;
    }

    //删除维修清单    0：删除失败      1：删除成功      2：删除了多条数据，有异常
    @RequestMapping(value = "/deleteRepairOrders/{repairId}",method = RequestMethod.GET)
    @ResponseBody
    Integer deleteRepairOrders(@PathVariable("repairId") Integer repairId){
        Integer i = repairSQLService.deleteRepairOrders(repairId);
        return i;
    }

    //查询维修清单 按照维修单id,申请维修的用户id，受理维修的管理员的id，维修单申请日期（模糊查询）,维修单状态
    @RequestMapping(value = "/selectRepairOrdersByInfo",method = RequestMethod.GET)
    @ResponseBody
    List<RepairOrders> selectRepairOrdersByInfo(@PathParam("repairId") Integer repairId,
                                                @PathParam("userId1") Integer userId1,
                                                @PathParam("userId2") Integer userId2,
                                                @PathParam("date") Date date,
                                                @PathParam("repairStatus") Integer repairStatus){

        Map<String,Object> map =new HashMap();
        map.put("repairId",repairId);
        map.put("userId1",userId1);
        map.put("userId2",userId2);
        map.put("date",date);
        map.put("repairStatus",repairStatus);

        List<RepairOrders> repairOrders = repairSQLService.selectRepairOrdersByInfo(map);

        return repairOrders;
    }

    //按照id查询维修清单明细
    @RequestMapping(value = "/selectRepairOrdersDetails",method = RequestMethod.GET)
    @ResponseBody
    public List<RepairOrdersDetails> selectRepairOrdersDetails(@PathParam("id") String id){
        List<RepairOrdersDetails> repairOrdersDetails = repairService.selectRepairOrdersDetails(id);
        return repairOrdersDetails;
    }

}
