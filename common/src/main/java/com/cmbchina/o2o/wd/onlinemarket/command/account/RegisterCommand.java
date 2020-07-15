package com.cmbchina.o2o.wd.onlinemarket.command.account;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegisterCommand {
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @NotEmpty(message = "密码不能为空")
    private String password;
    @NotEmpty(message = "确认密码不能为空")
    private String confirmPassword;
    @NotEmpty(message = "邮箱不能为空")
    private String email;
    @NotEmpty(message = "手机号不能为空")
    private String telephone;
}
