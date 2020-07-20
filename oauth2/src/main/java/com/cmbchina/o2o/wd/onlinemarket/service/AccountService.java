package com.cmbchina.o2o.wd.onlinemarket.service;

import com.cmbchina.o2o.wd.onlinemarket.command.account.RegisterCommand;
import com.cmbchina.o2o.wd.onlinemarket.command.account.UserInfoCommand;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;


public interface AccountService {
    // login通过spring security来控制
    // Result login(LoginCommand command);

    // 注册，admin管控，分为管理员，商户和普通的消费者三类人（暂时由admin来创建用户）
    Result register(RegisterCommand command);

    Result obtainUser(Principal user);

    Result update(UserInfoCommand command, HttpServletRequest request);
}
