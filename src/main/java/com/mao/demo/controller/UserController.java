package com.mao.demo.controller;

import com.mao.demo.common.annotation.NotResponseBody;
import com.mao.demo.common.utils.*;
import com.mao.demo.entity.Copyright;
import com.mao.demo.entity.User;
import com.mao.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.*;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

/**
 * @author mhh
 * @description 用户接口
 */
@Slf4j
@RestController
@Api(tags = "用户接口")
@RequestMapping("user")
public class UserController {

    // 绑定文件上传路径到uploadPath
    @Value("${web.upload-path}")
    private String uploadPath;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private HttpServletRequest request;

    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @ApiOperation("创建用户账号")
    @PostMapping("/addUser")
    @NotResponseBody
    public ResponseEntity<InputStreamResource> addUser(@RequestBody @Valid User user) {
        log.info("<==== 创建用户账号 ===>");

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String time = formatter.format(date);
        String fileName = "CST-"+ time + "-Wallet.json";
        try {
            // 1.创建钱包
            String keystore = WalletUtils.createWallet(user.getPassword());
            // 2.通过钱包获取凭证
            String privateKey = WalletUtils.decryptWallet(keystore, user.getPassword());
            // 3.添加用户
            userService.addUser(user, privateKey);
            // 4.返回新用户的钱包
            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(keystore.getBytes(StandardCharsets.UTF_8));

            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            log.info("<==== 创建用户账号[成功] ===>");
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(arrayInputStream.available())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(arrayInputStream));

        } catch (Exception e) {
            log.error("操作异常", e);
            return null;
        }
    }
    @ApiOperation("查询用户信息")
    @PostMapping("/getUserInfo")
    @NotResponseBody
    public Map<String,Object> getUserInfo(@RequestParam("file") MultipartFile file, @RequestParam("password") String password) {
        log.info("<==== 查询用户信息 ===>");
        try {
            // 1.获取凭证
            String keystore = new String(file.getBytes());
            String privatekey = WalletUtils.decryptWallet(keystore, password);
            // 2.执行操作
            User user = userService.getUserInfo(privatekey);
            // 3.注册sessionid
            // 以秒为单位，即在没有活动120分钟后，session将失效
            request.getSession().setMaxInactiveInterval(120*60);
            // 私钥存入该用户的session 中
            request.getSession().setAttribute("privatekey", privatekey);

            Map<String,Object> result = new HashMap<>();
            result.put("code",1000);
            result.put("msg","success");
            result.put("data",user);
            log.info("<==== 查询用户信息[成功] ===>");
            return result;
        } catch (Exception e) {
            log.error("操作异常", e);
            return null;
        }
    }

    @ApiOperation("查询用户版权")
    @GetMapping("/userCopyrights")
    @ResponseBody
    public List<Copyright> getUserCopyrights() {
        log.info("<==== 查询用户版权 ===>");
        try {
            // 从Session加载客户私钥
            String privatekey = (String) request.getSession().getAttribute("privatekey");
            List<Copyright> copyrightList = userService.getUserCopyrights(privatekey);
            log.info("<==== 查询用户版权[成功] ===>");
            return copyrightList;
        } catch (Exception e) {
            log.error("操作异常", e);
            return null;
        }
    }

    @ApiOperation("添加版权信息")
    @PostMapping("/addCopyright")
    @ResponseBody
    public void addWaterMark(@RequestParam("image")MultipartFile image, boolean isText, String watermark, HttpServletResponse response)  {
        log.info("<==== 添加版权信息 ===>");
        try {
            // 检查上传的图片，如果不符合要求 则终止
            if (checkImage(image, isText)) return;

            // 1、创建存储水印文件的存储目录
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
            String format = sdf.format(new Date());
            File folder = new File(uploadPath + format);
            if (!folder.exists()) {
                boolean mkdirs = folder.mkdirs();
            }
            // 2.对上传的文件重命名，避免文件重名
            String fileName = image.getOriginalFilename();
            String outputFileName = UUID.randomUUID().toString() + ".png";
            // 3、输入图片、输出图片、二维码图片的临时文件
            File tempInputFile = File.createTempFile("image", fileName);
            File tempOutputFile = new File(folder, outputFileName);
            File tempOutputQRFile = File.createTempFile("image",".png");
            // 临时存储输入文件
            image.transferTo(tempInputFile);
            String imagePath = tempInputFile.getAbsolutePath();
            String outputPath = tempOutputFile.getAbsolutePath();
            String outputQR = tempOutputQRFile.getAbsolutePath();
            // 开始加水印 文本则使用DFT, 否则使用DCT
            if (isText){
                WaterMarkDFT.embed(imagePath, watermark, outputPath);
            }else{
                // 将水印内容转换成二维码（生成默认的100*100尺寸的二维码）
                new QRCodeUtils().QREncode(watermark, outputQR);
                WaterMarkDCT.embed(imagePath, outputQR, outputPath);
            }

            // 从session加载私钥
            String privatekey = (String) request.getSession().getAttribute("privatekey");

            Copyright copyright = new Copyright();
            // 原图哈希值
            copyright.setPictureHash(HashUtils.fileMD5HashCode(imagePath));
            // 水印信息
            copyright.setWaterMark(watermark);
            // 加水印后图片路径（可扩展为IPFS，暂时为临时文件）
            copyright.setPicturePath(outputPath);
            // 上链操作
            String result = userService.addCopyright(copyright, privatekey);

            // 返回处理后的文件
            download(response, outputPath);
            // 5.删除临时文件
            boolean isDelete1 = tempInputFile.delete();
            boolean isDelete2 = tempOutputQRFile.delete();
            if (isDelete1 && isDelete2){
                log.info("<==== 临时文件已删除 ===>");
            }
            log.info(result);
            log.info("<==== 添加版权信息[成功] ===>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("提取水印信息")
    @PostMapping(value = "/getWatermark",produces = {MediaType.IMAGE_PNG_VALUE})
    @NotResponseBody
    public byte[] getWaterMark(@RequestParam("image")MultipartFile image, boolean isText)  {
        try {
            // 检查上传的图片，如果不符合要求 则终止
            if (checkImage(image, isText)) return null;
            // 1.原始文件名
            String fileName = image.getOriginalFilename();
            // 2.创建临时文件
            File tempInputFile = File.createTempFile("temp",fileName);
            File tempOutputFile = File.createTempFile("temp",".png");
            image.transferTo(tempInputFile);
            String imagePath = tempInputFile.getAbsolutePath();
            String outputPath = tempOutputFile.getAbsolutePath();
            // 3.提取水印
            if (isText){
                WaterMarkDFT.extract(imagePath, outputPath);
            }else{
                WaterMarkDCT.extract(imagePath, outputPath);
            }
            // 4.返回水印信息
            File file = new File(outputPath);
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            int readLength = inputStream.read(bytes, 0, inputStream.available());
            // 5.关闭流、删除临时文件
            inputStream.close();
            boolean delete0 = tempInputFile.delete();
            boolean delete1 = tempOutputFile.delete();
            if (delete0 && delete1){
                log.info("<==== 临时文件已删除 ===>");
            }
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean checkImage(MultipartFile image, boolean isText) throws IOException {
        Image picture = ImageIO.read(image.getInputStream());
        // 如果不是图片, 返回false
        if (picture == null){
            return true;
        }
        // 是图片，则判断水印类别以及要求的图片尺寸
        if (isText){
            return false;
        } else {
            // (二维码水印 && 图片尺寸 >= 800 * 800 返回true)
            return (picture.getWidth(null) < 800) || (picture.getHeight(null) < 800);
        }
    }

    private void download(HttpServletResponse response, String filePath) throws Exception{
        File file = new File(filePath);
        // 1、设置response 响应头
        // 设置页面不缓存,清空buffer
        response.reset();
        // 字符编码
        response.setCharacterEncoding("UTF-8");
        // 二进制传输数据
        response.setContentType("multipart/form-data");
        // 设置响应头
        response.setHeader("Content-Disposition", "attachment;fileName="+ URLEncoder.encode(file.getName(), "UTF-8"));
        // 2、 读取文件--输入流
        InputStream input = new FileInputStream(file);
        // 3、 写出文件--输出流
        OutputStream out = response.getOutputStream();
        byte[] buff = new byte[1024];
        int length;
        // 4、执行写出操作
        while((length= input.read(buff))!= -1){
            out.write(buff, 0, length);
            out.flush();
        }
        out.close();
        input.close();
    }
}
