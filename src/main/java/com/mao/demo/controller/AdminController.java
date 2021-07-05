package com.mao.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mao.demo.common.annotation.NotResponseBody;
import com.mao.demo.entity.Copyright;
import com.mao.demo.entity.User;
import com.mao.demo.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author mhh
 * @description 系统首页接口
 */
@Slf4j
@RestController
@Api(tags = "首页接口")
@RequestMapping("home")
public class AdminController {

    // 网站的URL
    @Value("${web.url}")
    private String webUrl;

    private AdminService adminService;


    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @ApiOperation("查询所有用户")
    @GetMapping("/getUsers")
    @NotResponseBody
    public Map<String, Object> getUsers() {
        log.info("<==== 获取所有用户信息 ===>");
        try {
            List<User> userList = adminService.getUsers();
            Map<String, Object> result = new HashMap<>();
            result.put("code",0);
            result.put("msg","ok");
            result.put("count",userList.size());
            result.put("data",userList);

            log.info("<==== 获取所有用户信息[成功] ===>");
            return result;
        } catch (Exception e) {
            log.info("数据读取异常", e);
            return null;
        }
    }

    @ApiOperation("所有版权信息")
    @GetMapping("/getCopyrights")
    @NotResponseBody
    public Map<String, Object> getCopyrights() {
        log.info("<==== 获取所有版权信息 ===>");
        try {
            List<Copyright> copyrightList = adminService.getCopyrights();

            Map<String, Object> result = new HashMap<>();
            result.put("code",0);
            result.put("msg","ok");
            result.put("count",copyrightList.size());
            result.put("data",copyrightList);

            log.info("<=== 获取所有版权信息[成功] ===>");
            return result;
        } catch (Exception e) {
            log.info("数据读取异常", e);
            return null;
        }
    }

    @ApiOperation("查询版权信息")
    @PostMapping("/queryCopyrights")
    @NotResponseBody
    public Map<String, Object> queryCopyrights(String params) {
        log.info("<==== 查询版权信息 ===>");
        JSONObject jsonObject = JSONObject.parseObject(params);
        String copyrightID = jsonObject.getString("copyrightID");
        int id = Integer.parseInt(copyrightID);
        try {
            List<Copyright> copyrightList = adminService.queryCopyrights(id);

            Map<String, Object> result = new HashMap<>();
            result.put("code",0);
            result.put("msg","ok");
            result.put("count",copyrightList.size());
            result.put("data",copyrightList);

            log.info("<==== 查询版权信息[成功] ===>");
            return result;
        } catch (Exception e) {
            log.info("数据读取异常", e);
            return null;
        }
    }
    @ApiOperation("版权列表展示")
    @GetMapping("/copyrightsCard")
    @NotResponseBody
    public Map<String, Object> copyrightsCard() {
        log.info("<==== 版权列表展示 ===>");
        try {
            List<Copyright> copyrightList = adminService.getCopyrights();
            // 构造jsonArray
            JSONArray jsonArray = new JSONArray();
            for (Copyright copyright : copyrightList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",copyright.getPictureID());
                jsonObject.put("image", webUrl + copyright.getPicturePath()); // 加上网站的url
                jsonObject.put("title","水印信息："+copyright.getWaterMark());
                jsonObject.put("remark","原图md5值："+copyright.getPictureHash());
                jsonObject.put("time","存证时间："+copyright.getCreateTime());
                jsonArray.add(jsonObject);
            }
            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("code",0);
            result.put("msg","ok");
            result.put("count",copyrightList.size());
            result.put("data",jsonArray);

            log.info("<==== 版权列表展示[成功] ===>");
            return result;
        } catch (Exception e) {
            log.info("数据读取异常", e);
            return null;
        }
    }

    @ApiOperation("版权详细信息")
    @GetMapping("/copyrightDetail")
    @NotResponseBody
    public Map<String, Object> copyrightDetail(@RequestParam("copyrightID")String copyrightID) {
        log.info("<==== 版权详细信息 ===>");
        int id = Integer.parseInt(copyrightID);
        try {
            List<Copyright> copyrightList = adminService.queryCopyrights(id);
            Copyright copyright = copyrightList.get(0);
            // 构造jsonArray
            JSONArray jsonArray = new JSONArray();
            Map<String,String> map = new HashMap<>();
            map.put("id","1");
            // 加上网站的url
            map.put("image", webUrl + copyright.getPicturePath());
            map.put("title","水印信息："+copyright.getWaterMark());
            map.put("remark","原图md5值："+copyright.getPictureHash());
            map.put("time","存证时间："+copyright.getCreateTime());
            jsonArray.add(map);
            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("code",0);
            result.put("msg","ok");
            result.put("count",copyrightList.size());
            result.put("data",jsonArray);

            log.info("<==== 版权详细信息[成功] ===>");
            return result;
        } catch (Exception e) {
            log.info("数据读取异常", e);
            return null;
        }
    }
}
