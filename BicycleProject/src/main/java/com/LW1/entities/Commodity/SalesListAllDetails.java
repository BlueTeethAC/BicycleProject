package com.LW1.entities.Commodity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//包装了 消费清单明细实体类的  消费清单全部明细类
//对应 mongodb 数据中的 存放方式
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesListAllDetails {

    //id必须要叫id，这样插入 mongodb 时，才可以拿到返回的 id，否则会被视为 单纯插入的属性，无法接受到
    private String id;//存放于 mongodb 中的数据id
    private List<SalesListDetails> listAllDetails;//将整个清单所有 明细 变成列表作为属性

}
