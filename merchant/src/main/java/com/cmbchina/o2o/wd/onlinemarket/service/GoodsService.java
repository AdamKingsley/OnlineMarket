package com.cmbchina.o2o.wd.onlinemarket.service;

import com.cmbchina.o2o.wd.onlinemarket.command.goods.GoodsCommand;
import com.cmbchina.o2o.wd.onlinemarket.command.goods.GoodsFilterCommand;
import com.cmbchina.o2o.wd.onlinemarket.dto.PageResult;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;

import javax.servlet.http.HttpServletRequest;

public interface GoodsService {
    Result addGoods(GoodsCommand command, HttpServletRequest request);

    Result updateGoods(GoodsCommand command, HttpServletRequest request);

    Result removeGoods(Long id, HttpServletRequest request);

    Result removeAttr(Long id, HttpServletRequest request);

    Result getCategoryList(long l);

    PageResult getGoodsList(GoodsFilterCommand command);

    Result getGoodsDetail(Long goodId);
}
