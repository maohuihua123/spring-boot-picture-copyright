package com.mao.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.mao.demo.entity.Copyright;
import com.mao.demo.entity.User;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author mhh
 * @date: 2021/6/16
 * @Description: 页面路由控制
 */
@Slf4j
@Controller
@Api(tags = "路由控制")
@RequestMapping("")
public class PageController {

    private HttpServletRequest request;

    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @RequestMapping("/index.html")
    public String index(Model model){
        log.info("index.html");
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("nikeName", user.getNickName());
        return "index";
    }

    @RequestMapping("/login.html")
    public String login(){
        log.info("login.html");
        // 清除session
        request.getSession().invalidate();
        return "login";
    }

    @RequestMapping("/register.html")
    public String register(){
        log.info("register.html");
        return "view/system/register";
    }

    @RequestMapping("/view/console/console1.html")
    public String console1(){
        log.info("console1.html");
        return "view/console/console1";
    }

    @RequestMapping("/view/console/console2.html")
    public String console2(){
        log.info("console2.html");
        return "view/console/console2";
    }

    @RequestMapping("/view/system/user.html")
    public String user(){
        log.info("user.html");
        return "view/system/user";
    }


    @RequestMapping("/view/system/role.html")
    public String role(){
        log.info("role.html");
        return "view/system/role";
    }

    @RequestMapping("/view/document/card.html")
    public String card(){
        log.info("card.html");
        return "view/document/card";
    }

    @RequestMapping("/view/system/operate/detail.html")
    public String detail(String data, Model model){
        log.info("detail.html");
        JSONObject jsonData = (JSONObject) JSONObject.parse(data);
        Copyright copyright = jsonData.toJavaObject(Copyright.class);
        model.addAttribute("data",copyright.getPictureID());
        return "view/system/operate/detail";
    }

    @RequestMapping("/view/system/operate/add.html")
    public String add(){
        log.info("add.html");
        return "view/system/operate/add";
    }

}
