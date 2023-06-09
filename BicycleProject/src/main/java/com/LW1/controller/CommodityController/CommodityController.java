package com.LW1.controller.CommodityController;

import com.LW1.entities.Commodity.*;
import com.LW1.entities.Picture.Picture;
import com.LW1.entities.Picture.PictureInfo;
import com.LW1.service.CommodityService.CommodityService;
import com.LW1.service.CommodityService.NoSqlService.CommodityNoSQLService;
import com.LW1.service.CommodityService.SQLService.CommoditySQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//commodity商品与消费模块 controller 层
@CrossOrigin
@Controller
@RequestMapping("/commodity")
public class CommodityController {

    //获得 商品与消费模块服务类 CommodityService
    @Autowired
    CommodityService commodityService;

    //获得 商品与消费模块 NoSql服务类 CommodityNoSQLService
    @Autowired
    CommodityNoSQLService commodityNoSQLService;

    //获得 商品与消费模块 Sql服务类 CommodityNoSQLService
    @Autowired
    CommoditySQLService commoditySQLService;



    //商品模块
    //新增商品图片
    @ResponseBody
    @RequestMapping(value = "/addCommodityPicture", method = RequestMethod.POST)
    public String uploadImage(@RequestBody Picture picture){

        //输入图片base64编码
        String id = commodityNoSQLService.addCommodityPicture(picture.getFileBase64());
        //返回的 id 为存放在数据库中的id

        return id;
    }

    //根据商品图片id查询图片Base64 编码
    @ResponseBody
    @RequestMapping(value = "/selectCommodityPictureById",method = RequestMethod.GET)
    public String selectImage(@PathParam("pictureId") String pictureId){
        //返回的 s 就是 图片的 base64 编码
        String s = commodityNoSQLService.selectCommodityPicture(pictureId);
        return s;
    }



    //新增商品信息（图片先暂时放一放）   返回  1：新增成功         0：新增失败
    @RequestMapping(value = "/addCommodity",method = RequestMethod.POST)
    @ResponseBody
    public Integer addCommodity(@PathParam("commodityName") String commodityName,
                                @PathParam("commodityType") Integer commodityType,
                                @PathParam("pictureId") String pictureId,
                                @PathParam("introduce") String introduce,
                                @PathParam("stock") Integer stock,
                                //@PathParam("sold") Integer sold,
                                @PathParam("price") Float price) {
        Map<String,Object> map=new HashMap();
        map.put("commodityName",commodityName);
        map.put("commodityType",commodityType);
        map.put("pictureId",pictureId);
        map.put("introduce",introduce);
        map.put("stock",stock);
        //map.put("sold",sold);
        map.put("price",price);

        Integer i = commoditySQLService.addCommodity(map);

        return i;
    }

    //删除商品信息   返回   1：删除成功      0：删除失败
    @RequestMapping(value = "/deleteCommodity/{commodityId}",method = RequestMethod.GET)
    @ResponseBody
    public Integer deleteCommodity(@PathVariable("commodityId") Integer commodityId) {
        Integer i = commoditySQLService.deleteCommodity(commodityId);
        if (i.equals(1)){
            return 1;
        }else {
            return 0;
        }
    }

    //修改商品信息   0：没修改到   1：修改成功      2：修改了多条数据有问题
    @RequestMapping(value = "/updateCommodity",method = RequestMethod.POST)
    @ResponseBody
    public Integer updateCommodity(@PathParam("commodityName") String commodityName,
                                   @PathParam("commodityType") Integer commodityType,
                                   @PathParam("pictureId") String pictureId,
                                   @PathParam("introduce") String introduce,
                                   @PathParam("stock") Integer stock,
                                   @PathParam("sold") Integer sold,
                                   @PathParam("price") Float price,
                                   @PathParam("commodityId") Integer commodityId) {
        Map<String,Object> map = new HashMap();
        map.put("commodityName",commodityName);
        map.put("commodityType",commodityType);
        map.put("pictureId",pictureId);
        map.put("introduce",introduce);
        map.put("stock",stock);
        map.put("sold",sold);
        map.put("price",price);
        map.put("commodityId",commodityId);

        Integer i = commoditySQLService.updateCommodity(map);

        if (i.equals(1)){
            return 1;//修改成功
        }else if (i.equals(0)){
            return 0;//没修改到
        }else {
            return 2;//修改了多条数据，有问题
        }
    }

