package com.cmbchina.o2o.wd.onlinemarket.entity;


import com.cmbchina.o2o.wd.onlinemarket.entity.base.BaseIdEntity;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品套餐
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_goods_attr")
@javax.persistence.Table(name = "t_goods_attr")
// @EnableSnakeToCamel
public class GoodsAttr extends BaseIdEntity {

    @Column(name = "goods_id")
    private Long goodsId;

    // 该配置信息
    @Column
    private String title;

    // 该配置商品小图
    @Column
    private String image;

    // 该配置商品编号（每一个属性都有一个独特的商品编号）
    @Column(name = "code", length = 50)
    private String code;

    // 该配置商品价格
    @Column
    private Double price;


    // 优惠折扣后，减掉的价格
    @Column(name = "discount_amount")
    private Double discountAmount;

    // 优惠折扣
    @Column(name = "discount_rate")
    private Double discountRate;

    // 商品销售总数
    @Column(name = "saled_num")
    private Integer saledNum = 0;

    // 库存总数
    @Column(name = "store_num")
    private Integer storeNum;

    // 该配置商品是否已经上架
    @Column(name = "on_sale")
    private Boolean onSale = true;
}
