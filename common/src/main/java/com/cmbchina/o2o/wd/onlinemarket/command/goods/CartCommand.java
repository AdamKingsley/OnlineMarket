package com.cmbchina.o2o.wd.onlinemarket.command.goods;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class CartCommand {
    // 可能不是必须的
    private Long id;

    private String userId;

    @JSONField(name = "goods_id")
    private Long goodsId;

    @JSONField(name = "attr_id")
    private Long attrId;

    @JSONField(name = "attr_title")
    private String attrTitle;

    @JSONField(name = "attr_code")
    private String attrCode;

    private String img;

    private String title;

    @JSONField(name = "merchant_id")
    private Long merchantId;

    private Integer count;
}
