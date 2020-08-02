package com.cmbchina.o2o.wd.onlinemarket.service.impl;

import com.cmbchina.o2o.wd.onlinemarket.command.account.PasswordCommand;
import com.cmbchina.o2o.wd.onlinemarket.command.account.UserCommand;
import com.cmbchina.o2o.wd.onlinemarket.command.account.UserFilterCommand;
import com.cmbchina.o2o.wd.onlinemarket.constant.ResultStatus;
import com.cmbchina.o2o.wd.onlinemarket.constant.Strings;
import com.cmbchina.o2o.wd.onlinemarket.dto.PageResult;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.dto.account.UserDto;
import com.cmbchina.o2o.wd.onlinemarket.entity.User;
import com.cmbchina.o2o.wd.onlinemarket.mapper.UserMapper;
import com.cmbchina.o2o.wd.onlinemarket.service.ManagementService;
import com.cmbchina.o2o.wd.onlinemarket.util.CopyUtil;
import com.cmbchina.o2o.wd.onlinemarket.util.Encryption;
import com.cmbchina.o2o.wd.onlinemarket.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ManagementServiceImpl implements ManagementService {

    @Resource
    private UserMapper userMapper;

    private static final int SALT_LENGTH = 6;

    @Override
    public PageResult getUserList(UserFilterCommand command, HttpServletRequest request) {
        PageHelper.startPage(command.getPageNum(), command.getPageSize());
        Example example = buildExample(User.class, command);
        List<User> list = userMapper.selectByExample(example);
        PageInfo page = new PageInfo(list);
        PageResult result = PageResult.success();
        result.setCurrentPage(command.getPageNum()).setSize(command.getPageSize()).setTotalNum(page.getTotal()).setTotalPage(page.getPages());
        List<UserDto> dtos = Lists.newArrayList();
        page.getList().forEach(user -> {
            UserDto dto = new UserDto();
            BeanUtils.copyProperties(user, dto);
            dtos.add(dto);
        });
        result.setData(dtos);
        return result;
    }

    private Example buildExample(Class<?> clazz, UserFilterCommand command) {
        Example example = new Example(clazz);
        example.setDistinct(true);
        // 按照时间顺序
        example.setOrderByClause(StringUtil.isEmpty(command.getOrderBy()) ? "`" + StringUtil.humpToUnderline(Strings.MODIFY_TIME) + "` desc" :
                command.getOrderBy());
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(Strings.IS_DELETED, false);
        if (!StringUtil.isEmpty(command.getUsername())) {
            criteria.andLike(Strings.USERNAME, StringUtil.wrap(command.getUsername(), "%"));
        }
        if (!StringUtil.isEmpty(command.getEmail())) {
            criteria.andLike(Strings.EMAIL, StringUtil.wrap(command.getEmail(), "%"));
        }
        if (!StringUtil.isEmpty(command.getTelephone())) {
            criteria.andLike(Strings.TELEPHONE, StringUtil.wrap(command.getTelephone(), "%"));
        }
        if (Objects.nonNull(command.getType())) {
            criteria.andEqualTo(Strings.TYPE, command.getType().getValue());
        }
        if (Objects.nonNull(command.getStatus())) {
            criteria.andEqualTo(Strings.STATUS, command.getStatus());
        }
        if (Objects.nonNull(command.getStartTime())) {
            criteria.andGreaterThanOrEqualTo(Strings.CREATE_TIME, command.getStartTime());
        }
        if (Objects.nonNull(command.getEndTime())) {
            criteria.andLessThanOrEqualTo(Strings.CREATE_TIME, command.getEndTime());
        }
        return example;
    }


    @Override
    public Result addUser(UserCommand command, HttpServletRequest request) {
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
        return Result.success().setStatus(ResultStatus.OPERATION_SUCCESS).setMessage("添加账户成功！");
    }

    @Override
    public Result updateUser(UserCommand command, HttpServletRequest request) {
        User user = userMapper.selectByPrimaryKey(command.getId());
        BeanUtils.copyProperties(command, user, CopyUtil.getNullPropertyNames(command));
        userMapper.updateByPrimaryKey(user);
        return Result.success().setStatus(ResultStatus.OPERATION_SUCCESS).setMessage("更新用户信息成功！");
    }

    @Override
    public Result removeUser(String userId, HttpServletRequest request) {
        User user = userMapper.selectByPrimaryKey(userId);
        user.setIsDeleted(true);
        userMapper.updateByPrimaryKey(user);
        return Result.success().setStatus(ResultStatus.OPERATION_SUCCESS).setMessage("删除用户成功！");
    }

    @Override
    public Result changePassword(PasswordCommand command, HttpServletRequest request) {

        if (!command.getConfirmPassword().equals(command.getPassword())){
            return Result.fail().setStatus(ResultStatus.PASSWORD_NOT_CONSISTENCY);
        }
        String userId = request.getHeader(Strings.USER_ID_KEY);

        if (null == userId){
            return Result.fail().setStatus(ResultStatus.AUTHRIZATION_ERROR);
        }
        User user = userMapper.selectByPrimaryKey(userId);
        if (null == user){
            return Result.fail().setStatus(ResultStatus.AUTHRIZATION_ERROR);
        }
        // 开始更新密码
        user.setSalt(RandomStringUtils.random(SALT_LENGTH, true, true));
        // 随机生成用户uuid，作为主键标识用户id
        //user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        // 根据盐度和密码进行md5加密生成密码
        user.setPassword(Encryption.encodeMd5(command.getPassword() + user.getSalt()));
        userMapper.updateByPrimaryKey(user);
        return  Result.success().setStatus(ResultStatus.OPERATION_SUCCESS).setMessage("更新用户密码成功");
    }

    // 下面接口暂时用不到，可被update接口替换
    @Override
    public Result resetUser(UserCommand command, HttpServletRequest request) {
        // implement
        return Result.success().setStatus(ResultStatus.OPERATION_SUCCESS);
    }
}
