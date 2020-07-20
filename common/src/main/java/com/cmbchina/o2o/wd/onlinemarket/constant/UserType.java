package com.cmbchina.o2o.wd.onlinemarket.constant;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

public enum UserType implements BaseEnum {
    ADMIN("管理员", 0), CUSTOMER("客户", 1), MERCHANT("商户", 2);

    String name;
    int value;

    private static Map<Integer, UserType> map = Maps.newHashMap();

    static {
        for (UserType type : UserType.values()) {
            map.put(type.getValue(), type);
        }
    }

    UserType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int getValue() {
        return this.value;
    }


    public static UserType getByValue(int value) {
        return map.get(value);
    }
}
