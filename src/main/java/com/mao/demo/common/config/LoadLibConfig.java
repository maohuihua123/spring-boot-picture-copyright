//package com.mao.demo.base.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//
///**
// * @author mhh
// * @date: 2021/6/10
// * @description 加载动态库
// */
//@Slf4j
//@Configuration
//public class LoadLibConfig {
//
//    private static final String libName = "opencv_java452";
//    private static final String libDir = "lib/";
//
////    static {
////        // 加载lib
////        loadLib(libDir, libName);
////    }
//
//    /**
//     * SpringBoot打包后，就是一个独立的文件。
//     * 使用ClassPathResource读取classpath下的lib文件，
//     * 然后copy到本地磁盘，再从文件系统去加载。
//     * 注：仅在Windows 10下测试过
//     */
//    public static void loadLib(String libDir, String libName) {
//        // 1.判断操作系统 根据操作系统加载动态库
//        String systemType = System.getProperty("os.name");
//        // 如果是win则后缀为.dll
//        String libExtension = (systemType.toLowerCase().contains("win")) ? ".dll" : ".so";
//        // lib的名称+后缀 如：opencv_java452.dll
//        String libFullName = libName + libExtension;
//        // 加上路径 如：lib/opencv_java452.dll
//        String libPath = libDir + libFullName;
//        try {
//            // 读取Resource下的动态库
//            ClassPathResource classPathResource = new ClassPathResource(libPath);
//            InputStream in = classPathResource.getInputStream();
//            // 将动态库提取到jar包所在的目录下
//            File libFile = new File(libFullName);
//            // 如果目录下不存在动态库，则进行拷贝
//            if (!libFile.exists()){
//                FileOutputStream fos = new FileOutputStream(libFile);
//                byte[] buffer = new byte[in.available()];
//                int readLength = in.read(buffer);
//                fos.write(buffer);
//                fos.flush();
//                fos.close();
//            }
//            in.close();
//            System.load(libFile.getAbsolutePath());
//            log.info("加载外部动态库成功===>"+libFile.getAbsolutePath());
//        }catch (Exception e){
//           log.error("加载动态库失败",e);
//        }
//    }
//}
