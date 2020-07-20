package com.cmbchina.o2o.wd.onlinemarket.util;

import com.cmbchina.o2o.wd.onlinemarket.constant.UserType;
import com.cmbchina.o2o.wd.onlinemarket.data.Role;

public class UserUtil {

    public static Role transferToRole(UserType type) {
        Role role = new Role();
        role.setId((long) type.getValue());
        role.setName(type.name());
        return role;
    }


}
