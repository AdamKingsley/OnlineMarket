package com.cmbchina.o2o.wd.onlinemarket.command.upload;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UploadCommand {
    // 上传单张图片
    @JSONField(serialize = false)
    private MultipartFile file;
    private String md5;

    //上传F多张图片
    @JSONField(serialize = false)
    private List<MultipartFile> files;
    private List<String> md5s;
}
