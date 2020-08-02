package com.cmbchina.o2o.wd.onlinemarket.dto.order;


import com.cmbchina.o2o.wd.onlinemarket.constant.OrderStatus;
import com.cmbchina.o2o.wd.onlinemarket.dto.account.AddressDto;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

@Data
public class OrderDetailDto extends OrderDto {

    private Long attrId;

    private String attrTitle;

    private Long goodsId;

    private String title;

    private String img;

    private String goodsCode;

    private Integer count;
}