    //按照商品名（模糊查询） 和 商品类型 查询商品信息（可以只输入单个条件，也可以都输入）
    @RequestMapping(value = "/selectCommodityByName",method = RequestMethod.GET)
    @ResponseBody
    public List<Commodity> selectCommodityByName(@PathParam("commodityName") String commodityName,@PathParam("commodityType") Integer commodityType) {

        Map<String,Object> map =new HashMap();
        map.put("commodityName",commodityName);
        map.put("commodityType",commodityType);

        List<Commodity> commodities = commoditySQLService.selectCommodityByName(map);
        return commodities;
    }

    //按照id查询商品信息
    @RequestMapping(value = "/selectCommodityById/{commodityId}",method = RequestMethod.GET)
    @ResponseBody
    public List<Commodity> selectCommodityById(@PathVariable("commodityId") Integer commodityId) {
        List<Commodity> commodities = commoditySQLService.selectCommodityById(commodityId);
        return commodities;
    }





    //销售清单模块
    //新增销售清单（配合 mongodb）   0：插入失败      1：插入成功
    //成功样例：新增消费清单明细数据：SalesListAllDetails(id=645665a9efd1141edf1c18aa, listAllDetails=[SalesListDetails(commodityId=99, commodityName=测试商品1, price=100.0, sum=9, sumPrice=900.0), SalesListDetails(commodityId=98, commodityName=测试商品2, price=10.0, sum=9, sumPrice=90.0), SalesListDetails(commodityId=97, commodityName=测试商品3, price=0.5, sum=9, sumPrice=4.5)])
    @RequestMapping(value = "/addSaleList",method = RequestMethod.POST)
    @ResponseBody
    public Integer addSaleList(@PathParam("userId") Integer userId,
                               @PathParam("username") String username,
                               @PathParam("sumPrice") Float sumPrice,
                               @PathParam("saleTime") Date saleTime,
                               //@PathParam("listStatus") Integer listStatus,
                               @RequestBody List<SalesListDetails> salesListDetails) {

        Map<String,Object> map =new HashMap();
        map.put("userId",userId);
        map.put("username",username);
        map.put("sumPrice",sumPrice);
        map.put("saleTime",saleTime);
        //map.put("listStatus",listStatus);
        map.put("salesListDetails",salesListDetails);

        Integer i = commodityService.addSaleList(map);

        return i;
    }

    //删除销售清单    0：没删除到      1：删除成功      2：删除异常
    @RequestMapping(value = "/deleteSaleList/{saleId}",method = RequestMethod.GET)
    @ResponseBody
    public Integer deleteSaleList(@PathVariable("saleId") Integer saleId) {
        Integer i = commoditySQLService.deleteSaleList(saleId);
        if (i.equals(1)){
            return 1;//删除成功
        }else if (i.equals(0)){
            return 0;//没删除到
        }else {
            return 2;//删除异常
        }
    }

    //修改消费清单  0：没修改到    1：修改成功      2：有异常，修改了多条数据
    @RequestMapping(value = "/updateStatus",method = RequestMethod.POST)
    @ResponseBody
    public Integer updateSaleList(@PathParam("listStatus") Integer listStatus,
                                  @PathParam("username") String username,
                                  @PathParam("detailsId") Integer detailsId,
                                  @PathParam("sumPrice") Float sumPrice,
                                  @PathParam("saleTime") Date saleTime,
                                  @PathParam("userId") Integer userId,
                                  @PathParam("saleId") Integer saleId) {
        Map<String,Object> map =new HashMap();
        map.put("listStatus",listStatus);
        map.put("username",username);
        map.put("detailsId",detailsId);
        map.put("sumPrice",sumPrice);
        map.put("saleTime",saleTime);
        map.put("userId",userId);
        map.put("saleId",saleId);

        Integer i = commoditySQLService.updateSaleList(map);

        if (i.equals(1)){
            return 1;//修改成功
        }else if (i.equals(0)){
            return 0;//没修改到
        }else {
            return 2;//有异常，修改了多条数据
        }
    }

