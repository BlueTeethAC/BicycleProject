package com.LW1.service.RepairService;

import com.LW1.entities.Repair.RepairOrdersAllDetails;
import com.LW1.entities.Repair.RepairOrdersDetails;
import com.LW1.service.RepairService.RepairNoSqlService.RepairNoSqlService;
import com.LW1.service.RepairService.RepairSQLService.RepairSQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

//维修模块 服务层
//整合 nosql 和 sql 的商品与消费模块 的服务
@Service
public class RepairService {

    //获得 RepairNoSqlService 维修模块NoSql 服务层
    @Autowired
    RepairNoSqlService repairNoSqlService;

    //获得 RepairSQLService 维修模块SQL 服务层
    @Autowired
    RepairSQLService repairSQLService;

    //维修清单模块

    //新增维修清单
    public Integer addRepairOrders(Map map){
        //获得维修单明细集合实体类
        Object repairOrdersDetails = map.get("repairOrdersDetails");
        //将 repairOrdersDetails 转换为 List<RepairOrdersDetails> 类
        List<RepairOrdersDetails> repairOrdersDetails1=(List<RepairOrdersDetails>) repairOrdersDetails;
        //实例化一个 RepairOrdersAllDetails，id为空，用于接收 mongodb 自动生成的 id
        RepairOrdersAllDetails repairOrdersAllDetails = new RepairOrdersAllDetails(null, repairOrdersDetails1);
        //执行 RepairNoSqlService 中的新增维修清单明细方法，返回一个获得了id 的 RepairOrdersAllDetails 类
        RepairOrdersAllDetails repairOrdersAllDetails1 = repairNoSqlService.addRepairOrdersDetails(repairOrdersAllDetails);
        //输出验证一下
        System.out.println("新增维修单明细数据："+repairOrdersAllDetails1);
        if (repairOrdersAllDetails.getId() == null){
            System.err.println("未获取到明细id");
        }

        //获得 repairOrdersAllDetails1 的 id
        String repairOrdersDetailsId = repairOrdersAllDetails1.getId();
        //将 id 填入 map集合
        map.put("repairOrdersDetailsId",repairOrdersDetailsId);
        //执行 RepairSQLService 中的新增维修清单方法，返回 i
        Integer i = repairSQLService.addRepairOrders(map);

        if (i.equals(1)){
            System.out.println("维修单新增成功！");
        }else {
            System.err.println("维修单新增失败！");
        }

        return i;//0：新增失败   1：新增成功
    }

    //查询维修清单明细数据
    public List<RepairOrdersDetails> selectRepairOrdersDetails(String id){
        //执行 repairNoSqlService 的查询维修清单明细数据方法
        RepairOrdersAllDetails repairOrdersAllDetails = repairNoSqlService.selectRepairOrdersDetails(id);
        //获得维修清单明细数据 repairOrdersDetails
        List<RepairOrdersDetails> repairOrdersDetails = repairOrdersAllDetails.getRepairOrdersDetails();
        return repairOrdersDetails;
    }

}
