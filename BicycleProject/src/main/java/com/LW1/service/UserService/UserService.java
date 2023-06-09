package com.LW1.service.UserService;

import com.LW1.entities.User.User;
import com.LW1.mapper.UserModelMapper;
import com.LW1.service.CommodityService.CommodityService;
import com.LW1.service.CommodityService.NoSqlService.CommodityNoSQLService;
import com.LW1.service.CommodityService.SQLService.CommoditySQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

//user用户模块service层
@Service
public class UserService implements UserModelMapper {

    //获得 UserModelMapper
    @Autowired
    UserModelMapper userModelMapper;

    //CommodityNoSQLService
    @Autowired
    CommodityService commodityService;

    //新增用户
    @Override
    public Integer addUser(Map map) {
        //执行新增用户
        Integer i = userModelMapper.addUser(map);

        //获得 username
        String username = String.valueOf(map.get("username"));

        //查询出对应的 user
        User user = selectByUserName1(username);

        if (i.equals(1)){ //新增用户成功
            Integer j = commodityService.addShoppingCart(user.getUserId()); //新增购物车   0：新增失败       1：新增成功
            if (j.equals(1)){
                return 2;//用户 和 购物车都新增成功
            }else {
                return 3;//新增用户成功，购物车新增失败
            }
        }else {//新增用户失败
            return 0;
        }
    }

    //删除用户
    @Override
    public Integer deleteUser(Integer userId) {
        return userModelMapper.deleteUser(userId);
    }

    //修改用户数据
    @Override
    public Integer updateUser(Map map) {
        return userModelMapper.updateUser(map);
    }

    //按照用户名查询用户信息（模糊查询）
    @Override
    public List<User> selectUserByName(String username) {
        return userModelMapper.selectUserByName(username);
    }

    //按照用户id 查询用户信息
    @Override
    public List<User> selectUserById(Integer userId) {
        return userModelMapper.selectUserById(userId);
    }

    //按照用户名查询用户信息
    @Override
    public User selectByUserName1(String username) {
        return userModelMapper.selectByUserName1(username);
    }
}
