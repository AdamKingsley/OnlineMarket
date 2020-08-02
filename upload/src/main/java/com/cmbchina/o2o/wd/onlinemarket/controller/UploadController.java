package com.cmbchina.o2o.wd.onlinemarket.controller;


import com.cmbchina.o2o.wd.onlinemarket.command.upload.UploadCommand;
import com.cmbchina.o2o.wd.onlinemarket.config.UploadConfig;
import com.cmbchina.o2o.wd.onlinemarket.constant.ResultStatus;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.service.UploadService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class UploadController {

    @Resource
    private UploadService uploadService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private UploadConfig uploadConfig;


    @PostMapping("avatar")
    public Result uploadAvatar(@ModelAttribute UploadCommand command) {
        try {
            return uploadService.upload(request, command,uploadConfig.getAvatarPath());
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail().setStatus(ResultStatus.SERVICE_ERROR);
        }
    }

    @PostMapping("banner")
    public Result uploadBanner(@ModelAttribute UploadCommand command) {
        try {
            return uploadService.upload(request, command, uploadConfig.getBannerPath());
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail().setStatus(ResultStatus.SERVICE_ERROR);
        }
    }

    @PostMapping("goods")
    public Result uploadGoods(@ModelAttribute UploadCommand command) {
        try {
            return uploadService.upload(request, command, uploadConfig.getGoodsPath());
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail().setStatus(ResultStatus.SERVICE_ERROR);
        }
    }

    @PostMapping("goodsAttr")
    public Result uploadGoodsAttr(@ModelAttribute UploadCommand command) {
        try {
            return uploadService.upload(request, command, uploadConfig.getGoodsAttrPath());
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail().setStatus(ResultStatus.SERVICE_ERROR);
        }
    }

    @GetMapping("isUpload/{md5}")
    public Result isUpload(@PathVariable String md5){
        return uploadService.isUpload(md5);
    }
}
