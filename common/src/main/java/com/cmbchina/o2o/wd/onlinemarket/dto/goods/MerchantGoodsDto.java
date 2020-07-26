package com.cmbchina.o2o.wd.onlinemarket.dto.goods;


import com.cmbchina.o2o.wd.onlinemarket.dto.BaseDto;
import lombok.Data;

@Data
public class MerchantGoodsDto extends BaseDto {
    // 商品名称
    private String name;
    private String imgs;
    private String description;
    private double price;
    private double discountAmount;
    private double discountRate;
    private int saledNum;
    private int storeNum;
    private long categoryId;
    private long merchantId;
    private String label;
    private long id;
}
