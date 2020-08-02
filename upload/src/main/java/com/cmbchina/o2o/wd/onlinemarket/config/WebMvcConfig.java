package com.cmbchina.o2o.wd.onlinemarket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

// WebMvcConfigurerAdapter在Spring 5.0之后被弃用了
// 采用实现WebMvcConfigurer形式实现web mvc 配置
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private UploadConfig uploadConfig;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**").addResourceLocations("file:"+uploadConfig.getBasePath());
    }
}
