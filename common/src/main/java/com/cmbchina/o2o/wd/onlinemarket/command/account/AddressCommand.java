package com.cmbchina.o2o.wd.onlinemarket.command.account;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import lombok.Data;

@Data
public class AddressCommand {
    private Long id;
    private String name;
    private String province;
    private String city;
    private String area;
    private String address;
    private String telephone;
    private String postalCode;

}
