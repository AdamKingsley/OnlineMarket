package com.cmbchina.o2o.wd.onlinemarket.entity.account;


import com.cmbchina.o2o.wd.onlinemarket.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "t_user")
public class User extends BaseEntity {
    /**
     * UUID生成
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String username;
    private String password;
    private String salt;
    private String telephone;
    private String email;
}
