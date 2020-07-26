package com.cmbchina.o2o.wd.onlinemarket.controller;

import com.cmbchina.o2o.wd.onlinemarket.config.UploadConfig;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @Resource
    private UploadConfig uploadConfig;

    @GetMapping("get")
    public Result get(){
        log.info(uploadConfig.getAvatarPath());
        return Result.success().setData(uploadConfig.getAvatarPath());
    }
}
