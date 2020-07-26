package com.cmbchina.o2o.wd.onlinemarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class BaseDto {
    private DateTime createTime;
    private DateTime modifyTime;
}
