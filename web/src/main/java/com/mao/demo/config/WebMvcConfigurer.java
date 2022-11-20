package com.mao.demo.config;

import com.mao.demo.Interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvcConfigurer implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {

    @Resource
    private LoginInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludeList = new ArrayList<>();
        // swagger放行
        excludeList.add("/webjars/**");
        excludeList.add("/swagger/**");
        excludeList.add("/v2/**");
        excludeList.add("/doc.html/**");
        excludeList.add("/swagger-ui.html/**");
        excludeList.add("/swagger-resources/**");
        // 需要放行的接口
        excludeList.add("/user/login");
        excludeList.add("/user/register");

        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludeList);
    }
}
