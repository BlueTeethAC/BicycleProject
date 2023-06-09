package com.LW1.service.CommodityService.NoSqlService;

import com.LW1.entities.Commodity.SalesListAllDetails;
import com.LW1.entities.Commodity.SalesListDetails;
import com.LW1.entities.Picture.PictureInfo;
import com.LW1.utils.MongoResultDetect;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//商品与消费模块 (NoSQL 部分)
@Service
public class CommodityNoSQLService {

    //获得 MongoTemplate 类
    //用 @Resource 吧，用 @Autowired 有时候会失灵
    @Resource
    MongoTemplate mongoTemplate;


    //商品模块

    //新增商品图片并返回图片id   输入 图片的 base64 编码
    public String addCommodityPicture(String base64Picture){
        PictureInfo pictureInfo = new PictureInfo(null, base64Picture);//实例化一个 pictureInfo 类
        ArrayList arrayList = new ArrayList();
        arrayList.add(pictureInfo);
        Collection<Object> commodityPicture = mongoTemplate.insert(arrayList, "CommodityPicture");//将 pictureInfo 类放到 CommodityPicture 表中

        //实例化一个空的 pictureInfo1 类用于接收返回数据，其中包含id
        PictureInfo pictureInfo1=null;

        //遍历获得返回的数据集，然后强转类型获得其中的id
        for (Object picture:commodityPicture) {
            pictureInfo1 = (PictureInfo)picture;
            break;//因为集合里只有这一条数据，所以提取第一条直接break了
        }

        String id=pictureInfo1.getId();

        System.out.println("成功插入图片，图片id为："+id);

        return id;
    }

    //根据id查询商品图片的 base64编码
    public String selectCommodityPicture(String id){
        //使用 mongodb 的_id 查询的话，id需要先用 ObjectId 转换
        ObjectId objectId=new ObjectId(id);
        //配置查询语句
        Criteria criteria = Criteria.where("_id").is(objectId);
        //生成查询条件
        Query query = Query.query(criteria);
        //执行查询语句
        List<PictureInfo> pictureInfos = mongoTemplate.find(query, PictureInfo.class, "CommodityPicture");
        //System.err.println(shoppingCart);
        //因为按id查询的，所以只会有一条数据
        PictureInfo pictureInfo = pictureInfos.get(0);//取出当中的唯一一条数据，并返回
        return pictureInfo.getBase64Picture();
    }





    //购物车模块

    //新增购物车明细（在新建用户时候一起创建）
    public SalesListAllDetails createShoppingCart(){

        List<SalesListDetails> nullList=new ArrayList();

        SalesListAllDetails salesListAllDetails = new SalesListAllDetails(null,nullList);
        ArrayList arrayList = new ArrayList();
        arrayList.add(salesListAllDetails);
        Collection<Object> shoppingCartDetails = mongoTemplate.insert(arrayList, "ShoppingCart");
        System.out.println("已经创建用户对应的购物车：\n"+shoppingCartDetails);

        //将包含id的返回值 shoppingCartDetails，提取出其中的 salesListAllDetails1 并且转换为 SalesListAllDetails 类型
        SalesListAllDetails salesListAllDetails1 = null;
        //遍历获得返回的数据集，然后强转类型获得其中的id
        for (Object shoppingCart:shoppingCartDetails) {
            salesListAllDetails1 = (SalesListAllDetails)shoppingCart;
            break;//因为集合里只有这一条数据，所以提取第一条直接break了
        }

        return salesListAllDetails1;
    }


    //修改购物车明细
    public Integer updateShoppingCart(SalesListAllDetails salesListAllDetails){
        //配置匹配条件，当 _id 为 消费明细包装类SalesListAllDetails 的 id 时，覆盖明细数据
        Criteria criteria = Criteria.where("_id").is(salesListAllDetails.getId());
        Query query = Query.query(criteria);//生成查询语句

        //生成修改语句
        //Update update = Update.update("shoppingCartDetails", salesListAllDetails.getListAllDetails());
        Update update = Update.update("listAllDetails", salesListAllDetails.getListAllDetails());

        //mongoTemplate.updateFirst(query对象, update对象, 查询结果对应的实体类.class,要操作的集合名);    此处操作的是 ShoppingCart 购物车集合
        UpdateResult shoppingCart = mongoTemplate.updateFirst(query, update, SalesListAllDetails.class, "ShoppingCart");

        //使用工具类检测 修改结果
        Integer i = new MongoResultDetect().updateDetect(shoppingCart);
        System.err.println(i);

        if (i.equals(1)){
            return 1;//成功修改对应的数据
        }else {
            System.err.println("修改购物车明细出现问题！！！");
            return i;//修改出现问题
        }
    }


    //根据id查询购物车明细
    public List<SalesListAllDetails> selectShoppingCartById(String shoppingCartDetailsId){

        //System.out.println(shoppingCartDetailsId);
        ObjectId objectId = new ObjectId(shoppingCartDetailsId);
        //System.out.println(objectId);

        //使用 _id 查询，需要将 id 用 objectId() 转换为对应的 objectId 查询才行
        //配置匹配条件，当 _id 为 消费明细包装类SalesListAllDetails 的 id 时
        Criteria criteria = Criteria.where("_id").is(objectId);
        Query query = Query.query(criteria);

        //List<Object> shoppingCart1 = mongoTemplate.find(query, Object.class, "ShoppingCart");
        //System.err.println(shoppingCart1);

        List<SalesListAllDetails> shoppingCart = mongoTemplate.find(query, SalesListAllDetails.class, "ShoppingCart");

        //System.out.println(shoppingCart);

        return shoppingCart;
    }





    //销售清单模块

    //新增销售清单明细
    public SalesListAllDetails addSaleListDetails(SalesListAllDetails salesListDetails){
        ArrayList arrayList = new ArrayList();
        arrayList.add(salesListDetails);
        //mongoTemplate.insert(要插入的数据集, 要插入到的集合名)
        Collection saleListDetails = mongoTemplate.insert(arrayList, "SaleListDetails");

        //初始化 salesListAllDetails11，用于接收返回的包含id的 SalesListAllDetails 对象
        SalesListAllDetails salesListAllDetails11 = null;

        //返回的 saleListDetails 就是插入的数据
        for(Object salesListAllDetails:saleListDetails){
            salesListAllDetails11=(SalesListAllDetails) salesListAllDetails;
            break;//因为集合里只有这一条数据，所以提取第一条直接break了
        }

        return salesListAllDetails11;
    }


    //按照 消费清单明细id查询对应明细
    public SalesListAllDetails selectSaleListDetails(String id){
        //使用 mongodb 的_id 查询的话，id需要先用 ObjectId 转换
        ObjectId objectId=new ObjectId(id);
        //配置查询语句
        Criteria criteria = Criteria.where("_id").is(objectId);
        //生成查询条件
        Query query = Query.query(criteria);
        //执行查询语句
        List<SalesListAllDetails> shoppingCart = mongoTemplate.find(query, SalesListAllDetails.class, "SaleListDetails");
        //System.err.println(shoppingCart);
        //因为按id查询的，所以只会有一条数据
        SalesListAllDetails salesListAllDetails = shoppingCart.get(0);//取出当中的唯一一条数据，并返回
        return salesListAllDetails;
    }

}
