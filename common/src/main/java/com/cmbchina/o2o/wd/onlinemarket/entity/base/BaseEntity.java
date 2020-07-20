package com.cmbchina.o2o.wd.onlinemarket.entity.base;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

import javax.persistence.MappedSuperclass;

;

/**
 * 基类，不包含ID，可以由非Long类型主键的entity继承
 */
@Data
@Accessors(chain = true)
@MappedSuperclass
// @EnableSnakeToCamel
public class BaseEntity {

    @Column(name = "create_time", type = MySqlTypeConstant.DATETIME, isNull = false)
    @javax.persistence.Column(name = "create_time")
    private DateTime createTime;

    @Column(name = "modify_time", type = MySqlTypeConstant.DATETIME, isNull = false)
    @javax.persistence.Column(name = "modify_time")
    private DateTime modifyTime;

    @Column(name = "is_deleted", isNull = false)
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
