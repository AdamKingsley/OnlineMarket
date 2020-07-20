package com.cmbchina.o2o.wd.onlinemarket;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.cmbchina.o2o.wd.onlinemarket.config.serializer.DateTimeSerializer;
import com.cmbchina.o2o.wd.onlinemarket.constant.Strings;
import org.joda.time.DateTime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

// gateway节点只提供统一接口的服务，并不直接跟数据库相关，因此不配置datasource
@SpringBootApplication
@EnableZuulProxy//动态代理
@EnableDiscoveryClient//发现服务
@EnableEurekaClient
@EnableFeignClients({"com.cmbchina.o2o.wd.onlinemarket.feign"})
@EnableCircuitBreaker//断路器
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    // @Bean
    // public HttpMessageConverters fastJsonHttpMessageConverters() {
    //     //1.需要定义一个convert转换消息的对象;
    //     FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
    //     //2:添加fastJson的配置信息;
    //     FastJsonConfig fastJsonConfig = new FastJsonConfig();
    //     /**
    //      * SerializerFeature.PrettyFormat可以省略，毕竟这会造成额外的内存消耗和流量，
    //      * 下行是用来指定当属性值为null是是否输出：pro:null
    //      * SerializerFeature.SkipTransientField
    //      */
    //     fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
    //     fastJsonConfig.setSerializerFeatures(
    //             SerializerFeature.DisableCircularReferenceDetect,  //禁用循环引用
    //             SerializerFeature.PrettyFormat,
    //             SerializerFeature.IgnoreNonFieldGetter
    //     );
    //     SerializeConfig config = new SerializeConfig();
    //     config.put(DateTime.class, new DateTimeSerializer(Strings.DATE_TIME_FORMAT));
    //     fastJsonConfig.setSerializeConfig(config);
    //     //3处理中文乱码问题
    //     List<MediaType> fastMediaTypes = new ArrayList<>();
    //     fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
    //     //4.在convert中添加配置信息.
    //     fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
    //     fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
    //     HttpMessageConverter<?> converter = fastJsonHttpMessageConverter;
    //     return new HttpMessageConverters(converter);
    // }
}
