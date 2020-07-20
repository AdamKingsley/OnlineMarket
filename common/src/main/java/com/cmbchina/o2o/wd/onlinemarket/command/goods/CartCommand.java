package com.cmbchina.o2o.wd.onlinemarket.command.goods;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class CartCommand {

    @JSONField(name = "goods_id")
    private Long goodsId;

    @JSONField(name = "attr_id")
    private Long attrId;

    @JSONField(name = "merchant_id")
    private Long merchantId;

    private Integer num;
}
