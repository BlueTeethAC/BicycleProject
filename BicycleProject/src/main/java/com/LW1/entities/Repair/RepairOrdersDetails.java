package com.LW1.entities.Repair;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//维修单明细实体类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairOrdersDetails {

    //private Integer repairOrdersDetailsId;//维修单明细id   int(2)
    //private Integer repairId;//对应维修单id
    private Integer itemsId;//维修项目id   int(2)
    private String itemsName;//维修项目名
    private Float price;//维修项目价格

}
