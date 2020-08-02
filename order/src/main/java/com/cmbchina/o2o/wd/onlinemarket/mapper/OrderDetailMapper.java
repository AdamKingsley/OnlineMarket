package com.cmbchina.o2o.wd.onlinemarket.mapper;

import com.cmbchina.o2o.wd.onlinemarket.command.order.OrderFilterCommand;
import com.cmbchina.o2o.wd.onlinemarket.dto.order.OrderDetailDto;
import com.cmbchina.o2o.wd.onlinemarket.entity.OrderDetail;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrderDetailMapper extends Mapper<OrderDetail> {

    List<String> selectCodeList(OrderFilterCommand command);

    List<OrderDetailDto> selectOrderDetailList(String code);
}
