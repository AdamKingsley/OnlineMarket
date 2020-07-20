package com.cmbchina.o2o.wd.onlinemarket.config.converter;


import com.cmbchina.o2o.wd.onlinemarket.constant.UserType;
import org.joda.time.DateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserTypeIntConverter implements Converter<Integer, UserType> {

    @Override
    public UserType convert(Integer integer) {
        return UserType.getByValue(integer);
    }
}
