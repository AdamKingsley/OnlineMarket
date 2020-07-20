package com.cmbchina.o2o.wd.onlinemarket.controller;


import com.cmbchina.o2o.wd.onlinemarket.command.account.RegisterCommand;
import com.cmbchina.o2o.wd.onlinemarket.command.account.UserInfoCommand;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.service.AccountService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/oauth")
public class AccountController {
    @Resource
    private AccountService accountService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

    // @PostMapping("/login")
    // public Result login(@RequestBody LoginCommand command, HttpServletRequest request, HttpServletResponse response) {
    //     return accountService.login(command);
    // }


    /**
     * @param user 鉴权认证后的信息
     * @return 包含用户信息的Result数据
     */
    @GetMapping("/user")
    public Result user(Principal user) {
        return accountService.obtainUser(user);
    }

    /**
     * @param command 用户注册的基本信息
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result register(@RequestBody @Valid RegisterCommand command) {
        return accountService.register(command);
    }

    @PutMapping("/update")
    public Result register(@RequestBody @Valid UserInfoCommand command) {
        return accountService.update(command,request);
    }
}
