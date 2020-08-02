package com.cmbchina.o2o.wd.onlinemarket.command.order;

import com.cmbchina.o2o.wd.onlinemarket.constant.OrderStatus;
import lombok.Data;

import java.util.List;

@Data
public class OrderUpdateAllCommand {
    private List<Long> ids;
    private OrderStatus orderStatus;
}
