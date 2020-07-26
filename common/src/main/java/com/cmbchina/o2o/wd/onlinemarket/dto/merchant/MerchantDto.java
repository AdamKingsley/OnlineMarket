package com.cmbchina.o2o.wd.onlinemarket.dto.merchant;

import com.cmbchina.o2o.wd.onlinemarket.dto.BaseDto;
import lombok.Data;

@Data
public class MerchantDto extends BaseDto {
    // private String userId;
    private Long id;
    // 商家名称
    private String name;
    private String address;
    private Long addressId;
    private String description;
    private String imgs;
    private String telephone;
    private String email;
}
