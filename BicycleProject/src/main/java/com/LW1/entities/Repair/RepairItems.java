package com.LW1.entities.Repair;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//维修项目实体类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairItems {

    private Integer itemsId;//维修项目id    int(2)
    private String itemsName;//维修项目名      varchar(40)
    private Float itemsPrice;//维修项目价目表     float(6,2)
    private Integer status;//是否启用 0:未启用   1：已启用

}
