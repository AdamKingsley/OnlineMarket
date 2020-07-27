package com.cmbchina.o2o.wd.onlinemarket.service;

import com.cmbchina.o2o.wd.onlinemarket.command.order.OrderCommand;
import com.cmbchina.o2o.wd.onlinemarket.command.order.OrderFilterCommand;
import com.cmbchina.o2o.wd.onlinemarket.command.order.OrderGoodsCommand;
import com.cmbchina.o2o.wd.onlinemarket.dto.PageResult;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {
    PageResult getOrderList(OrderFilterCommand command, HttpServletRequest request);

    Result getOrderDetail(Long id, HttpServletRequest request);

    Result addOrder(OrderCommand command, HttpServletRequest request);

    Result updateOrder(OrderCommand command, HttpServletRequest request);

    Result removeOrder(Long id, HttpServletRequest request);

    Result lockRepository(OrderGoodsCommand command);
}
