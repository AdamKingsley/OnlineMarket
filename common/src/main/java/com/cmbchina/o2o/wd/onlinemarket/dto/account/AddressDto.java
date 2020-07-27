package com.cmbchina.o2o.wd.onlinemarket.dto.account;

import lombok.Data;

@Data
public class AddressDto {
    private Long id;
    private String name;

    private String province;

    private String city;

    private String area;

    private String address;
    private String telephone;

    private String postalCode;
}
