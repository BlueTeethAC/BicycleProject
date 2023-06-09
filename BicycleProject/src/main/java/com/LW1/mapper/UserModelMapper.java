package com.LW1.mapper;

import com.LW1.entities.User.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

//用户模块
@Repository
@Mapper
//@Mapper 注解：表示了这是一个 mybatis 的 mapper 类     是 Dao 层的
//@Repository用在持久层的接口上，这个注解是将接口的一个实现类交给spring管理，与 @Controller，@Service 等注解 作用一样
public interface UserModelMapper {

    //注册模块

    //新增用户
    Integer addUser(Map map);

    //删除用户
    Integer deleteUser(Integer userId);

    //修改用户
    Integer updateUser(Map map);

    //查询用户按照用户名（模糊查询）
    List<User> selectUserByName(String username);

    //查询用户按照用户名
    User selectByUserName1(String username);

    //查询用户按照id
    List<User> selectUserById(Integer userId);


}
