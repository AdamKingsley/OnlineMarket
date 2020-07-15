package com.cmbchina.o2o.wd.onlinemarket.util;

import com.google.common.base.Strings;
import org.apache.http.entity.ContentType;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RestUtils {


    private static RestTemplate restTemplate = new RestTemplate();

    private HttpEntity httpEntity;

    /**
     * 构造函数已经私有化，只能通过create方法构造HttpContent
     *
     * @return HttpContent
     */
    public static RestUtils create() {
        return new RestUtils();
    }

    // /**
    //  * get请求返回模板对象
    //  *
    //  * @param url   url
    //  * @param clazz T.class
    //  * @param <T>   T
    //  * @return T
    //  */
    // public static <T> T getForObj(String url, Class<T> clazz) {
    //     return RestUtils.restTemplate.getForObject(url, clazz);
    // }

    public <T> T getForObj(String url, Class<T> clazz) {
        this.build();
        return restTemplate.getForObject(url, clazz);
    }

    /**
     * get请求返回模板对象
     *
     * @param url   url
     * @param clazz t.class
     * @param <T>   模板
     * @return T
     */
    public <T> T postForObj(String url, Class<T> clazz) {
        this.build();
        return restTemplate.postForObject(url, this.httpEntity, clazz);
    }

    /**
     * header
     */
    private HttpHeaders httpHeaders;

    private String contentType;

    /**
     * body
     */
    private HashMap<String, Object> params;

    private RestUtils() {
    }

    /**
     * 添加Header内容
     *
     * @param headerName  key
     * @param headerValue value
     * @return HttpContent
     */
    public RestUtils addHeader(String headerName, String headerValue) {
        if (Objects.isNull(httpHeaders)) {
            httpHeaders = new HttpHeaders();
        }
        httpHeaders.add(headerName, headerValue);
        return this;
    }

    /**
     * 设置请求体
     *
     * @param key   key
     * @param value value
     * @return HttpContent
     */
    public RestUtils addBody(String key, Object value) {
        if (Objects.isNull(params)) {
            params = new HashMap<>();
        }
        params.put(key, value);
        return this;
    }

    /**
     * 设置content-Type
     *
     * @param contentType string
     * @return HttpContent
     */
    public RestUtils setContentType(String contentType) {
        if (Strings.isNullOrEmpty(contentType)) {
            throw new RuntimeException("content-Type can not be empty,please check your mediaType and try again ~");
        }
        this.contentType = contentType;
        if (Objects.isNull(httpHeaders)) {
            httpHeaders = new HttpHeaders();
        }
        httpHeaders.setContentType(MediaType.parseMediaType(contentType));
        return this;
    }

    /**
     * 构建HttpEntity对象
     * 不设置Content-Type默认application/json
     */
    private void build() {
        if (Objects.isNull(this.params)) {
            throw new RuntimeException("body must be not empty,please add body and try again ~");
        }
        if (Objects.isNull(contentType) || ContentType.APPLICATION_JSON.getMimeType().equals(contentType)) {
            this.setContentType(ContentType.APPLICATION_JSON.getMimeType());
            JSONObject jsonObject = new JSONObject();
            for (String s : params.keySet()) {
                try {
                    jsonObject.put(s, params.get(s));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            String body = jsonObject.toString();
            this.httpEntity = new HttpEntity<>(body, httpHeaders);
        } else if (ContentType.APPLICATION_FORM_URLENCODED.getMimeType().equals(contentType)) {
            MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
            for (String s : params.keySet()) {
                formData.add(s, params.get(s));
            }
            this.httpEntity = new HttpEntity<>(formData, httpHeaders);
        } else {
            throw new RuntimeException("sorry this tool only support content-Type include {application/json,multipart/form-data}");
        }
    }

    public RestUtils addHeaders(Map<String, String> header) {
        header.forEach((key, value) -> this.addHeader(key, value));
        return this;
    }

    public RestUtils addBody(Map<String, String> requestBody) {
        requestBody.forEach((key, value) -> this.addBody(key, value));
        return this;
    }
}
