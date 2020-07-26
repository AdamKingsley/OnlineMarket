package com.cmbchina.o2o.wd.onlinemarket.command.account;

import com.cmbchina.o2o.wd.onlinemarket.command.PageCommand;
import com.cmbchina.o2o.wd.onlinemarket.constant.UserType;
import lombok.Data;

@Data
public class UserFilterCommand extends PageCommand {
    private String username;
    private String email;
    private String telephone;
    private UserType type;
    private Integer  status;
}
