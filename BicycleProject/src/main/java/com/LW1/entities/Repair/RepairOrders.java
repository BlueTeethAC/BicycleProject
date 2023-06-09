package com.LW1.entities.Repair;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

//维修单实体类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairOrders {

    private Integer repairId;//维修单id    int(2)
    private String userId1;//申请维修的用户id     int(2)
    private String username1;//申请维修的用户名        varchar(20)
    private String userId2;//受理维修的管理员的id        int(2)
    private String username2;//受理维修的管理员的用户名     varchar(20)
    private String repairOrdersDetailsId;//维修清单明细id
    private Date date;//维修单申请日期                         Date
    private Float sumPrice;//维修单总价                          float(7,2)
    private Integer repairStatus;//维修单状态 0：申请中   1：已撤销   2：已受理    3：维修中    4：待领取    5.已领取完成  默认为0  int(1)

}
