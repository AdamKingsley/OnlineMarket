package com.cmbchina.o2o.wd.onlinemarket.controller;

import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.feign.AccountFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private AccountFeign accountFeign;


    @GetMapping("test")
    public Result account(@RequestParam("access_token") String token) {
        return accountFeign.user(token);
    }

}
