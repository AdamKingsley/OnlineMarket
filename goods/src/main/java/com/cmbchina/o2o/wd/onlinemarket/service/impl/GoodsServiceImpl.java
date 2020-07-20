package com.cmbchina.o2o.wd.onlinemarket.service.impl;

import com.cmbchina.o2o.wd.onlinemarket.command.goods.GoodsFilterCommand;
import com.cmbchina.o2o.wd.onlinemarket.constant.ResultStatus;
import com.cmbchina.o2o.wd.onlinemarket.dto.PageResult;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.mapper.GoodsAttrMapper;
import com.cmbchina.o2o.wd.onlinemarket.mapper.GoodsCategoryMapper;
import com.cmbchina.o2o.wd.onlinemarket.mapper.GoodsMapper;
import com.cmbchina.o2o.wd.onlinemarket.service.GoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private GoodsAttrMapper goodsAttrMapper;

    @Resource
    private GoodsCategoryMapper goodsCategoryMapper;

    @Override
    public Result getCategoryList(long rootId) {
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
