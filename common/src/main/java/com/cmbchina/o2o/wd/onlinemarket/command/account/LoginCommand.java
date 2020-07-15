package com.cmbchina.o2o.wd.onlinemarket.command.account;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginCommand {
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @NotEmpty(message = "登录密码不能为空")
    private String password;
    @JSONField(name = "client_secret")
    private String secret;
    @JSONField(name = "client_id")
    private String client;
    @JSONField(name = "grant_type")
    private String type;
}
