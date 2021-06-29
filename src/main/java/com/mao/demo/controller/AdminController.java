package com.mao.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.mao.demo.common.annotation.NotResponseBody;
import com.mao.demo.entity.Copyright;
import com.mao.demo.entity.User;
import com.mao.demo.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Map<String, Object> queryCopyrights(@RequestBody String params) {
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
}
