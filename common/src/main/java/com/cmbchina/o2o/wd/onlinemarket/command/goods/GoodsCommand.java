package com.cmbchina.o2o.wd.onlinemarket.command.goods;

import lombok.Data;

import java.util.List;

@Data
public class GoodsCommand {
    private String name;
    private Double price;
    private Double discountAmount;
    private Double discountRate;
    private Long categoryId;
    private Integer storeNum;
    private List<String> imgs;
    private Long merchantId;
    private List<GoodsAttrCommand> attrs;
    private String description;
    private String label;
    private Long id;
}
