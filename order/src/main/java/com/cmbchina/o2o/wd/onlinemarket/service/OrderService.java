package com.cmbchina.o2o.wd.onlinemarket.service;

import com.cmbchina.o2o.wd.onlinemarket.command.order.*;
import com.cmbchina.o2o.wd.onlinemarket.dto.PageResult;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;

import javax.servlet.http.HttpServletRequest;

public interface OrderService {
    PageResult getOrderList(OrderFilterCommand command, HttpServletRequest request);

    Result getOrderDetail(String code, HttpServletRequest request);

    Result addOrder(OrderCommand command, HttpServletRequest request);

    // 原则上不允许删除订单
    @Deprecated
    Result removeOrder(Long id, HttpServletRequest request);

    Result lockRepository(OrderGoodsCommand command);

    Result updateOrderState(OrderAttrUpdateCommand command, HttpServletRequest request);

    Result updateOrderInfo(OrderUpdateCommand command, HttpServletRequest request);

    Result updateAllOrderState(OrderUpdateAllCommand command, HttpServletRequest request);
}
