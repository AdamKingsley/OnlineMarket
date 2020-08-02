package com.cmbchina.o2o.wd.onlinemarket.command.order;

import com.cmbchina.o2o.wd.onlinemarket.constant.OrderStatus;
import lombok.Data;

import java.util.List;

@Data
public class OrderUpdateCommand {
    private List<OrderAttrUpdateCommand> details;
    // private Long id;
    private String code;
    // private String address;
    // private Long addressId;
    // private Double price;
    // private Double discountAmount;
    // private String userId;
    private OrderStatus orderStatus;

}
