package com.cmbchina.o2o.wd.onlinemarket.service.impl;

import com.cmbchina.o2o.wd.onlinemarket.command.account.AddressCommand;
import com.cmbchina.o2o.wd.onlinemarket.constant.ResultStatus;
import com.cmbchina.o2o.wd.onlinemarket.constant.Strings;
import com.cmbchina.o2o.wd.onlinemarket.constant.UserType;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.dto.account.AddressDto;
import com.cmbchina.o2o.wd.onlinemarket.entity.Address;
import com.cmbchina.o2o.wd.onlinemarket.entity.User;
import com.cmbchina.o2o.wd.onlinemarket.mapper.AddressMapper;
import com.cmbchina.o2o.wd.onlinemarket.mapper.UserMapper;
import com.cmbchina.o2o.wd.onlinemarket.service.AddressService;
import com.cmbchina.o2o.wd.onlinemarket.util.CopyUtil;
import com.cmbchina.o2o.wd.onlinemarket.util.StringUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AdressServiceImpl implements AddressService {

    @Resource
    private AddressMapper addressMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public Result getAddressList(HttpServletRequest request) {
        String userId = request.getHeader(Strings.USER_ID_KEY);
        Result state = checkAuthority(request);
        if (!state.isSuccess()) {
            return state;
        }
        Example example = new Example(Address.class);
        example.createCriteria().andEqualTo(Strings.IS_DELETED, false)
                .andEqualTo(Strings.USER_ID, userId);
        example.setOrderByClause(StringUtil.humpToUnderline(Strings.MODIFY_TIME) + " desc");
        List<Address> list = addressMapper.selectByExample(example);
        List<AddressDto> dtos = Lists.newArrayList();
        list.forEach(address -> {
            AddressDto dto = new AddressDto();
            BeanUtils.copyProperties(address, dto);
            dtos.add(dto);
        });
        return Result.success().setData(dtos);
    }

    @Override
    public Result addAddress(AddressCommand command, HttpServletRequest request) {
        String userId = request.getHeader(Strings.USER_ID_KEY);
        Result state = checkAuthority(request);
        if (!state.isSuccess()) {
            return state;
        }
        Address address = new Address();
        BeanUtils.copyProperties(command, address, CopyUtil.getNullPropertyNames(command));
        address.setUserId(userId);
        Example example = new Example(Address.class);
        example.createCriteria().andEqualTo(Strings.IS_DELETED, true)
                .andEqualTo(Strings.USER_ID, userId).andEqualTo(Strings.NAME, address.getName())
                .andEqualTo(Strings.ADDRESS, address.getAddress()).andEqualTo(Strings.AREA, address.getArea()).
                andEqualTo(Strings.CITY, address.getCity()).andEqualTo(Strings.PROVINCE,address.getProvince())
        .andEqualTo(Strings.POSTAL_CODE,address.getPostalCode()).andEqualTo(Strings.TELEPHONE,address.getTelephone());
        List<Address> addressList = addressMapper.selectByExample(example);
        if (addressList.size() > 0) {
            // 有重复的地址项，将删除字段恢复
            address = addressList.get(0);
            address.setIsDeleted(false);
            CopyUtil.updateObject(address);
            addressMapper.updateByPrimaryKey(address);
        } else {
            // 没有直接插入
            addressMapper.insert(address);
        }
        return Result.success();
    }

    @Override
    public Result updateAddress(AddressCommand command, HttpServletRequest request) {
        String userId = request.getHeader(Strings.USER_ID_KEY);
        Result state = checkAuthority(request);
        if (!state.isSuccess()) {
            return state;
        }
        // 为了避免查看订单的时候无法看到原来的地址
        // 这里的更新是删掉后，重新插入
        Address address = addressMapper.selectByPrimaryKey(command.getId());
        address.setIsDeleted(true);
        // BeanUtils.copyProperties(command,address,CopyUtil.getNullPropertyNames(command));
        command.setId(null);
        addressMapper.updateByPrimaryKey(address);
        return this.addAddress(command,request);
    }

    @Override
    public Result removeAddress(Long id, HttpServletRequest request) {
        String userId = request.getHeader(Strings.USER_ID_KEY);
        Result state = checkAuthority(request);
        if (!state.isSuccess()) {
            return state;
        }
        Address address = addressMapper.selectByPrimaryKey(id);
        address.setIsDeleted(true);
        addressMapper.updateByPrimaryKey(address);
        return Result.success();
    }

    private Result checkAuthority(HttpServletRequest request) {
        String userId = request.getHeader(Strings.USER_ID_KEY);
        if (null == userId) {
            return Result.fail().setStatus(ResultStatus.AUTHRIZATION_ERROR).setMessage("未检测到登录信息！");
        }
        User user = userMapper.selectByPrimaryKey(userId);
        if (user.getType() != UserType.CUSTOMER) {
            return Result.fail().setStatus(ResultStatus.AUTHRIZATION_ERROR).setMessage("仅支持消费者进行地址管理操作！");
        }
        return Result.success();
    }
}
