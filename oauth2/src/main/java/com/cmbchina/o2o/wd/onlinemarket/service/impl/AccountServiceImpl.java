package com.cmbchina.o2o.wd.onlinemarket.service.impl;

import com.cmbchina.o2o.wd.onlinemarket.command.account.RegisterCommand;
import com.cmbchina.o2o.wd.onlinemarket.command.account.UserInfoCommand;
import com.cmbchina.o2o.wd.onlinemarket.constant.ResultStatus;
import com.cmbchina.o2o.wd.onlinemarket.constant.Strings;
import com.cmbchina.o2o.wd.onlinemarket.constant.UserType;
import com.cmbchina.o2o.wd.onlinemarket.data.Role;
import com.cmbchina.o2o.wd.onlinemarket.data.UserDetail;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.dto.account.UserDto;
import com.cmbchina.o2o.wd.onlinemarket.entity.User;
import com.cmbchina.o2o.wd.onlinemarket.mapper.UserMapper;
import com.cmbchina.o2o.wd.onlinemarket.service.AccountService;
import com.cmbchina.o2o.wd.onlinemarket.util.CopyUtil;
import com.cmbchina.o2o.wd.onlinemarket.util.Encryption;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.UUID;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private static final int SALT_LENGTH = 6;

    @Resource
    private UserMapper userMapper;


    @Override
    public Result register(RegisterCommand command) {
        // command类上校验参数完整性
        // 验证密码和确认密码一致 password.equals(confirmPassword)
        if (!command.getConfirmPassword().equals(command.getPassword())) {
            return Result.fail().setStatus(ResultStatus.PASSWORD_NOT_CONSISTENCY);
        }
        // TODO 如有必要验证手机号和邮箱的合法性
        User user = new User();
        //用户属性copy
        BeanUtils.copyProperties(command, user);
        // 随机生成六位数的盐度
        user.setSalt(RandomStringUtils.random(SALT_LENGTH, true, true));
        // 随机生成用户uuid，作为主键标识用户id
        user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        // 根据盐度和密码进行md5加密生成密码
        user.setPassword(Encryption.encodeMd5(user.getPassword() + user.getSalt()));
        int state = userMapper.insert(user);
        return Result.success().setStatus(ResultStatus.OPERATION_SUCCESS).setMessage("注册账户成功！");
    }

    @Override
    public Result obtainUser(Principal user) {
        if (user instanceof OAuth2Authentication) {
            UserDetail userDetail = (UserDetail) ((OAuth2Authentication) user).getUserAuthentication().getPrincipal();
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userDetail, userDto);
            // 代码有点牵强
            Role role = (Role) userDetail.getAuthorities().iterator().next();
            userDto.setType(UserType.getByValue(role.getId().intValue()));
            return Result.success().setData(userDto).setStatus(ResultStatus.OPERATION_SUCCESS).setDescription("获取用户信息成功！");
        }
        // 未登录,请登录后重试该操作
        return Result.fail().setStatus(ResultStatus.OPERATION_NOT_PERMITTED);
    }

    @Override
    public Result update(UserInfoCommand command, HttpServletRequest request) {
        // 获取gateway传入的userId
        String userId = request.getHeader(Strings.USER_ID_KEY);
        User user = userMapper.selectByPrimaryKey(userId);
        if (null == user) {
            return Result.fail().setStatus(ResultStatus.USER_NOT_FOUND_ERROR).setDescription("未获取到对应用户信息，无法修改用户信息！");
        }
        // 均为String 忽略 null的字段，其余字段更新
        BeanUtils.copyProperties(command, user, CopyUtil.getNullPropertyNames(command));
        CopyUtil.updateObject(user);
        userMapper.updateByPrimaryKey(user);
        return Result.success().setStatus(ResultStatus.OPERATION_SUCCESS).setDescription("更新用户个人信息成功！");
    }

    @Override
    public Result obtainUserDetail(Principal user) {
        if (user instanceof OAuth2Authentication) {
            UserDetail userDetail = (UserDetail) ((OAuth2Authentication) user).getUserAuthentication().getPrincipal();
            String id = userDetail.getId();
            User entity = userMapper.selectByPrimaryKey(id);
            UserDto dto = new UserDto();
            BeanUtils.copyProperties(entity,dto);
            return Result.success().setData(dto);
        }
        return Result.fail();
    }
}
