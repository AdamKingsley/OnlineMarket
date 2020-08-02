package com.cmbchina.o2o.wd.onlinemarket.service;

import com.cmbchina.o2o.wd.onlinemarket.command.goods.GoodsFilterCommand;
import com.cmbchina.o2o.wd.onlinemarket.dto.PageResult;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;

public interface GoodsService {
    Result getCategoryList(long rootId);

    PageResult getGoodsList(GoodsFilterCommand command);

    Result getGoodsDetail(Long goodId);

    Result getCategory(Long id);
}
