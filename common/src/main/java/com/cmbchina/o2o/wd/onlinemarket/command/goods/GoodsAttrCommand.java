package com.cmbchina.o2o.wd.onlinemarket.command.goods;


import lombok.Data;

@Data
public class GoodsAttrCommand {
    private Long id;
    private String title;
    private String image;
    private String code;
    private Double price;
    private Double discountAmount;
    private Double discountRate;
    private Integer storeNum;
    private Long merchantId;
    private String description;
    private boolean isCodeChanged;
    private boolean isPriceChanged;
    private boolean isStoreChanged;
}
