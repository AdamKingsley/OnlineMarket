package com.cmbchina.o2o.wd.onlinemarket.config.processor;

import org.springframework.web.method.annotation.InitBinderDataBinderFactory;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;

public class SnakeToCamelRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {

    @Override
    protected InitBinderDataBinderFactory createDataBinderFactory(
            List<InvocableHandlerMethod> binderMethods)
            throws Exception {
        return new SnakeToCamelServletRequestDataBinderFactory(binderMethods,
                getWebBindingInitializer());
    }



}