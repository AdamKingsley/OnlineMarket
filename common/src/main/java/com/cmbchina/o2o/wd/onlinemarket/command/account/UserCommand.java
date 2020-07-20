package com.cmbchina.o2o.wd.onlinemarket.command.account;


import com.cmbchina.o2o.wd.onlinemarket.constant.UserType;
import lombok.Data;

@Data
public class UserCommand {
    // 更新操作必传，创建可不传
    private String id;
    // 必传
    private UserType type;

    // 创建操作必传，更新操作可不传
    private String username;
    private String password;

    // 可以不传这些数据
    private String salt;
}
