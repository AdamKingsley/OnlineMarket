package com.cmbchina.o2o.wd.onlinemarket.config.converter;

import com.cmbchina.o2o.wd.onlinemarket.constant.OrderStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusIntConverter implements Converter<Integer, OrderStatus> {
    @Override
    public OrderStatus convert(Integer integer) {
        return OrderStatus.getByValue(integer);
    }
}
