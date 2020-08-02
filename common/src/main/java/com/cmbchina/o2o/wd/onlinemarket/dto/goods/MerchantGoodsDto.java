package com.cmbchina.o2o.wd.onlinemarket.dto.goods;


import com.cmbchina.o2o.wd.onlinemarket.dto.BaseDto;
import lombok.Data;

@Data
public class MerchantGoodsDto extends BaseDto {
    // 商品名称
    private String name;
    private String imgs;
    private String description;
    private Double price;
    private Double discountAmount;
    private Double discountRate;
    private Integer saledNum;
    private Integer storeNum;
    private Long categoryId;
    private String categoryName;
    private Long categoryParentId;
    private Long merchantId;
    private String label;
    private Long id;
}
