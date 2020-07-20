package com.cmbchina.o2o.wd.onlinemarket.entity;


import com.cmbchina.o2o.wd.onlinemarket.config.processor.EnableSnakeToCamel;
import com.cmbchina.o2o.wd.onlinemarket.entity.base.BaseIdEntity;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商家
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_merchant")
@javax.persistence.Table(name = "t_merchant")
// @EnableSnakeToCamel
public class Merchant extends BaseIdEntity {

    // 商家名称
    @Column(length = 50)
    private String name;

    // 店铺地址
    @Column(length = 100)
    private String address;

    //店铺地址ID
    @Column(name = "address_id")
    private long addressId;

    // 店铺描述
    @Column(length = 500)
    private String description;

    // 店铺照片
    @Column(length = 300)
    private String imgs;

    @Column(name = "user_id", length = 45)
    private String userId;
}
