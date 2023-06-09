package com.LW1.entities.Commodity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//购物车实体类 （一个用户只有一个）
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {

    private Integer userId;//用户id
    private String detailsId;//对应消费明细数据id

}
