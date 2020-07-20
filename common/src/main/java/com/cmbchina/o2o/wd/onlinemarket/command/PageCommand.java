package com.cmbchina.o2o.wd.onlinemarket.command;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.joda.time.DateTime;

import javax.persistence.MappedSuperclass;
import java.util.Map;

@Data
@MappedSuperclass
public class PageCommand {

    // 查询的页码
    @JSONField(name = "page_num")
    private int pageNum;

    // 对应页码的大小
    @JSONField(name = "page_size")
    private int pageSize;

    @JSONField(name = "order_by")
    private String orderBy;

    @JSONField(name = "start_time")
    private DateTime startTime;

    @JSONField(name = "end_time")
    private DateTime endTime;

    private Map<String, Object> params;

}
