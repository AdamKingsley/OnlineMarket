package com.cmbchina.o2o.wd.onlinemarket.dto.account;

import com.cmbchina.o2o.wd.onlinemarket.constant.UserType;
import lombok.Data;

import javax.management.relation.Role;
import java.util.List;

@Data
public class UserDto {
    private String id;
    private String username;
    // private String password;
    // private String salt;
    private String telephone;
    private String email;
    private UserType type;
    // private List<Role> authorities;
    private int status;
}
