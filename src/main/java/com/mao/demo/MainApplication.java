package com.mao.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URL;

@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        // 1.开发时可以如此加载动态库
        URL url = ClassLoader.getSystemResource("lib/opencv_java452.dll");
        System.load(url.getPath());
        SpringApplication.run(MainApplication.class, args);
    }

}
