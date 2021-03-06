package com.cmbchina.o2o.wd.onlinemarket.dto.goods;

import lombok.Data;
import org.joda.time.DateTime;

@Data
public class CartDto {
    private Long id;
    // 商品图片
    private String img;
    private Long goodsId;
    // private String userId;
    private Long attrId;
    private Long merchantId;
    private String merchantName;
    private Double goodsPrice;
    private Double goodsDiscountAmount;
    private Double goodsDiscountRate;
    private Double attrPrice;
    private Double attrDiscountAmount;
    private Double attrDiscountRate;
    private Integer count;
    private String title;
    private String attrTitle;
    private String goodsName;
    private String attrName;
    // private String attrCode
    private DateTime createTime;
    private DateTime modifyTime;
}
