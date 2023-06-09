package com.LW1.service.CommodityService.SQLService;

import com.LW1.entities.Commodity.Commodity;
import com.LW1.entities.Commodity.SaleList;
import com.LW1.entities.Commodity.ShoppingCart;
import com.LW1.mapper.CommodityModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

//商品与消费模块 (MySQL 部分)
@Service
public class CommoditySQLService implements CommodityModelMapper {

    //商品模块
    //获得 CommodityModelMapper
    @Autowired
    CommodityModelMapper commodityModelMapper;

    //商品模块
    //新增商品信息
    @Override
    public Integer addCommodity(Map map) {
        return commodityModelMapper.addCommodity(map);
    }

    //删除商品信息
    @Override
    public Integer deleteCommodity(Integer commodityId) {
        return commodityModelMapper.deleteCommodity(commodityId);
    }

    //修改商品信息
    @Override
    public Integer updateCommodity(Map map) {
        return commodityModelMapper.updateCommodity(map);
    }

    //按照商品名（模糊查询） 和 商品类型 查询商品信息（可以只输入单个条件，也可以都输入）
    @Override
    public List<Commodity> selectCommodityByName(Map map) {
        return commodityModelMapper.selectCommodityByName(map);
    }

    //按照id查询商品信息
    @Override
    public List<Commodity> selectCommodityById(Integer commodityId) {
        return commodityModelMapper.selectCommodityById(commodityId);
    }




    //销售清单模块
    //新增销售清单（配合 mongodb）
    @Override
    public Integer addSaleList(Map map) {
        return commodityModelMapper.addSaleList(map);
    }

    //删除销售清单
    @Override
    public Integer deleteSaleList(Integer saleId) {
        return commodityModelMapper.deleteSaleList(saleId);
    }

    //修改消费清单
    @Override
    public Integer updateSaleList(Map map) {
        return commodityModelMapper.updateSaleList(map);
    }

    //按照 消费者id ，消费日期（模糊查询），清单状态  查询数据 （配合 mongodb 先查询出所有明细到页面，当用户需要仔细进入某一条数据时再去 mongodb 查吧）
    @Override
    public List<SaleList> selectSaleListByInfo(Map map) {
        return commodityModelMapper.selectSaleListByInfo(map);
    }



    //购物车模块
    //新增购物车（配合 mongodb）
    @Override
    public Integer addShoppingCart(Map map) {
        return commodityModelMapper.addShoppingCart(map);
    }

    //按照id 查询购物车 （配合 mongodb）
    @Override
    public ShoppingCart selectShoppingCartById(Integer userId) {
        return commodityModelMapper.selectShoppingCartById(userId);
    }
}
