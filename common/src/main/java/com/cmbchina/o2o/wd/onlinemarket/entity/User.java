package com.cmbchina.o2o.wd.onlinemarket.entity;

import com.cmbchina.o2o.wd.onlinemarket.constant.UserType;
import com.cmbchina.o2o.wd.onlinemarket.entity.base.BaseEntity;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

/**
 * 用户
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_user")
@javax.persistence.Table(name = "t_user")
// @EnableSnakeToCamel
public class User extends BaseEntity {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @IsKey
    @Column
    private String id;

    @Column(type = MySqlTypeConstant.INT, length = 3)
    @javax.persistence.Column(name = "type")
    private UserType type;

    @Column(length = 45)
    private String username; // 用户名

    @Column(length = 100)
    private String password; // 密码

    @Column(length = 45)
    private String email; // 邮箱

    @Column(length = 20)
    private String telephone; // 手机

    @Column(length = 20)
    private String salt;

    @Column
    private int status; // 状态

    @Column
    private String avatar; // 头像

}
