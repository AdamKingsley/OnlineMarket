package com.cmbchina.o2o.wd.onlinemarket.command.order;

import com.cmbchina.o2o.wd.onlinemarket.constant.OrderStatus;
import lombok.Data;

@Data
public class OrderGoodsCommand {
    // code是order的外键
    private String code;
    private Long merchantId;
    private Long goodsId;
    private Long attrId;
    private String title;
    private String attrTitle;
    private Double price;
    private Double discountAmount;
    private Integer count;
    private String img;
    // 更新状态时候必须传，其他情况默认不传
    private OrderStatus status;
}
