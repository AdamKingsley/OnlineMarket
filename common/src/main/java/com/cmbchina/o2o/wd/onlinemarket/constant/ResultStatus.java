package com.cmbchina.o2o.wd.onlinemarket.constant;

public enum ResultStatus {
    OPERATION_SUCEES(200, "操作成功！"),
    SERVICE_ERROR(500, "服务后台内部出现错误，请联系管理员！"),
    INFORMITION_INVALID(501, "请检查信息正确性和完备性！"),
    OPERATION_NOT_PERMITTED(502, "用户无操作权限，请登录后重试！"),
    PASSWORD_NOT_CONSISTENCY(503, "输入的密码和确认密码不一致！"),
    USER_NOT_FOUND_ERROR(504, "登录失败，未查找到对应用户！"),
    PASSWORD_NOT_MATCH_ERROR(503, "用户登录失败，密码错误！"),
    AUTHRIZATION_ERROR(505, "用户登录失败，认证出错！"),

    MICROSERVICE_NOT_AVAILABLE(510, "微服务不可用！");

    String message;
    int code;

    ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