    //按照 消费者id ，消费日期（模糊查询），清单状态  查询数据 （配合 mongodb 先查询出所有明细到页面，当用户需要仔细进入某一条数据时再去 mongodb 查吧）
    @RequestMapping(value = "/selectSaleListByInfo",method = RequestMethod.GET)
    @ResponseBody
    public List<SaleList> selectSaleListByInfo(@PathParam("userId") Integer userId,
                                               @PathParam("saleTime") Date saleTime,
                                               @PathParam("listStatus") Integer listStatus,
                                               @PathParam("saleId") Integer saleId) {
        Map<String,Object> map = new HashMap();
        map.put("userId",userId);
        map.put("saleTime",saleTime);
        map.put("listStatus",listStatus);
        map.put("saleId",saleId);

        List<SaleList> saleLists = commoditySQLService.selectSaleListByInfo(map);
        return saleLists;
    }

    //按照 消费清单明细id查询对应明细
    //成功样例：[SalesListAllDetails(id=645665a9efd1141edf1c18aa, listAllDetails=[SalesListDetails(commodityId=99, commodityName=测试商品1, price=100.0, sum=9, sumPrice=900.0), SalesListDetails(commodityId=98, commodityName=测试商品2, price=10.0, sum=9, sumPrice=90.0), SalesListDetails(commodityId=97, commodityName=测试商品3, price=0.5, sum=9, sumPrice=4.5)])]
    @RequestMapping(value = "/selectSaleListDetails/{detailsId}",method = RequestMethod.GET)
    @ResponseBody
    public SalesListAllDetails selectSaleListDetails(@PathVariable("detailsId") String detailsId){
        SalesListAllDetails salesListAllDetails = commodityNoSQLService.selectSaleListDetails(detailsId);
        return salesListAllDetails;
    }





    //购物车模块
    //查询购物车信息
    @RequestMapping(value = "/selectShoppingCart/{userId}",method = RequestMethod.GET)
    @ResponseBody
    public List<SalesListDetails> selectShoppingCart(@PathVariable("userId") Integer userId){
        List<SalesListDetails> salesListDetails = commodityService.selectShoppingCart(userId);
        return salesListDetails;
    }


    //修改购物车信息   1：修改成功     0：没修改到数据     2：修改了多条数据       3：修改的数据量与匹配查询条件的数据量不一致
    //@RequestBody List<SalesListDetails> salesListDetails：@RequestBody 用于接收body中的json字符串，并非params中，此处接收的是 List<SalesListDetails> 类型的数据
    @RequestMapping(value = "/updateShoppingCart",method = RequestMethod.POST)
    @ResponseBody
    public Integer updateShoppingCart(@PathParam("userId") Integer userId,@RequestBody List<SalesListDetails> salesListDetails){

        ShoppingCart shoppingCart = commoditySQLService.selectShoppingCartById(userId);//用 userId 查询出 对应id的购物车实体类
        String detailsId = shoppingCart.getDetailsId();//获得对应的购物车明细id

        //JSONArray salesListDetails1 = JSON.parseArray(salesListDetails);
        //List<SalesListDetails> salesListDetails2 = salesListDetails1.toJavaList(SalesListDetails.class);

        //实例化一个 SalesListAllDetails 类
        SalesListAllDetails salesListAllDetails = new SalesListAllDetails(detailsId, salesListDetails);

        Integer i = commodityService.updateShoppingCart(salesListAllDetails);

        return i;
    }

}
