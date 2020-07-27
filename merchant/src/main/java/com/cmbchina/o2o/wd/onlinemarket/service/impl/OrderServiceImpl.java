package com.cmbchina.o2o.wd.onlinemarket.service.impl;

import com.cmbchina.o2o.wd.onlinemarket.command.order.OrderCommand;
import com.cmbchina.o2o.wd.onlinemarket.command.order.OrderFilterCommand;
import com.cmbchina.o2o.wd.onlinemarket.constant.ResultStatus;
import com.cmbchina.o2o.wd.onlinemarket.dto.PageResult;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.mapper.OrderDetailMapper;
import com.cmbchina.o2o.wd.onlinemarket.mapper.OrderMapper;
import com.cmbchina.o2o.wd.onlinemarket.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderDetailMapper orderDetailMapper;

    @Override
    public PageResult getOrderList(OrderFilterCommand command, HttpServletRequest request) {
        //TODO implement
        return PageResult.success();
    }

    @Override
    public Result getOrderDetail(Long id, HttpServletRequest request) {
        //TODO implement
        return Result.success().setStatus(ResultStatus.OPERATION_SUCCESS);
    }

    @Override
    public Result updateOrder(OrderCommand command, HttpServletRequest request) {
        //TODO implement
        return Result.success().setStatus(ResultStatus.OPERATION_SUCCESS);
    }
}
