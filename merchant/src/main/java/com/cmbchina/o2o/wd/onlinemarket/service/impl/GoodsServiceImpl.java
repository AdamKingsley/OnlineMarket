package com.cmbchina.o2o.wd.onlinemarket.service.impl;

import com.cmbchina.o2o.wd.onlinemarket.command.goods.GoodsCommand;
import com.cmbchina.o2o.wd.onlinemarket.command.goods.GoodsFilterCommand;
import com.cmbchina.o2o.wd.onlinemarket.constant.ResultStatus;
import com.cmbchina.o2o.wd.onlinemarket.dto.PageResult;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.mapper.GoodsAttrMapper;
import com.cmbchina.o2o.wd.onlinemarket.mapper.GoodsMapper;
import com.cmbchina.o2o.wd.onlinemarket.service.GoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private GoodsAttrMapper goodsAttrMapper;

    @Override
    public Result addGoods(GoodsCommand command, HttpServletRequest request) {
        // TODO implement
        return Result.success().setStatus(ResultStatus.OPERATION_SUCEES);
    }

    @Override
    public Result updateGoods(GoodsCommand command, HttpServletRequest request) {
        // TODO implement
        return Result.success().setStatus(ResultStatus.OPERATION_SUCEES);
    }

    @Override
    public Result removeGoods(Long id, HttpServletRequest request) {
        // TODO implement
        return Result.success().setStatus(ResultStatus.OPERATION_SUCEES);
    }

    @Override
    public Result removeAttr(Long id, HttpServletRequest request) {
        // TODO implement
        return Result.success().setStatus(ResultStatus.OPERATION_SUCEES);
    }

    @Override
    public Result getCategoryList(long l) {
        // TODO implement
        return Result.success().setStatus(ResultStatus.OPERATION_SUCEES);
    }

    @Override
    public PageResult getGoodsList(GoodsFilterCommand command) {
        // TODO implement
        return PageResult.success();
    }

    @Override
    public Result getGoodsDetail(Long goodId) {
        // TODO implement
        return Result.success().setStatus(ResultStatus.OPERATION_SUCEES);
    }
}
