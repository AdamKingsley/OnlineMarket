package com.cmbchina.o2o.wd.onlinemarket.service.impl;

import com.cmbchina.o2o.wd.onlinemarket.command.upload.UploadCommand;
import com.cmbchina.o2o.wd.onlinemarket.config.UploadConfig;
import com.cmbchina.o2o.wd.onlinemarket.constant.Strings;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.entity.UploadFile;
import com.cmbchina.o2o.wd.onlinemarket.mapper.UploadFileMapper;
import com.cmbchina.o2o.wd.onlinemarket.service.UploadService;
import com.cmbchina.o2o.wd.onlinemarket.util.FileUtil;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class UploadServiceImpl implements UploadService {

    @Resource
    private UploadConfig uploadConfig;
    @Resource
    private UploadFileMapper uploadFileMapper;

    @Override
    public Result upload(HttpServletRequest request, UploadCommand command, String path) throws IOException {
        if (command.getFile() != null) {
            return uploadFile(command.getFile(), command.getMd5(), path);
        }
        if (command.getFiles() != null || command.getFiles().size() > 0) {
            return uploadFiles(command.getFiles(), command.getMd5s(), path);
        }
        return Result.fail().setMessage("后台未发现上传文件信息，上传失败！");
    }

    private Result uploadFiles(List<MultipartFile> files, List<String> md5s, String path) throws IOException {
        // private String path; private Integer size; private String suffix; private String name; private String md5;
        List<String> paths = Lists.newArrayList();
        for (int i = 0; i < files.size(); i++) {
            Result result = uploadFile(files.get(i),md5s.get(i),path);
            if (result.isSuccess()){
                paths.add(result.getData().toString());
            }else {
                return result;
            }
        }
        return Result.success().setMessage("上传成功！").setData(paths);
    }

    private Result uploadFile(MultipartFile file, String md5, String path) throws IOException {
        // private String path; private Integer size; private String suffix; private String name; private String md5;
        UploadFile uploadFile = new UploadFile();
        String filename = file.getOriginalFilename();
        Long size = file.getSize();
        uploadFile.setMd5(md5);
        uploadFile.setName(filename);
        uploadFile.setSize(size);
        String suffix = filename.substring(filename.lastIndexOf('.'));
        uploadFile.setSuffix(suffix);
        path = FileUtil.join(path,md5+suffix);
        uploadFile.setPath(path);
        String savePath = FileUtil.join(uploadConfig.getBasePath(),path);
        file.transferTo(new File(savePath));
        uploadFileMapper.insert(uploadFile);
        return Result.success().setMessage("上传成功！").setData(path);
    }


    @Override
    public Result isUpload(String md5) {
        Example example = new Example(UploadFile.class);
        example.createCriteria().andEqualTo(Strings.MD5, md5);
        UploadFile entity = uploadFileMapper.selectOneByExample(example);
        if (null == entity) {
            return Result.fail().setMessage("之前并未上传过该图片!");
        }
        return Result.success().setData(entity.getPath());
    }
}
