package com.cmbchina.o2o.wd.onlinemarket.entity;

import com.cmbchina.o2o.wd.onlinemarket.entity.base.BaseIdEntity;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import lombok.Data;
import lombok.ToString;

@Table(name = "t_upload_file")
@javax.persistence.Table(name = "t_upload_file")
@Data
@ToString
public class UploadFile extends BaseIdEntity {

    @Column
    private String path;
    /**
     * 文件大小
     */
    @Column
    private Long size;
    /**
     * 文件后缀
     */
    @Column
    private String suffix;
    /**
     * 上传时文件名称
     */
    @Column
    private String name;
    /**
     * 文件md5值
     */
    @Column
    private String md5;
}