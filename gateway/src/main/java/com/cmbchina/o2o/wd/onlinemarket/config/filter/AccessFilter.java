package com.cmbchina.o2o.wd.onlinemarket.config.filter;

import com.alibaba.fastjson.JSONObject;
import com.cmbchina.o2o.wd.onlinemarket.constant.Strings;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.feign.AccountFeign;
import com.cmbchina.o2o.wd.onlinemarket.util.JsonUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
@Slf4j
public class AccessFilter extends ZuulFilter {

    //排除拦截的路径
    // private static final String LOGIN_URI = "account/oauth/login";

    //无权限时的提示语
    // private static final String INVALID_TOKEN = "您没有权限进行此操作";

    // private static final String ACCESS_TOKEN_KEY = "access_token";

    @Autowired
    private AccountFeign accountFeign;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //获取上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        //获取request对象
        HttpServletRequest request = currentContext.getRequest();
        //验证token时候 token的参数 从请求头获取
        String token = request.getHeader(Strings.ACCESS_TOKEN_KEY);
        // Enumeration<String> names = request.getParameterNames();
        // Enumeration</String> names1 = request.getAttributeNames();
        // String method = request.getMethod();
        String requestURL = request.getRequestURL().toString();
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(Strings.ACCESS_TOKEN_KEY);
        }
        if (!StringUtils.isEmpty(token)) {
            //添加用户信息
            Result result = accountFeign.user(token);
            JSONObject jsonObject = JsonUtil.parseObject(result.getData().toString());
            if (Objects.nonNull(jsonObject) && jsonObject.containsKey(Strings.ID_KEY)) {
                currentContext.addZuulRequestHeader(Strings.USER_ID_KEY, jsonObject.getString(Strings.ID_KEY));
                // add attribute对于zuul转发不生效
                // request.setAttribute(Keys.USER_ID, jsonObject.getString(Keys.ID));
            }
        }

        // System.out.println("token="+token);
        //封装请求
        //获取参数
        // Map<String, String[]> parameterMap = request.getParameterMap();
        // if (parameterMap == null) {
        //     return null;
        // }
        // //替换,业务逻辑
        // Map<String, List<String>> requestQueryParams = currentContext.getRequestQueryParams();
        // if (requestQueryParams == null) {
        //     requestQueryParams = new HashMap<>(parameterMap.size() * 2);
        // }
        //
        // for (String key : parameterMap.keySet()) {
        //     String[] values = parameterMap.get(key);
        //     List<String> valueList = new LinkedList<>();
        //     for (String value : values) {
        //         valueList.add(value);
        //     }
        //     requestQueryParams.put(key, valueList);
        // }
        //
        // //重新写入参数
        // List<String> valueList = new LinkedList<>();
        // valueList.add(token);
        // requestQueryParams.put("access_token",valueList);
        //
        // currentContext.setRequestQueryParams(requestQueryParams);
        log.info("转译完成, url = {}", request.getRequestURI());
        log.info("转译完成, url = {}", requestURL);
        return null;
    }
}
/*
 * 资源过滤器
 * 所有的资源请求在路由之前进行前置过滤
 * 如果请求头不包含 Authorization参数值，直接拦截不再路由
 */
// @Component
// @Slf4j
// public class AccessFilter extends ZuulFilter {
//
//     /**
//      * 过滤器的类型 pre表示请求在路由之前被过滤
//      *
//      * @return 类型
//      */
//     @Override
//     public String filterType() {
//         return "pre";
//     }
//
//     /**
//      * 过滤器的执行顺序
//      *
//      * @return 顺序 数字越大表示优先级越低，越后执行
//      */
//     @Override
//     public int filterOrder() {
//         return 0;
//     }
//
//     /**
//      * 过滤器是否会被执行
//      *
//      * @return true
//      */
//     @Override
//     public boolean shouldFilter() {
//         return true;
//     }
//
//     /**
//      * 过滤逻辑
//      *
//      * @return 过滤结果
//      */
//     @Override
//     public Object run() {
//         RequestContext requestContext = RequestContext.getCurrentContext();
//         HttpServletRequest request = requestContext.getRequest();
//
//         log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());
//
//         Object accessToken = request.getHeader("Authorization");
//         if (accessToken == null) {
//             log.warn("Authorization token is empty");
//             requestContext.setSendZuulResponse(false);
//             requestContext.setResponseStatusCode(401);
//             requestContext.setResponseBody("Authorization token is empty");
//             return null;
//         }
//         log.info("Authorization token is ok");
//         return null;
//     }
// }