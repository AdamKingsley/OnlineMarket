package com.cmbchina.o2o.wd.onlinemarket.command.merchant;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import lombok.Data;

@Data
public class MerchantCommand {
    // private String userId;
    // 商家名称
    private String name;
    private String address;
    private Long addressId;
    private String description;
    private String imgs;
    private String telephone;
    private String email;
}
