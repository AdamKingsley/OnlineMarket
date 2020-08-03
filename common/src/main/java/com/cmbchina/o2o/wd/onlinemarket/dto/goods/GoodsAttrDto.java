package com.cmbchina.o2o.wd.onlinemarket.dto.goods;

import lombok.Data;

@Data
public class GoodsAttrDto {
    private long id;
    private long goodsId;
    private String title;
    private String image;
    private String code;
    private double price;
    private double discountAmount;
    private double discountRate;
    // private int saledNum;
    // private int storeNum;
    private boolean onSale = true;
    private boolean isSelective = true;
}
