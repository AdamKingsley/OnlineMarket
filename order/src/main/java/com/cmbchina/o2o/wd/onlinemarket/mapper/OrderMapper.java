package com.cmbchina.o2o.wd.onlinemarket.mapper;

import com.cmbchina.o2o.wd.onlinemarket.command.order.OrderFilterCommand;
import com.cmbchina.o2o.wd.onlinemarket.dto.goods.CartDto;
import com.cmbchina.o2o.wd.onlinemarket.dto.order.OrderDto;
import com.cmbchina.o2o.wd.onlinemarket.entity.Order;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrderMapper extends Mapper<Order> {
    List<OrderDto> selectOrderList(OrderFilterCommand command);
}
