package com.jqh.forum.search;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import util.IdWorker;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;


/**
 * @Auther: 几米
 * @Date: 2019/5/22 18:02
 * @Description: 站内搜索模块儿，目前有文章检索
 */
@SpringCloudApplication
@EnableFeignClients
public class SearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }

//    @Bean
//    public StringHttpMessageConverter stringHttpMessageConverter() {
//        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
//        stringHttpMessageConverter.setDefaultCharset(Charset.forName("UTF-8"));
//        ArrayList<MediaType> types = new ArrayList<>();
//        types.add(MediaType.valueOf("text/html;charset=UTF-8"));
//        stringHttpMessageConverter.setSupportedMediaTypes(types);
//        return stringHttpMessageConverter;
//    }
//
////    *
////     *  * 解决异常信息：
////     *  *  java.lang.IllegalArgumentException:
////     *  *      Invalid character found in the request target. The valid characters are defined in RFC 7230 and RFC 3986
////     *  * @return
////     *  
//
//    @Bean
//    public ConfigurableServletWebServerFactory webServerFactory() {
//        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
//        factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
//            @Override
//            public void customize(Connector connector) {
//                connector.setProperty("relaxedQueryChars", "|{}[]");
//            }
//        });
//        return factory;
//    }
}
