package com.cmbchina.o2o.wd.onlinemarket.dto.goods;

import lombok.Data;

@Data
public class MerchantGoodAttrDto {
    private Long id;
    private Long goodsId;
    private String title;
    private String image;
    private String code;
    private Double price;
    private Double discountAmount;
    private Double discountRate;
    private Integer saledNum;
    private Integer storeNum;
    private Boolean onSale;
}
