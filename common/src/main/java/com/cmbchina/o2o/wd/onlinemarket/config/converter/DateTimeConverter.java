package com.cmbchina.o2o.wd.onlinemarket.config.converter;

import com.cmbchina.o2o.wd.onlinemarket.constant.Strings;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DateTimeConverter implements Converter<String, DateTime> {

    @Override
    public DateTime convert(String s) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(Strings.DATE_TIME_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(s);
        return dateTime;
    }
}
