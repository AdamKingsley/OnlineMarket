package com.cmbchina.o2o.wd.onlinemarket.entity;

import com.cmbchina.o2o.wd.onlinemarket.constant.OrderStatus;
import com.cmbchina.o2o.wd.onlinemarket.entity.base.BaseIdEntity;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_order_detail")
@javax.persistence.Table(name = "t_order_detail")
// @EnableSnakeToCamel
public class OrderDetail extends BaseIdEntity {

    // @Unique
    @Column(length = 50)
    // 这个字段是order表的外键
    private String code;

    // 支持发单前的修改
    @Column(length = 100)
    private String address;

    @Column
    private Long addressId;

    // 套餐ID
    @Column(name = "attr_id")
    private Long attrId;

    // 套餐名称
    @Column(name = "attr_title", length = 50)
    private String attrTitle;

    @Column(name = "goods_id")
    private Long goodsId;

    // 商品名称goodsName
    @Column(name = "title", length = 50)
    private String title;

    // 商品图片 默认255
    @Column
    private String img;

    @Column
    private Double price;

    @Column(name = "discount_amount")
    private Double discountAmount;

    // 套餐code
    @Column(name = "goods_code", length = 50)
    private String goodsCode;

    // 商家ID
    @Column(name = "merchant_id")
    private Long merchantId;

    // 购买数量
    @Column
    private Integer count;

    // 订单状态
    @Column(name = "order_status", type = MySqlTypeConstant.INT, length = 5)
    @javax.persistence.Column(name = "order_status")
    private OrderStatus orderStatus = OrderStatus.SUBMIT;


}
