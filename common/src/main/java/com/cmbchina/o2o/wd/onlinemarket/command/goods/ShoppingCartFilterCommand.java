package com.cmbchina.o2o.wd.onlinemarket.command.goods;

import com.cmbchina.o2o.wd.onlinemarket.command.PageCommand;
import lombok.Data;

@Data
public class ShoppingCartFilterCommand extends PageCommand {
    private String userId;
    private String name;
    private String label;
    // private Long merchantId;
    // private Long categoryId;
    // private List<Long> categoryList = Lists.newArrayList();
}
