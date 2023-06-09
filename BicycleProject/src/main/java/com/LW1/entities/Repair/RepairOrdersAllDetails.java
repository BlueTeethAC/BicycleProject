package com.LW1.entities.Repair;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//维修单明细集合实体类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairOrdersAllDetails {

    String id;//维修单明细集合id
    List<RepairOrdersDetails> repairOrdersDetails;//维修单明细集合

}
