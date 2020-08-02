package com.cmbchina.o2o.wd.onlinemarket.dto.goods;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
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
    private boolean onSale;
    private boolean isSelective = true;
}
