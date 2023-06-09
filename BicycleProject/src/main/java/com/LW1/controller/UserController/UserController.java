package com.LW1.controller.UserController;

import com.LW1.entities.User.User;
import com.LW1.service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//user用户模块 controller 层
@CrossOrigin
@Controller
@RequestMapping("/user")
public class UserController {

    //获得 UserService
    @Autowired
    UserService userService;

    //新增用户   0：新增失败     1：新增成功    2：用户 和 购物车都新增成功   3：新增用户成功，购物车新增失败
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    @ResponseBody
    public Integer addUser(@PathParam("username") String username,@PathParam("password") String password,@PathParam("phoneNumber") String phoneNumber) {
        Map<String,Object> map = new HashMap();
        map.put("username",username);
        map.put("password",password);
        map.put("phoneNumber",phoneNumber);

        Integer i = userService.addUser(map);

        if (i.equals(1)){
            System.out.println("新增用户成功！");
        }else if (i.equals(0)){
            System.err.println("新增用户失败！");
        }else if (i.equals(2)){
            System.out.println("用户 和 购物车都新增成功！");
        }else {
            System.err.println("新增用户成功，购物车新增失败！");
        }
        return i;
    }

    //删除用户   0：删除成功      1：删除失败
    @RequestMapping(value = "/deleteUser",method = RequestMethod.POST)
    @ResponseBody
    public Integer deleteUser(Integer userId) {
        Integer i = userService.deleteUser(userId);
        if (i.equals(1)){
            System.out.println("删除成功！");
        }else {
            System.err.println("删除失败！");
        }
        return i;
    }

    //修改用户数据   0：修改失败       1：修改成功      2：修改了多条数据，出现异常
    @RequestMapping(value = "/upd" +
            "ateUser",method = RequestMethod.POST)
    @ResponseBody
    public Integer updateUser(@PathParam("userId") Integer userId,
                              @PathParam("username") String username,
                              @PathParam("password") String password,
                              @PathParam("phoneNumber") String phoneNumber,
                              @PathParam("role") Integer role,
                              @PathParam("status") Integer status) {

        Map<String,Object> map=new HashMap();
        map.put("userId",userId);
        map.put("username",username);
        map.put("password",password);
        map.put("phoneNumber",phoneNumber);
        map.put("role",role);
        map.put("status",status);

        Integer i = userService.updateUser(map);

        if (i.equals(1)){
            System.out.println("修改成功！");
            return 1;
        }else if (i.equals(0)){
            System.err.println("修改失败！");
            return 0;
        }else {
            System.err.println("修改了多条数据，出现异常！");
            return 2;
        }

    }

    //按照用户名查询用户信息（模糊查询）
    @RequestMapping(value = "/selectUserByName",method = RequestMethod.GET)
    @ResponseBody
    public List<User> selectUserByName(@PathParam("username") String username) {
        List<User> users = userService.selectUserByName(username);
        return users;
    }

    //按照用户id 查询用户信息
    @RequestMapping(value = "/selectUserById",method = RequestMethod.GET)
    @ResponseBody
    public List<User> selectUserById(@PathParam("userId") Integer userId) {
        List<User> user = userService.selectUserById(userId);
        return user;
    }

    //按照用户名查询用户信息
    @RequestMapping(value = "/selectUserByName1",method = RequestMethod.POST)
    @ResponseBody
    public User selectUserByName1(@PathParam("username") String username){
        User user = userService.selectByUserName1(username);
        return user;
    }

}
