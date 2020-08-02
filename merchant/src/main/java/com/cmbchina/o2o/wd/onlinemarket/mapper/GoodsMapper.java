package com.cmbchina.o2o.wd.onlinemarket.mapper;

import com.cmbchina.o2o.wd.onlinemarket.command.goods.GoodsFilterCommand;
import com.cmbchina.o2o.wd.onlinemarket.dto.goods.GoodsDto;
import com.cmbchina.o2o.wd.onlinemarket.dto.goods.MerchantGoodsDto;
import com.cmbchina.o2o.wd.onlinemarket.entity.Goods;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface GoodsMapper extends Mapper<Goods> {
    List<MerchantGoodsDto> selectGoodsList(GoodsFilterCommand command);
}
