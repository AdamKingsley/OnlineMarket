package com.cmbchina.o2o.wd.onlinemarket.service.impl;

import com.cmbchina.o2o.wd.onlinemarket.command.merchant.MerchantCommand;
import com.cmbchina.o2o.wd.onlinemarket.constant.ResultStatus;
import com.cmbchina.o2o.wd.onlinemarket.constant.Strings;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.dto.merchant.MerchantDto;
import com.cmbchina.o2o.wd.onlinemarket.entity.Merchant;
import com.cmbchina.o2o.wd.onlinemarket.entity.User;
import com.cmbchina.o2o.wd.onlinemarket.mapper.MerchantMapper;
import com.cmbchina.o2o.wd.onlinemarket.service.AccountService;
import com.cmbchina.o2o.wd.onlinemarket.util.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private MerchantMapper merchantMapper;

    @Override
    public Result updateMerchant(MerchantCommand command, HttpServletRequest request) {
        String userId = request.getHeader(Strings.USER_ID_KEY);
        if (null == userId){
            return Result.fail().setStatus(ResultStatus.AUTHRIZATION_ERROR);
        }
        // User user = userMapper.selectByPrimaryKey(userId);
        // if (null == user){
        //     return Result.fail().setStatus(ResultStatus.AUTHRIZATION_ERROR);
        // }
        Example example = new Example(Merchant.class);
        example.createCriteria().andEqualTo(Strings.USER_ID,userId);
        Merchant merchant = merchantMapper.selectOneByExample(example);
        if (null == merchant){
            merchant = new Merchant();
            BeanUtils.copyProperties(command,merchant);
            merchantMapper.insert(merchant);
        }else {
            BeanUtils.copyProperties(command,merchant, CopyUtil.getNullPropertyNames(command));
            CopyUtil.updateObject(merchant);
            merchantMapper.updateByPrimaryKey(merchant);
        }
        return Result.success().setStatus(ResultStatus.OPERATION_SUCEES).setMessage("更新商户信息成功！");
    }

    @Override
    public Result getMerchant(HttpServletRequest request) {
        String userId = request.getHeader(Strings.USER_ID_KEY);
        if (null == userId){
            return Result.fail().setStatus(ResultStatus.AUTHRIZATION_ERROR);
        }
        Example example = new Example(Merchant.class);
        example.createCriteria().andEqualTo(Strings.USER_ID,userId);
        Merchant merchant = merchantMapper.selectOneByExample(example);
        MerchantDto dto = new MerchantDto();
        if (null!=merchant){
            BeanUtils.copyProperties(merchant,dto);
        }
        return Result.success().setStatus(ResultStatus.OPERATION_SUCEES).setData(merchant);
    }

    @Override
    public Result getMerchant(Long id, HttpServletRequest request) {
        Merchant merchant = merchantMapper.selectByPrimaryKey(id);
        MerchantDto dto = new MerchantDto();
        if (null!=merchant){
            BeanUtils.copyProperties(merchant,dto);
        }
        return Result.success().setStatus(ResultStatus.OPERATION_SUCEES).setData(merchant);
    }
}
