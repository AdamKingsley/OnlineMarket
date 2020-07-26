package com.cmbchina.o2o.wd.onlinemarket.entity;

import com.cmbchina.o2o.wd.onlinemarket.config.processor.EnableSnakeToCamel;
import com.cmbchina.o2o.wd.onlinemarket.entity.base.BaseIdEntity;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
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

    // 套餐ID
    @Column(name = "attr_id")
    private Long attrId;

    // 套餐名称
    @Column(name = "attr_title", length = 50)
    private String attrTitle;

    // 商品名称
    @Column(name = "goods_name", length = 50)
    private String goodsName;

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

}
