package com.cmbchina.o2o.wd.onlinemarket.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/pay")
public class PayoffController {
    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

}
