package com.cmbchina.o2o.wd.onlinemarket.entity.base;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 基类，不包含ID，可以由Long类型主键的entity继承
 */
@Data
@Accessors(chain = true)
@MappedSuperclass
// @EnableSnakeToCamel
public class IdEntity {

    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column
    @IsKey
    @IsAutoIncrement
    @Column
    @Id
    private Long id;

}
