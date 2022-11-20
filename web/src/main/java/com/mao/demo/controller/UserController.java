package com.mao.demo.controller;

import com.mao.demo.common.annotation.GlobalResponse;
import com.mao.demo.common.annotation.NotResponseBody;
import com.mao.demo.common.exception.APIException;
import com.mao.demo.common.response.ResponseResult;
import com.mao.demo.dto.CopyrightDTO;
import com.mao.demo.dto.UserDTO;
import com.mao.demo.entity.Copyright;
import com.mao.demo.entity.User;
import com.mao.demo.service.UserService;
import com.mao.demo.utils.WalletUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@GlobalResponse
@RequestMapping("user")
@Api(tags = "用户管理")
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private ModelMapper modelMapper;

    @ApiOperation("创建用户钱包")
    @PostMapping("register")
    @NotResponseBody
    public ResponseEntity<InputStreamResource> addUser(@RequestBody @Valid UserDTO userDTO) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String time = formatter.format(date);
        String fileName = "CST-" + time + "-Wallet.json";

        // 1.创建钱包
        String keystore = WalletUtils.createWallet(userDTO.getPassword());
        // 2.通过钱包获取凭证
        String privateKey = WalletUtils.decryptWallet(keystore, userDTO.getPassword());
        // 3.添加用户
        User user = modelMapper.map(userDTO, User.class);
        userService.addUser(user, privateKey);
        // 4.返回新用户的钱包
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(keystore.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment;" + URLEncoder.encode(fileName, "UTF-8"));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(arrayInputStream.available())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(arrayInputStream));
    }

    @ApiOperation("使用钱包登录")
    @PostMapping("/login")
    public User getUserInfo(@RequestParam("wallet") MultipartFile wallet,
                            @RequestParam("password") String password,
                            HttpServletRequest request) throws Exception {
        // 1.获取凭证
        String keystore = new String(wallet.getBytes());
        String privatekey = WalletUtils.decryptWallet(keystore, password);
        if (privatekey == null) {
            throw new APIException("钱包密码不正确");
        }
        // 2.执行操作
        User user = userService.getUserInfo(privatekey);
        // 3.注册sessionid
        request.getSession().setMaxInactiveInterval(120 * 60);
        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("privatekey", privatekey);

        return user;
    }

    @ApiOperation("查询链上信息")
    @GetMapping()
    public List<Copyright> getUserCopyrights(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        String privatekey = (String) session.getAttribute("privatekey");
        return userService.getUserCopyrights(privatekey);
    }

    @ApiOperation("存证信息上链")
    @PostMapping("/addCopyright")
    public ResponseResult<String> addWaterMark(@RequestBody @Valid CopyrightDTO copyrightDTO, HttpServletRequest request) throws Exception {
        // 从session加载私钥
        String privatekey = (String) request.getSession().getAttribute("privatekey");
        // 转换实体 DTO -> Entity
        copyrightDTO.setPictureHash(copyrightDTO.getPicturePath());
        Copyright copyright = modelMapper.map(copyrightDTO, Copyright.class);
        // 上链操作
        String result = userService.addCopyright(copyright, privatekey);

        return new ResponseResult<>(result);
    }
}
