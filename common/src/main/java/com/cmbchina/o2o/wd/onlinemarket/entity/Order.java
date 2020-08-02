package com.cmbchina.o2o.wd.onlinemarket.entity;

import com.cmbchina.o2o.wd.onlinemarket.config.processor.EnableSnakeToCamel;
import com.cmbchina.o2o.wd.onlinemarket.constant.OrderStatus;
import com.cmbchina.o2o.wd.onlinemarket.entity.base.BaseIdEntity;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.annotation.Unique;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_order")
@javax.persistence.Table(name = "t_order")
// @EnableSnakeToCamel
public class Order extends BaseIdEntity {

    @Unique
    @Column(length = 50)
    private String code;
    // 姓名
    // private String name;
    // 地址
    // private String address;
    // 地址ID
    @Column(name = "address_id")
    private Long addressId;
    // 价钱
    @Column
    private Double price;

    // 优惠额度
    @Column(name = "discount_amount")
    private Double discountAmount;

    // 用户id
    @Column(name = "user_id", length = 45)
    private String userId;

    // 订单状态
    @Column(name = "order_status", type = MySqlTypeConstant.INT, length = 5)
    @javax.persistence.Column(name = "order_status")
    private OrderStatus orderStatus=OrderStatus.SUBMIT;


    @Column(name = "merchant_id")
    private Long merchantId;

    // 订单详情 一对多，自行获取list
    // @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    // @Fetch(value = FetchMode.SUBSELECT)
    // private Set<OrderDetail> orderDetail = new HashSet<>();
}
