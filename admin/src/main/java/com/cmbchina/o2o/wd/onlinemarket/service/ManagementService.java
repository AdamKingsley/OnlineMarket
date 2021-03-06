package com.cmbchina.o2o.wd.onlinemarket.service;

import com.cmbchina.o2o.wd.onlinemarket.command.account.PasswordCommand;
import com.cmbchina.o2o.wd.onlinemarket.command.account.UserCommand;
import com.cmbchina.o2o.wd.onlinemarket.command.account.UserFilterCommand;
import com.cmbchina.o2o.wd.onlinemarket.dto.PageResult;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;

import javax.servlet.http.HttpServletRequest;

public interface ManagementService {
    PageResult getUserList(UserFilterCommand command, HttpServletRequest request);

    Result addUser(UserCommand command, HttpServletRequest request);

    Result updateUser(UserCommand command, HttpServletRequest request);

    @Deprecated
    Result resetUser(UserCommand command, HttpServletRequest request);

    Result removeUser(String userId, HttpServletRequest request);

    Result changePassword(PasswordCommand command, HttpServletRequest request);
}
