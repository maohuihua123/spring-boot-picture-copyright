package com.mao.demo.Interceptor;

import com.mao.demo.common.utils.JsonUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        HttpSession session = request.getSession();
        String privateKey = (String) session.getAttribute("privatekey");
        if (privateKey != null) {
            return true;
        }
        unauthorizedResponse(response);
        return false;
    }

    private void unauthorizedResponse(HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        response.setContentType("Application/json;charset=utf-8");
        // 构造返回响应体
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("code", "401");
        resultMap.put("message", "请先登陆");
        resultMap.put("data", "Unauthorized");
        String resultString = JsonUtils.toJson(resultMap);
        outputStream.write(resultString.getBytes(StandardCharsets.UTF_8));
    }

}
