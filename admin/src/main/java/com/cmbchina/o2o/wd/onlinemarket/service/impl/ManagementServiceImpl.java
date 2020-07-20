package com.cmbchina.o2o.wd.onlinemarket.service.impl;

import com.cmbchina.o2o.wd.onlinemarket.command.account.UserCommand;
import com.cmbchina.o2o.wd.onlinemarket.command.account.UserFilterCommand;
import com.cmbchina.o2o.wd.onlinemarket.constant.ResultStatus;
import com.cmbchina.o2o.wd.onlinemarket.dto.PageResult;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.mapper.UserMapper;
import com.cmbchina.o2o.wd.onlinemarket.service.ManagementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class ManagementServiceImpl implements ManagementService {

    @Resource
    private UserMapper userMapper;

    @Override
    public PageResult getUserList(UserFilterCommand command, HttpServletRequest request) {
        // TODO implement
        return PageResult.success();
    }

    @Override
    public Result frozenUser(String userId, HttpServletRequest request) {
        // TODO implement
        return Result.success().setStatus(ResultStatus.OPERATION_SUCEES);
    }

    @Override
    public Result addUser(UserCommand command, HttpServletRequest request) {
        // TODO implement
        return Result.success().setStatus(ResultStatus.OPERATION_SUCEES);
    }

    @Override
    public Result updateUser(UserCommand command, HttpServletRequest request) {
        // TODO implement
        return Result.success().setStatus(ResultStatus.OPERATION_SUCEES);
    }

    @Override
    public Result resetUser(UserCommand command, HttpServletRequest request) {
        // TODO implement
        return Result.success().setStatus(ResultStatus.OPERATION_SUCEES);
    }
}
