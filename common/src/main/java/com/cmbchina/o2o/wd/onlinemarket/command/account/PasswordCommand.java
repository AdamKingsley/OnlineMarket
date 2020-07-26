package com.cmbchina.o2o.wd.onlinemarket.command.account;

import lombok.Data;

@Data
public class PasswordCommand {
    private String password;
    private String confirmPassword;
}
