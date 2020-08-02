package com.cmbchina.o2o.wd.onlinemarket.command.order;


import com.cmbchina.o2o.wd.onlinemarket.command.account.AddressCommand;
import com.cmbchina.o2o.wd.onlinemarket.constant.OrderStatus;
import lombok.Data;

@Data
public class OrderAttrUpdateCommand {
    private String code;
    private Long detailId;
    private Long attrId;
    private Long goodsId;
    // 更新状态信息
    private OrderStatus orderStatus;
    // 更新地址信息
    private AddressCommand address;

    private boolean isAddressChanged;

}
