package com.cmbchina.o2o.wd.onlinemarket.entity.base;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 基类，不包含ID，可以由Long类型主键的entity继承
 */
@Data
@Accessors(chain = true)
@MappedSuperclass
public class IdEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
