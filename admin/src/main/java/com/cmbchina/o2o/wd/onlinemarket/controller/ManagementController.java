package com.cmbchina.o2o.wd.onlinemarket.controller;


import com.cmbchina.o2o.wd.onlinemarket.command.account.UserCommand;
import com.cmbchina.o2o.wd.onlinemarket.command.account.UserFilterCommand;
import com.cmbchina.o2o.wd.onlinemarket.dto.PageResult;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.service.ManagementService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("account")
public class ManagementController {
    @Resource
    private ManagementService managementService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

    // 这里请求都加request的原因是
    // 需要验证作此操作的用户是admin
    @GetMapping("/list")
    public PageResult userList(@ModelAttribute UserFilterCommand command) {
        return managementService.getUserList(command, request);
    }

    @DeleteMapping("/{userId}")
    public Result removeUser(@PathVariable String userId) {
        return managementService.frozenUser(userId, request);
    }

    @PostMapping("/add")
    public Result addUser(@RequestBody UserCommand command) {
        return managementService.addUser(command, request);
    }

    @PutMapping("/update")
    public Result updateUser(@RequestBody UserCommand command) {
        return managementService.updateUser(command, request);
    }

    @PostMapping("/reset")
    public Result resetUser(@RequestBody UserCommand command) {
        return managementService.resetUser(command, request);
    }


}
