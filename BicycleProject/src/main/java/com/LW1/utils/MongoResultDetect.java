package com.LW1.utils;

import com.mongodb.client.result.UpdateResult;
import org.bson.BsonValue;

//mongodb 操作结果数据检测工具类
public class MongoResultDetect {

    //Mongodb 更新结果检测
    public Integer updateDetect(UpdateResult updateResult){
        Long matchedCount = updateResult.getMatchedCount();//获得匹配的数据数量
        System.out.println("匹配的数据数量："+matchedCount);
        Long modifiedCount = updateResult.getModifiedCount();//获得被修改的数据的数量
        System.out.println("获得被修改的数据的数量："+modifiedCount);
        BsonValue upsertedId = updateResult.getUpsertedId();//更新的插入标识
        System.out.println("更新的插入标识："+upsertedId);

        if (matchedCount.equals(modifiedCount)){ //如果两者数量相等
            if (matchedCount==0){
                return 0;//没有匹配的数据
            }else if (matchedCount >= 1){
                return 2;//修改了多条数据
            }else {
                return 1;//修改了一条数据
            }
        }else {
            return 3;// 匹配的数据数量 与 被修改的数据的数量 不一致
        }
    }

}
