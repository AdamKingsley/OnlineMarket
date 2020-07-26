package com.cmbchina.o2o.wd.onlinemarket.dto.goods;

import com.cmbchina.o2o.wd.onlinemarket.dto.BaseDto;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import lombok.Data;

@Data
public class CategoryDto extends BaseDto {

    // 关键信息是id 和name，级联筛选的时候需要
    private String name;

    private Integer parentId;

    // private String description;

    private Long id;
}
