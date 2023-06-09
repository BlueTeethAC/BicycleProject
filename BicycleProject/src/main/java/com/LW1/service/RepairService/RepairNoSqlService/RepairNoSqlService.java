package com.LW1.service.RepairService.RepairNoSqlService;

import com.LW1.entities.Repair.RepairOrdersAllDetails;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//维修模块 nosql(mongodb) 服务层
@Service
public class RepairNoSqlService {

    //获得 MongoTemplate 类
    //用 @Resource 吧，用 @Autowired 有时候会失灵
    @Resource
    MongoTemplate mongoTemplate;

    //维修清单模块

    //新增维修清单明细
    public RepairOrdersAllDetails addRepairOrdersDetails(RepairOrdersAllDetails repairOrdersAllDetails){
        //创建一个集合，因为 mongodb 插入必须用 list 的形式
        ArrayList arrayList = new ArrayList();
        arrayList.add(repairOrdersAllDetails);
        //mongoTemplate.insert(要插入的数据集, 要插入到的集合名)
        Collection repairOrdersDetails = mongoTemplate.insert(arrayList, "RepairOrdersDetails");

        //初始化 repairOrdersAllDetails1，用于接收返回的包含id的 RepairOrdersAllDetails 对象
        RepairOrdersAllDetails repairOrdersAllDetails1=null;

        //Collection 集合只能用遍历来获得数据，此处只新增一条，所以也是返回一条，
        //遍历Collection集合，取得其中唯一一条数据，转换类型赋值给 repairOrdersAllDetails1,
        //赋值完后就break，因为只有一条数据
        for (Object details:repairOrdersDetails){
            repairOrdersAllDetails1 = (RepairOrdersAllDetails) details;
            break;
        }
        //输出一下数据，用于验证下
        System.out.println("新增的维修清单明细为："+repairOrdersAllDetails1);

        return repairOrdersAllDetails1;
    }


    //按照 维修清单明细id 查询维修清单明细
    public RepairOrdersAllDetails selectRepairOrdersDetails(String id){
        //使用后 mongodb  的 _id 查询的话，id需要先用 ObejctId 转换
        ObjectId objectId = new ObjectId(id);
        //配置查询语句
        Criteria criteria = Criteria.where("_id").is(objectId);
        //生成查询语句
        Query query = Query.query(criteria);
        //执行查询语句
        List<RepairOrdersAllDetails> repairOrdersDetails = mongoTemplate.find(query, RepairOrdersAllDetails.class, "RepairOrdersDetails");
        //因为是按照 id 查询的，所以只会有一条数据
        RepairOrdersAllDetails repairOrdersAllDetails = repairOrdersDetails.get(0);
        return repairOrdersAllDetails;
    }

}
