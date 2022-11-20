package com.mao.demo.controller;

import com.mao.demo.common.annotation.GlobalResponse;
import com.mao.demo.entity.Copyright;
import com.mao.demo.entity.User;
import com.mao.demo.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@GlobalResponse
@Api(tags = "管理接口")
@RequestMapping("admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @ApiOperation("查询所有用户")
    @GetMapping("/allUsers")
    public List<User> getAllUsers() throws Exception {
        return adminService.getUsers();
    }

    @ApiOperation("所有版权信息")
    @GetMapping("/allCopyrights")
    public List<Copyright> getCopyrights() throws Exception {
        return adminService.getCopyrights();
    }

    @ApiOperation("根据ID查询")
    @PostMapping("/queryByID")
    public List<Copyright> queryCopyrights(@RequestParam Integer copyrightID) throws Exception {
        return adminService.queryCopyrights(copyrightID);
    }
}
