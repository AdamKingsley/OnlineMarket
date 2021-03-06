package com.cmbchina.o2o.wd.onlinemarket.config.processor;


import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

// @Configuration
public class SnakeToCamelWebConfig extends WebMvcConfigurationSupport {

    @Override
    protected RequestMappingHandlerAdapter createRequestMappingHandlerAdapter() {
        return new SnakeToCamelRequestMappingHandlerAdapter();
    }

    // @Override
    // protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
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
    //     converters.add(converter);
    // }
}