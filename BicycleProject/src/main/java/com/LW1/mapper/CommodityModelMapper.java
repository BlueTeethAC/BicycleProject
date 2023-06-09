package com.LW1.mapper;

import com.LW1.entities.Commodity.Commodity;
import com.LW1.entities.Commodity.SaleList;
import com.LW1.entities.Commodity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
//@Mapper 注解：表示了这是一个 mybatis 的 mapper 类     是 Dao 层的
//@Repository用在持久层的接口上，这个注解是将接口的一个实现类交给spring管理，与 @Controller，@Service 等注解 作用一样
public interface CommodityModelMapper {

    //商品与消费模块

    //商品模块
    //新增商品信息
    Integer addCommodity(Map map);

    //删除商品信息
    Integer deleteCommodity(Integer commodityId);

    //修改商品信息
    Integer updateCommodity(Map map);

    //按照商品名（模糊查询） 和 商品类型 查询商品信息
    List<Commodity> selectCommodityByName(Map map);

    //按照id查询商品信息
    List<Commodity> selectCommodityById(Integer commodityId);



    //销售清单模块
    //新增销售清单
    Integer addSaleList(Map map);

    //删除销售清单
    Integer deleteSaleList(Integer saleId);

    //修改消费清单
    Integer updateSaleList(Map map);

    //按照 消费者id ，消费日期（模糊查询），清单状态  查询数据
    List<SaleList> selectSaleListByInfo(Map map);



    //购物车模块
    //新增购物车
    Integer addShoppingCart(Map map);

    //按照id 查询购物车
    ShoppingCart selectShoppingCartById(Integer userId);

}
