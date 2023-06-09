package com.LW1.service.RepairService.RepairSQLService;

import com.LW1.entities.Repair.RepairItems;
import com.LW1.entities.Repair.RepairOrders;
import com.LW1.mapper.RepairOrdersModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

//维修模块 sql服务层
@Service
public class RepairSQLService implements RepairOrdersModelMapper {

    //获得被实现的 repairOrdersModelMapper 接口
    @Autowired
    RepairOrdersModelMapper repairOrdersModelMapper;

    //维修模块

    //维修项目模块
    //新增维修项目
    @Override
    public Integer addRepairItems(Map map) {
        return repairOrdersModelMapper.addRepairItems(map);
    }

    //修改维修项目
    @Override
    public Integer updateRepairItems(Map map) {
        Integer i = repairOrdersModelMapper.updateRepairItems(map);
        if (i.equals(1)){
            System.out.println("修改成功！");
            return 1;//修改成功
        }else if (i.equals(0)){
            System.err.println("没修改到！");
            return 0;//没修改到
        }else {
            System.err.println("修改了多条数据，有异常！");
            return 2;//修改了多条数据，有异常
        }
    }

    //删除维修项目
    @Override
    public Integer deleteRepairItems(Integer repairId) {
        Integer i = repairOrdersModelMapper.deleteRepairItems(repairId);
        if (i.equals(1)){
            System.out.println("删除成功！");
            return 1;
        }else if (i.equals(0)){
            System.err.println("没删除到！");
            return 0;
        }else {
            System.err.println("删除了多条数据，有异常！");
            return 2;
        }
    }

    //查询维修项目 按照 启用状态，项目名（模糊拆线呢），项目id 来查询
    @Override
    public List<RepairItems> selectRepairItemsByInfo(Map map) {
        return repairOrdersModelMapper.selectRepairItemsByInfo(map);
    }


    //维修清单模块
    //新增维修清单（配合 mongodb）
    @Override
    public Integer addRepairOrders(Map map) {
        return repairOrdersModelMapper.addRepairOrders(map);
    }

    //修改维修清单（不配合 mongodb，要加维修项目就申请新工单去）
    @Override
    public Integer updateRepairOrders(Map map) {
        Integer i = repairOrdersModelMapper.updateRepairOrders(map);
        if (i.equals(1)){
            System.out.println("修改成功！");
            return 1;
        }else if (i.equals(0)){
            System.err.println("没修改到！");
            return 0;
        }else {
            System.err.println("修改了多条数据，有问题！");
            return 2;
        }
    }

    //删除维修清单
    @Override
    public Integer deleteRepairOrders(Integer repairId) {
        Integer i = repairOrdersModelMapper.deleteRepairOrders(repairId);
        if (i.equals(1)){ //删除成功
            return 1;
        }else if (i.equals(0)){//没删除到
            return 0;
        }else {//删除了多条数据，有异常
            return 2;
        }
    }

    //查询维修清单 按照维修单id,申请维修的用户id，受理维修的管理员的id，维修单申请日期（模糊查询）,维修单状态
    @Override
    public List<RepairOrders> selectRepairOrdersByInfo(Map map) {
        return repairOrdersModelMapper.selectRepairOrdersByInfo(map);
    }
}
