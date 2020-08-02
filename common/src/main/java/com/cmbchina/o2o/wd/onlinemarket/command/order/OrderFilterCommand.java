package com.cmbchina.o2o.wd.onlinemarket.command.order;


import com.cmbchina.o2o.wd.onlinemarket.command.PageCommand;
import com.cmbchina.o2o.wd.onlinemarket.constant.OrderStatus;
import lombok.Data;

import java.util.List;

@Data
public class OrderFilterCommand extends PageCommand {
    private String userId;
    private String name;
    private String label;
    private Long merchantId;
    private OrderStatus status;
    private List<String> codeList;
    // private Long categoryId;

}
