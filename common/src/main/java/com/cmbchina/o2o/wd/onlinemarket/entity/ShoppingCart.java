package com.cmbchina.o2o.wd.onlinemarket.entity;

import com.cmbchina.o2o.wd.onlinemarket.config.processor.EnableSnakeToCamel;
import com.cmbchina.o2o.wd.onlinemarket.entity.base.BaseIdEntity;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// 购物车
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_shopping_cart")
@javax.persistence.Table(name = "t_shopping_cart")
// @EnableSnakeToCamel
public class ShoppingCart extends BaseIdEntity {
    // 商品图片
    @Column
    private String img;

    // 商品ID
    @Column(name = "goods_id")
    private Long goodsId;

    @Column(name = "user_id", length = 45)
    private String userId;

    // 套餐ID
    @Column(name = "attr_id")
    private Long attrId;
    // 商户ID
    @Column(name = "merchant_id")
    private Long merchantId;


    // 商品价格
    // @Column
    // private Double price;

    // 商品优惠价格
    // @Column(name = "discount_amount")
    // private Double discountAmount;

    // 商品个数
    @Column
    private Integer count;

    // 商品描述
    @Column
    private String title;

    // 套餐描述
    @Column(name = "attr_title", length = 50)
    private String attrTitle;

    // 商品code
    @Column(name = "attr_code", length = 50)
    private String attrCode;

}
