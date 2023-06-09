package com.LW1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing //连接 MongoDB 数据库需要执行类添加 @EnableMongoAuditing 注解
public class BicycleProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BicycleProjectApplication.class, args);
    }

}
