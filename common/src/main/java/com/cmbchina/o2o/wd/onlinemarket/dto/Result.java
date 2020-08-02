package com.cmbchina.o2o.wd.onlinemarket.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.cmbchina.o2o.wd.onlinemarket.constant.ResultStatus;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ToString
public class Result {
    private boolean success;
    @JSONField(name = "error_message")
    private String errorMessage = "";
    private String message = "";
    private ResultStatus status;
    private Object data;
    private int code;
    private String description;


    public static Result success() {
        return new Result().setSuccess(true).setCode(200);
    }

    public static Result fail() {
        return new Result().setSuccess(false);
    }

    public Result setStatus(ResultStatus status) {
        this.status = status;
        this.code = status.getCode();
        // if (status.getCode() == 200) {
        //     this.message = status.getMessage();
        // } else {
        //     this.errorMessage = status.getMessage();
        // }
        this.message = status.getMessage();
        this.errorMessage = status.getMessage();
        this.description = status.getMessage();
        return this;
    }
}
