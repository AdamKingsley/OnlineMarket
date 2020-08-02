package com.cmbchina.o2o.wd.onlinemarket.constant;

import com.google.common.collect.Maps;

import java.util.Map;

public enum OrderStatus implements BaseEnum {
    SUBMIT("提交订单，未付款", 0), PAID("已付款", 1),
    ACCEPT("商家确认订单", 2), REFUSE("商家拒绝订单", 3),
    DELIVERY("订单运送中", 4), CHECK("订单签收", 5), REJECT("拒收件", 6),
    REQUEST_PAY_BACK("申请退款",7),PAID_BACK("退款成功！",8);

    private static Map<Integer, OrderStatus> map = Maps.newHashMap();

    int value;
    String name;

    static {
        for (OrderStatus status : OrderStatus.values()) {
            map.put(status.getValue(), status);
        }
    }

    OrderStatus(String name, int value) {
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

    public static OrderStatus getByValue(int value) {
        return map.get(value);
    }
}
