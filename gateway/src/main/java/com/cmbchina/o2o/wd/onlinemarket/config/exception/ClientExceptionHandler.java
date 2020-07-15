package com.cmbchina.o2o.wd.onlinemarket.config.exception;


import com.cmbchina.o2o.wd.onlinemarket.constant.ResultStatus;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.util.JsonUtil;
import com.netflix.client.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class ClientExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(ClientException.class)
    public Result clientException(ClientException ce, HttpServletRequest httpServletRequest) {
        log.info("---------------------------- ClientException Start ---------------------------------");
        Result result = Result.fail().setStatus(ResultStatus.MICROSERVICE_NOT_AVAILABLE).setDescription(ce.getErrorMessage());
        // log.info("Exception Response : {}", JsonUtil.prettyJson(result));
        log.info("Exception Response : {}", JsonUtil.toString(result));
        log.info("---------------------------- ClientException End ---------------------------------");
        return result;
    }
}
