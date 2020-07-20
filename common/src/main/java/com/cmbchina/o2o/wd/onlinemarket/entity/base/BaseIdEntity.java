package com.cmbchina.o2o.wd.onlinemarket.entity.base;


import com.cmbchina.o2o.wd.onlinemarket.config.processor.EnableSnakeToCamel;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@Accessors(chain = true)
@MappedSuperclass
// @EnableSnakeToCamel
public class BaseIdEntity extends BaseEntity {

    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column
    @IsKey
    @IsAutoIncrement
    @Column
    @Id
    private Long id;




}
