package com.LW1.service.CommodityService;

import com.LW1.entities.Commodity.SaleList;
import com.LW1.entities.Commodity.SalesListAllDetails;
import com.LW1.entities.Commodity.SalesListDetails;
import com.LW1.entities.Commodity.ShoppingCart;
import com.LW1.service.CommodityService.NoSqlService.CommodityNoSQLService;
import com.LW1.service.CommodityService.SQLService.CommoditySQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//商品与消费模块 服务层
//整合 nosql 和 sql 的商品与消费模块 的服务
@Service
public class CommodityService {

    //获得商品与消费模块 SQL Service
    @Autowired
    CommoditySQLService commoditySQLService;

    //获得商品与消费模块 NoSql Service
    @Autowired
    CommodityNoSQLService commodityNoSQLService;


    //购物车模块

    //新增购物车   0：新增失败       1：新增成功
    public Integer addShoppingCart(Integer userId){
        //先在 mongodb 中创建 购物车信息
        SalesListAllDetails shoppingCart = commodityNoSQLService.createShoppingCart();
        String shoppingCartId = shoppingCart.getId();//获得购物车数据对应的id

        //获得要添加购物车的 用户id
        //Integer userId=user.getUserId();

        //将传来的 用户id 和 数据 id 放入 map 集合
        Map<String,Object> map = new HashMap();
        map.put("userId",userId);
        map.put("detailsId",shoppingCartId);

        Integer i = commoditySQLService.addShoppingCart(map);
        if (i.equals(1)){
            return 1;//新增成功
        }else {
            return 0;//新增失败
        }
    }


    //查询购物车信息
    public List<SalesListDetails> selectShoppingCart(Integer userId){
        //先用用户id 查询出对应的 购物车信息
        ShoppingCart shoppingCart = commoditySQLService.selectShoppingCartById(userId);
        //获得购物车明细id
        String shoppingCartId = shoppingCart.getDetailsId();

        //查询出购物车明细
        List<SalesListAllDetails> salesListAllDetails = commodityNoSQLService.selectShoppingCartById(shoppingCartId);

        //获取其中的 SalesListAllDetails 类
        SalesListAllDetails salesListAllDetails1 = salesListAllDetails.get(0);
        //获取其中的 明细集合
        List<SalesListDetails> salesListDetails = salesListAllDetails1.getListAllDetails();

        return salesListDetails;//返回明细集合
    }


    //修改购物车信息   1：修改成功     其他数字：修改出现异常
    public Integer updateShoppingCart(SalesListAllDetails salesListAllDetails){
        Integer i = commodityNoSQLService.updateShoppingCart(salesListAllDetails);
        return i;
    }



    //消费清单模块

    //新增消费清单模块
    public Integer addSaleList(Map map){
        //从 map 集合中提取 明细集合 salesListDetails
        Object salesListDetails=map.get("salesListDetails");
        //将 salesListDetails 转换为 List<SalesListDetails>
        List<SalesListDetails> salesListDetails1 = (List<SalesListDetails>) salesListDetails;

        //用提供的数据实例化一个没有 id 的 SalesListAllDetails 对象
        SalesListAllDetails salesListAllDetails = new SalesListAllDetails(null, salesListDetails1);

        //执行查询并返回获得id 的  salesListAllDetails1
        SalesListAllDetails salesListAllDetails1 = commodityNoSQLService.addSaleListDetails(salesListAllDetails);

        System.out.println("新增消费清单明细数据："+salesListAllDetails1);

        //获得 salesListAllDetails1 的id
        String detailsId = salesListAllDetails1.getId();

        //将获得的id 插入 map 集合
        map.put("detailsId",detailsId);

        //执行mysql新增 消费清单操作
        Integer i = commoditySQLService.addSaleList(map);

        if (i.equals(1)){
            return 1;//插入成功
        }else {
            return 0;//插入失败
        }
    }


}
