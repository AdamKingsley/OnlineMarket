package com.cmbchina.o2o.wd.onlinemarket.command.goods;

import lombok.Data;

import java.util.List;

@Data
public class GoodsCommand {

    private List<GoodsAttrCommand> goodsAttrs;
}
