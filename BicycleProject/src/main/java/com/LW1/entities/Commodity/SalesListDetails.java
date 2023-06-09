package com.LW1.entities.Commodity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//销售清单明细实体类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesListDetails {

    //private Integer  saleListDetailsId;//销售清单明细id   int(2)
    //private Integer saleId;//销售清单id int(2)
    private Integer commodityId;//商品id           int(2)
    private String commodityName;//商品名
    private Float price;//商品当时单价
    //private String commodity;//商品名
    private Integer sum;//该商品销售数量       int(2)
    private Float sumPrice;//该明细总价

}
