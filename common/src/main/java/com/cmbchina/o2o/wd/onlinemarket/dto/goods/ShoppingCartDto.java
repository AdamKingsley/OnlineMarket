package com.cmbchina.o2o.wd.onlinemarket.dto.goods;

import com.cmbchina.o2o.wd.onlinemarket.config.typeHandler.DateTimeTypeHandler;
import lombok.Data;
import org.joda.time.DateTime;

@Data
public class ShoppingCartDto {
    private Long id;
    // 商品图片

    private String img;
    private Long goodsId;
    // private String userId;
    private Long attrId;
    private Long merchantId;
    private String merchantName;
    private Double price;
    private Double discountAmount;
    private Integer count;
    private String title;
    private String attrTitle;
    // private String attrCode;
    private DateTime createTime;
    private DateTime modifyTime;
}
