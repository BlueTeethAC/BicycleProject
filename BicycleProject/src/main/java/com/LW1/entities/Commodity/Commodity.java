package com.LW1.entities.Commodity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//商品实体类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commodity {

    private Integer commodityId;//商品id    int(2)
    private String commodityName;//商品名          varchar(60)
    private Integer commodityType;//商品类型     int(2)
    private String pictureId;//商品图片Id（存放在 mongodb 中）   varchar(100)
    private String introduce;//商品介绍，最多200字  varchar(400)
    private Integer stock;//商品存量                   int(2)
    private Integer sold;//是否在售  0：下架   1：销售中   2:即将销售   int(1)  默认为2
    private Float price;//售价            float(6,2)

}
