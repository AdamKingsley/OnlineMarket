package com.cmbchina.o2o.wd.onlinemarket.command.goods;


import com.cmbchina.o2o.wd.onlinemarket.command.PageCommand;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class GoodsFilterCommand extends PageCommand {
    // 个性化定制的查询条件
    // 类别 条件
    // 商品特征、关键词条件
    private String name;
    private String label;
    private Long merchantId;
    private Long categoryId;
    private List<Long> categoryList = Lists.newArrayList();
}
