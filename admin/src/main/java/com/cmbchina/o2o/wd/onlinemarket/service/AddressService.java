package com.cmbchina.o2o.wd.onlinemarket.service;

import com.cmbchina.o2o.wd.onlinemarket.command.account.AddressCommand;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;

import javax.servlet.http.HttpServletRequest;

public interface AddressService {
    Result getAddressList(HttpServletRequest request);

    Result addAddress(AddressCommand command, HttpServletRequest request);

    Result updateAddress(AddressCommand command, HttpServletRequest request);

    Result removeAddress(Long id, HttpServletRequest request);
}
