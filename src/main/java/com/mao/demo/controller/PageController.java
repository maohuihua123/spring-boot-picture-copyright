package com.mao.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.mao.demo.entity.Copyright;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author mhh
 * @date: 2021/6/16
 * @Description: 页面路由控制
 */
@Slf4j
@Controller
@Api(tags = "路由控制")
@RequestMapping("/")
public class PageController {

    @RequestMapping("/index.html")
    public String index(){
        log.info("index.html");
        return "index";
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

//        // 构造jsonArray
//        JSONArray jsonArray = new JSONArray();
//        Map<String,String> map = new HashMap<>();
//        map.put("id","1");
//        map.put("image","https://gw.alipayobjects.com/zos/rmsportal/gLaIAoVWTtLbBWZNYEMg.png");
//        map.put("title","Alipay");
//        map.put("remark","那是一种内在的东西， 他们到达不了，也无法触及的");
//        map.put("time",copyright.getCreateTime());
//        jsonArray.add(map);

        model.addAttribute("data",copyright);
        return "view/system/operate/detail";
    }

    @RequestMapping("/view/system/operate/add.html")
    public String add(){
        log.info("add.html");
        return "view/system/operate/add";
    }
}
