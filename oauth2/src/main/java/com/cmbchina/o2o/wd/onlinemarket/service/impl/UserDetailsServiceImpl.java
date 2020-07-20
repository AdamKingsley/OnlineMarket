package com.cmbchina.o2o.wd.onlinemarket.service.impl;

import com.cmbchina.o2o.wd.onlinemarket.data.UserDetail;
import com.cmbchina.o2o.wd.onlinemarket.entity.User;
import com.cmbchina.o2o.wd.onlinemarket.mapper.UserMapper;
import com.cmbchina.o2o.wd.onlinemarket.util.UserUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> list = userMapper.selectByName(username);
        if (list.size() == 1) {
            UserDetail userDetail = new UserDetail();
            User user = list.get(0);
            BeanUtils.copyProperties(user, userDetail);
            String processedPassword = new StringBuffer(userDetail.getPassword()).
                    append("{").append(userDetail.getSalt()).append("}").toString();
            userDetail.setPassword(processedPassword);
            // 添加用户角色用于反馈用户类型
            userDetail.addRole(UserUtil.transferToRole(user.getType()));
            return userDetail;
        }
        return null;
    }
}
