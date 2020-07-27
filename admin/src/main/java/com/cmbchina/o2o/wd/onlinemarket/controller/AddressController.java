package com.cmbchina.o2o.wd.onlinemarket.controller;


import com.cmbchina.o2o.wd.onlinemarket.command.account.AddressCommand;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.service.AddressService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("address")
public class AddressController {

    @Resource
    private AddressService addressService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

    @GetMapping("list")
    public Result getAddressList(){
        return addressService.getAddressList(request);
    }

    @PostMapping("add")
    public Result addAddress(@RequestBody AddressCommand command){
        return addressService.addAddress(command,request);
    }

    @PutMapping("update")
    public Result updateAddress(@RequestBody AddressCommand command){
        return addressService.updateAddress(command,request);
    }

    @DeleteMapping("/{id}")
    public Result removeAddress(@PathVariable Long id){
        return addressService.removeAddress(id,request);
    }

}
