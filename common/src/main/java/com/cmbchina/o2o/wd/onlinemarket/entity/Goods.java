package com.cmbchina.o2o.wd.onlinemarket.entity;


import com.cmbchina.o2o.wd.onlinemarket.entity.base.BaseIdEntity;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_goods")
@javax.persistence.Table(name = "t_goods")
// @EnableSnakeToCamel
public class Goods extends BaseIdEntity {

    // 商品名称
    @Column(length = 50, isNull = false)
    private String name;

    // 商品图片多张使用 `,` 分割，限制最多五张
    @Column(length = 500)
    private String imgs;

    // 商品描述（文字）
    @Column(length = 500)
    private String description;

    // 基础价格
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
    private Integer saledNum = 0 ;

    // 库存总数
    @Column(name = "store_num")
    private Integer storeNum;

    // 商品所在分类 ID
    @Column(name = "category_id")
    private Long categoryId;

    // 商品所属商家1
    @Column(name = "merchant_id")
    private Long merchantId;

    // 商品所在分组（提供给商家自己对商品归类）多对多的关系
    // 设置为商品标签，可以通过逗号隔开
    @Column(length = 100)
    private String label;

    // // 商品套餐（价格在这里面定义）
    // // 一对多 （一个商品的多个颜色的不同价格）
    // private Set<GoodsAttr> goodsAttrs = new HashSet<>();

    // 商品详细配置信息
    // 其实是键值对（可以用mongo存储）
    // @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    // @Fetch(value = FetchMode.SUBSELECT)
    // private Set<GoodsDetail> goodsDetails = new HashSet<>();

}
