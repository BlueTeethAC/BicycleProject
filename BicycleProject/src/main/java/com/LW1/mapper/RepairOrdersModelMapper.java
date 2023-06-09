package com.LW1.mapper;

import com.LW1.entities.Repair.RepairItems;
import com.LW1.entities.Repair.RepairOrders;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
//@Mapper 注解：表示了这是一个 mybatis 的 mapper 类     是 Dao 层的
//@Repository用在持久层的接口上，这个注解是将接口的一个实现类交给spring管理，与 @Controller，@Service 等注解 作用一样
public interface RepairOrdersModelMapper {

    //维修模块

    //维修项目模块
    //新增维修项目
    Integer addRepairItems(Map map);

    //修改维修项目
    Integer updateRepairItems(Map map);

    //删除维修项目
    Integer deleteRepairItems(Integer repairId);

    //查询维修项目 按照 启用状态，项目名（模糊拆线呢），项目id 来查询
    List<RepairItems> selectRepairItemsByInfo(Map map);


    //维修清单模块
    //新增维修清单（配合 mongodb）
    Integer addRepairOrders(Map map);

    //修改维修清单（不配合 mongodb，要加维修项目就申请新工单去）
    Integer updateRepairOrders(Map map);

    //删除维修清单
    Integer deleteRepairOrders(Integer repairId);

    //查询维修清单 按照维修单id,申请维修的用户id，受理维修的管理员的id，维修单申请日期（模糊查询）,维修单状态
    List<RepairOrders> selectRepairOrdersByInfo(Map map);


}
