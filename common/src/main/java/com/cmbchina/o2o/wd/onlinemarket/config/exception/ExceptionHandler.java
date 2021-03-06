package com.cmbchina.o2o.wd.onlinemarket.config.exception;


import com.cmbchina.o2o.wd.onlinemarket.constant.ResultStatus;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
@Slf4j
public class ExceptionHandler {

    /**
     * 表单提交异常统一处理
     *
     * @param cve
     * @param httpServletRequest
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public Result methodArgNotValidException(ConstraintViolationException cve, HttpServletRequest httpServletRequest) {
        log.info("---------------------------- ConstraintViolationException Start ---------------------------------");
        Set<ConstraintViolation<?>> cves = cve.getConstraintViolations();
        StringBuffer errorMsg = new StringBuffer();
        cves.forEach(ex -> errorMsg.append(ex.getMessage()).append('\n'));
        Result result = Result.fail().setStatus(ResultStatus.INFORMITION_INVALID).setDescription(errorMsg.toString());
        // log.info("Exception Response : {}", JsonUtil.prettyJson(result));
        log.info("Exception Response : {}", JsonUtil.toString(result));
        log.info("---------------------------- ConstraintViolationException End ---------------------------------");
        return result;
    }

    /**
     * 方法参数提交异常统一处理
     *
     * @param ex
     * @param request
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({MethodArgumentNotValidException.class})
    public Result methodDtoNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.info("---------------------------- MethodArgumentNotValidException Start ---------------------------------");
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        StringBuffer errorMsg = new StringBuffer();
        errors.stream().forEach(x -> {
            errorMsg.append(x.getDefaultMessage()).append("\n");
        });
        Result result = Result.fail().setStatus(ResultStatus.INFORMITION_INVALID).setDescription(errorMsg.toString());
        // log.info("Exception Response : {}", JsonUtil.prettyJson(result));
        log.info("Exception Response : {}", JsonUtil.toString(result));
        log.info("---------------------------- MethodArgumentNotValidException Start ---------------------------------");
        return result;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = MultipartException.class)
    public Result handleBusinessException(MaxUploadSizeExceededException ex) {
        String msg;
        if (ex.getCause().getCause() instanceof FileUploadBase.FileSizeLimitExceededException) {
            log.error(ex.getMessage());
            msg = "上传文件过大[单文件大小不得超过1M]";
        } else if (ex.getCause().getCause() instanceof FileUploadBase.SizeLimitExceededException) {
            log.error(ex.getMessage());
            msg = "上传文件过大[总上传文件大小不得超过10M]";
        } else {
            msg = "上传文件失败";
        }
        return Result.fail().setMessage(msg);
    }
}
