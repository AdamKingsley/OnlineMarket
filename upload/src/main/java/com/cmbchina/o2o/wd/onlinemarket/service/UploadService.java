package com.cmbchina.o2o.wd.onlinemarket.service;

import com.cmbchina.o2o.wd.onlinemarket.command.upload.UploadCommand;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface UploadService {
    Result upload(HttpServletRequest request, UploadCommand command, String path)  throws IOException;

    Result isUpload(String md5);
}
