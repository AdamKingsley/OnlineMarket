package com.cmbchina.o2o.wd.onlinemarket.service.impl;

import com.cmbchina.o2o.wd.onlinemarket.command.goods.CartCommand;
import com.cmbchina.o2o.wd.onlinemarket.constant.ResultStatus;
import com.cmbchina.o2o.wd.onlinemarket.dto.PageResult;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.mapper.ShoppingCartMapper;
import com.cmbchina.o2o.wd.onlinemarket.service.CartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class CartServiceImpl implements CartService {

    @Resource
    private ShoppingCartMapper shoppingCartMapper;

    @Override
    public Result addCart(CartCommand command, HttpServletRequest request) {
        // TODO implement
        return Result.success().setStatus(ResultStatus.OPERATION_SUCEES);
    }

    @Override
    public Result updateCart(CartCommand command, HttpServletRequest request) {
        // TODO implement
        return Result.success().setStatus(ResultStatus.OPERATION_SUCEES);
    }

    @Override
    public Result removeCart(Long attrId, HttpServletRequest request) {
        // TODO implement
        return Result.success().setStatus(ResultStatus.OPERATION_SUCEES);
    }

    @Override
    public PageResult getCartList(HttpServletRequest request) {
        // TODO implement
        return PageResult.success();
    }
}
