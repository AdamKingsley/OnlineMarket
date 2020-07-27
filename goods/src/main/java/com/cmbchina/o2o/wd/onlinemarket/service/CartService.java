package com.cmbchina.o2o.wd.onlinemarket.service;

import com.cmbchina.o2o.wd.onlinemarket.command.goods.CartCommand;
import com.cmbchina.o2o.wd.onlinemarket.command.goods.ShoppingCartFilterCommand;
import com.cmbchina.o2o.wd.onlinemarket.dto.PageResult;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    Result addCart(CartCommand command, HttpServletRequest request);

    Result updateCart(CartCommand command, HttpServletRequest request);

    Result removeCart(Long attrId, HttpServletRequest request);

    PageResult getCartList(ShoppingCartFilterCommand command, HttpServletRequest request);
}
