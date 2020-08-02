package com.cmbchina.o2o.wd.onlinemarket.dto.order;


import com.cmbchina.o2o.wd.onlinemarket.constant.OrderStatus;
import com.cmbchina.o2o.wd.onlinemarket.dto.BaseDto;
import com.cmbchina.o2o.wd.onlinemarket.dto.account.AddressDto;
import lombok.Data;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class OrderDto extends BaseDto {

    private String code;
    private Long addressId;
    private String name;
    private String province;
    private String city;
    private String area;
    private String address;
    private String telephone;
    private String postalCode;
    // 价钱
    private Double price;
    // 优惠额度
    private Double discountAmount;

    // 订单状态
    private OrderStatus orderStatus;
    private Long merchantId;
    private String merchantName;
}
