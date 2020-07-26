package com.cmbchina.o2o.wd.onlinemarket.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application-dev.yaml")
// @ConfigurationProperties(prefix = "upload")
@Data
public class UploadConfig {

    @Value(value = "${upload.images.avatar-path}")
    private String avatarPath;

    @Value(value = "${upload.images.goods-path}")
    private String goodsPath;

    @Value(value = "${upload.images.goods-attr-path}")
    private String goodsAttrPath;

}
