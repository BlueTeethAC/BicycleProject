package com.LW1.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//用户实体类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer userId;//用户id       int(2)
    private String username;//用户名       varchar(20)
    private String password;//密码          varchar(20)
    private String phoneNumber;//电话号码      char(11)
    private Integer role;//角色类型 0：顾客  1：管理员  2：最高管理员  int(1)
    private Integer status;//是否启用 0：未启用   1：已启用 （启用中的账户用户名不可以重复）

}
