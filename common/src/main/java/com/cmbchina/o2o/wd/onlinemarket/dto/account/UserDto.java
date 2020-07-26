package com.cmbchina.o2o.wd.onlinemarket.dto.account;

import com.cmbchina.o2o.wd.onlinemarket.constant.UserType;
import com.cmbchina.o2o.wd.onlinemarket.dto.BaseDto;
import lombok.Data;

@Data
public class UserDto extends BaseDto {
    private String id;
    private String username;
    // private String password;
    // private String salt;
    private String telephone;
    private String email;
    private UserType type;
    // private List<Role> authorities;
    private int status;
    private String avatar;
    private String description;
}
