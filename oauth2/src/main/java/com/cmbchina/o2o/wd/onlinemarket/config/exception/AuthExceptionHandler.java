package com.cmbchina.o2o.wd.onlinemarket.config.exception;

import com.cmbchina.o2o.wd.onlinemarket.constant.ResultStatus;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class AuthExceptionHandler {

    // InternalAuthenticationServiceException
    @org.springframework.web.bind.annotation.ExceptionHandler({InternalAuthenticationServiceException.class})
    public Result internalAuthenticationServiceException(InternalAuthenticationServiceException ex, HttpServletRequest request) {
        log.info("---------------------------- InternalAuthenticationServiceException Start ---------------------------------");
        Result result = Result.fail().setStatus(ResultStatus.AUTHRIZATION_ERROR);
        // log.info("Exception Response : {}", JsonUtil.prettyJson(result));
        log.info("Exception Response : {}", JsonUtil.toString(result));
        log.info("---------------------------- InternalAuthenticationServiceException Start ---------------------------------");
        return result;
    }
}
