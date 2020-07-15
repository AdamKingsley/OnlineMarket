package com.cmbchina.o2o.wd.onlinemarket.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class JsonUtil {

    public static boolean contains(Object obj, String key) {
        JSONObject object = (JSONObject) JSONObject.toJSON(obj);
        return object.containsKey(key);
    }

    public static String getValue(Object obj, String key) {
        JSONObject object = (JSONObject) JSONObject.toJSON(obj);
        if (object.containsKey(key)) {
            return object.get(key).toString();
        }
        return null;
    }

    public static String prettyJson(Object result) {
        if (Objects.isNull(result)) {
            return "null";
        }
        return JSON.toJSONString(result, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
    }

    public static String toString(Object result) {
        if (Objects.isNull(result)) {
            return "null";
        }
        return JSON.toJSONString(result);
    }

    public static JSONObject parseObject(String data) {
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        return JSONObject.parseObject(data);
    }
}
