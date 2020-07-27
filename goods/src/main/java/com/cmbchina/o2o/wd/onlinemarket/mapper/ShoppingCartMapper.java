package com.cmbchina.o2o.wd.onlinemarket.mapper;

import com.cmbchina.o2o.wd.onlinemarket.command.goods.ShoppingCartFilterCommand;
import com.cmbchina.o2o.wd.onlinemarket.dto.goods.CartDto;
import com.cmbchina.o2o.wd.onlinemarket.dto.goods.ShoppingCartDto;
import com.cmbchina.o2o.wd.onlinemarket.entity.ShoppingCart;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ShoppingCartMapper extends Mapper<ShoppingCart> {
    List<CartDto> selectCartList(ShoppingCartFilterCommand command);
}
