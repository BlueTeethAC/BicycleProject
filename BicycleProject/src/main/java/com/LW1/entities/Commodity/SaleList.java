package com.LW1.entities.Commodity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

//销售清单实体类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleList {

    private Integer saleId;//销售清单id         int(2)
    private String userId;//消费者id               int(2)
    private String username;//消费者用户名                varchar(20)
    private String detailsId;//对应消费明细数据id
    private Float sumPrice;//总价                 float(7,2)
    private Date saleTime;//销售日期            date
    private Integer listStatus;//清单状态     0：申请中  1：备货中   2：待领取    3：已完成     int(1)

}
