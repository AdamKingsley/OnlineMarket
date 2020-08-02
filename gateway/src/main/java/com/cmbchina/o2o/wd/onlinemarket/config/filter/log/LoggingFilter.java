package com.cmbchina.o2o.wd.onlinemarket.config.filter.log;

import com.cmbchina.o2o.wd.onlinemarket.util.JsonUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.annotation.meta.Exclusive;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;


@Component
@Slf4j
public class LoggingFilter extends ZuulFilter {

    private static final String EXCLUSIVE_RESPONSE_URL = "/upload/image";

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;//要打印返回信息，必须得用"post"
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 2;
    }

    @Override
    public boolean shouldFilter() {
        // RequestContext context = RequestContext.getCurrentContext();
        // Boolean isSuccess = (boolean) context.get("isSuccess");
        // return isSuccess;
        return true;
    }

    @Override
    public Object run() {
        try {
            log.info("---------------------  Zuul Logging Filter Start  -------------------------");
            RequestContext ctx = RequestContext.getCurrentContext();
            HttpServletRequest request = ctx.getRequest();

            InputStream in = request.getInputStream();
            String method = request.getMethod();
            String interfaceMethod = request.getServletPath();
            log.info("Request method = {}, url = {}", method, interfaceMethod);
            String reqBody = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
            if ("GET".equals(method.toUpperCase())) {
                Map<String, String[]> map = request.getParameterMap();
                // 打印请求url参数
                log.info("Request Params = {}", JsonUtil.toString(map));
            } else if ("POST".equals(method.toUpperCase())) {
                Map<String, String[]> map = request.getParameterMap();
                // 打印请求url参数
                if (map.size() > 0) {
                    log.info("Request Params = {}", JsonUtil.toString(map));
                }
                //打印请求json参数
                if (reqBody != null) {
                    String conType = request.getHeader("content-type");
                    //post请求目前获取userFlag，user参数只支持multipart/form-data，application/json，对于其他方式不记录用户信息
                    if (conType.contains("multipart/form-data") || conType.contains("application/json")) {
                        if (conType.contains("multipart/form-data")) {
                            //  处理文件日志
                            log.info("request content type is multipart/form-data, upload file operation!");
                            // reqBody = JsonUtil.toString(MultiPartFormDataToJson.toMap(reqBody));
                        } else {
                            // 默认content-type传json-->application/json
                            log.info("Request Body = {}", JsonUtil.toString(JsonUtil.parseObject(reqBody)));
                        }
                    }
                }
            }
            if (interfaceMethod.startsWith(EXCLUSIVE_RESPONSE_URL)){
                // 请求静态资源文件的请求需要过滤，不能获取流重新写入
                log.info("Response Body : = {}", interfaceMethod);
                return null;
            }
            //打印response
            InputStream out = ctx.getResponseDataStream();
            String outBody = StreamUtils.copyToString(out, Charset.forName("UTF-8"));
            log.info("Response Body : = {}", outBody);
            //必须重新写入流//重要！！！
            ctx.setResponseBody(outBody);
            log.info("---------------------  Zuul Logging Filter End  -------------------------");
        } catch (IOException e) {
            log.error("IOException occurs cannot get inputstream", e);
        }
        return null;
    }
}
