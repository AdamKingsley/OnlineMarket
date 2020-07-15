package com.cmbchina.o2o.wd.onlinemarket.command;

import com.alibaba.fastjson.annotation.JSONField;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.util.Map;

@Data
@MappedSuperclass
public class PageCommand extends Result {
    // 查询的页码
    @JSONField(name = "page_num")
    private int pageNum;
    // 对应页码的大小
    @JSONField(name = "page_size")
    private int pageSize;

    @JSONField(name = "order_by")
    private String orderBy;

    private Map<String, Object> params;

}
