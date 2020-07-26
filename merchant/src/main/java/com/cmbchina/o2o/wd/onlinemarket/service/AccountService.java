package com.cmbchina.o2o.wd.onlinemarket.service;

import com.cmbchina.o2o.wd.onlinemarket.command.merchant.MerchantCommand;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;

import javax.servlet.http.HttpServletRequest;

public interface AccountService {
    Result updateMerchant(MerchantCommand command, HttpServletRequest request);

    Result getMerchant(HttpServletRequest request);

    Result getMerchant(Long id, HttpServletRequest request);
}
