package com.cmbchina.o2o.wd.onlinemarket.command.order;

import com.cmbchina.o2o.wd.onlinemarket.constant.OrderStatus;
import lombok.Data;
import org.joda.time.DateTime;

import java.util.List;

@Data
public class OrderCommand {
    private List<OrderGoodsCommand> orderGoodsList;
    private String address;
    private Long addressId;
    private Double price;
    private Double discountAmount;
    private String userId;
    private OrderStatus orderStatus;
}
