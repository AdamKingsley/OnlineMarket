package com.cmbchina.o2o.wd.onlinemarket.controller;

import com.cmbchina.o2o.wd.onlinemarket.command.merchant.MerchantCommand;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.service.AccountService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/account")
public class MerchantController {

    @Resource
    private HttpServletRequest request;

    @Resource
    private AccountService accountService;

    @PutMapping("/update")
    public Result updateMerchant(@RequestBody MerchantCommand command) {
        return accountService.updateMerchant(command, request);
    }

    @GetMapping("/info")
    public Result getMerchant() {
        return accountService.getMerchant(request);
    }

    @GetMapping
    public Result getMerchantById(@RequestParam("id")Long id) {
        return accountService.getMerchant(id,request);
    }


}
