package com.cmbchina.o2o.wd.onlinemarket.entity.base;

import lombok.Data;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 基类，不包含ID，可以由非Long类型主键的entity继承
 */
@Data
@Accessors(chain = true)
@MappedSuperclass
public class BaseEntity {

    @Column(name = "create_time")
    private DateTime createTime;

    @Column(name = "modify_time")
    private DateTime modifyTime;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public BaseEntity() {
        this.createTime = new DateTime();
        this.modifyTime = new DateTime();
        this.isDeleted = false;
    }

    public void update() {
        this.modifyTime = new DateTime();
    }
}
