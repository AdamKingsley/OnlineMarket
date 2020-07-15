package com.cmbchina.o2o.wd.onlinemarket.config.filter.log;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MultiPartFormDataToJson {

    public static Map<String, String> toMap(String params) {
        Map<String, String> map = new HashMap<>();
        //获得分隔符
        String boundary = params.split("\r\n")[0];
        //获得分割后的参数
        String[] ps = Optional.ofNullable(params).orElse("").split(boundary);
        for (String p : ps) {
            if (p.equals("") || p.equals("--\r\n")) {
                continue;
            }
            p = p.trim().replaceAll("\r\n", "&&");
            String[] ds = p.split(";");
            //获得参数名
            String nameMeta = Arrays.asList(ds).stream()
                    .filter(d -> d.trim().startsWith("name="))
                    .findAny()
                    .orElse("");
            String name = Optional.ofNullable(nameMeta.split("\"")[1]).orElse("");
            //获得参数值
            String value = Optional.ofNullable(StringUtils.substringAfter(p, "&&&&")).orElse("");
            map.put(name, value);
        }
        return map;
    }


}