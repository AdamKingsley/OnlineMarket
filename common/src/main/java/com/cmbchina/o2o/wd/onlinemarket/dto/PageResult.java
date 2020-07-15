package com.cmbchina.o2o.wd.onlinemarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
public class PageResult extends Result {
    // 当前返回的页号
    private int currentPage;
    //总共页码数量
    private int totalPage;
    //数据总数
    private long totalNum;
    //查询的页面size大小
    private int size;


    public static PageResult success() {
        PageResult pageResult = new PageResult();
        pageResult.setSuccess(true).setCode(200);
        return pageResult;
    }

    public static PageResult fail() {
        PageResult pageResult = new PageResult();
        pageResult.setSuccess(false);
        return pageResult;
    }
}
