package com.mao.demo.Interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

// 拦截器配置类
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer  {

    private MyInterceptor interceptor;

    @Autowired
    public void setInterceptor(MyInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> jwtExcludePatterns = new ArrayList<>();
        // swagger放行
        jwtExcludePatterns.add("/webjars/**");
        jwtExcludePatterns.add("/swagger/**");
        jwtExcludePatterns.add("/v2/**");
        jwtExcludePatterns.add("/doc.html/**");
        jwtExcludePatterns.add("/swagger-ui.html/**");
        jwtExcludePatterns.add("/swagger-resources/**");
        // 系统静态资源的放行
        jwtExcludePatterns.add("/admin/**");
        jwtExcludePatterns.add("/component/**");
        jwtExcludePatterns.add("/config/**");
        jwtExcludePatterns.add("/login.html");
        jwtExcludePatterns.add("/register.html");
        // 需要放行的接口
        jwtExcludePatterns.add("/user/getUserInfo");
        jwtExcludePatterns.add("/user/addUser");
        // 添加拦截器
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(jwtExcludePatterns);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*静态资源的位置*/
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        /*放行swagger*/
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 当访问根路径时，如http://localhost:8080/  跳转到登录界面  ！！！如无此需求将此方法体中的代码删除即可！！！
        registry.addViewController("").setViewName("index.html");
    }
}
