package com.cmbchina.o2o.wd.onlinemarket.entity;

import com.cmbchina.o2o.wd.onlinemarket.config.processor.EnableSnakeToCamel;
import com.cmbchina.o2o.wd.onlinemarket.entity.base.BaseIdEntity;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_address")
@javax.persistence.Table(name = "t_address")
// @EnableSnakeToCamel
public class Address extends BaseIdEntity {
    // 姓名
    @Column(length = 45)
    private String name;
    // 省
    @Column(length = 20)
    private String province;
    // 市
    @Column(length = 20)
    private String city;
    // 区
    @Column(length = 20)
    private String area;
    // 详细地址
    @Column(length = 20)
    private String address;
    // 手机
    @Column(length = 30)
    private String phone;

    // 邮政编码
    @Column(name = "postal_code", length = 20)
    private String postalCode;


    @Column(name = "user_id", length = 45)
    private String userId;
}
