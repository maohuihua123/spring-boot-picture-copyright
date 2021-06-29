package com.mao.demo.common.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class QRCodeUtils {

    // 二维码的宽度
    private int width;
    // 二维码的高度
    private int heigth;
    // 二维码的格式
    private String format;
    // 二维码参数
    private final Map<EncodeHintType, Object> paramMap;


    public QRCodeUtils() {
        // 1.默认尺寸
        this.width = 100;
        this.heigth = 100;
        // 2.默认格式
        this.format = "png";
        // 3，默认参数
        // 定义二维码的参数
        this.paramMap = new HashMap<>();
        // 设置二维码字符编码
        paramMap.put(EncodeHintType.CHARACTER_SET, CharacterSetECI.UTF8);
        // 设置二维码纠错等级
        paramMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        // 设置二维码边距
        paramMap.put(EncodeHintType.MARGIN, 4);

    }

    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * @param size 二维码尺寸大小
     */
    public void setSize(int size) {
        this.width = size;
        this.heigth = size;
    }

    /**
     * @param type 参数类型
     * @param param 参数值
     *    1.编码：EncodeHintType.CHARACTER_SET, CharacterSetECI
     *    2.纠错等级：EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M
     *    3.二维码边距：EncodeHintType.MARGIN, int
     *    等
     */
    public void setParam(EncodeHintType type, Object param) {
        paramMap.put(type,param);
    }

    /**
     * 生成二维码
     *
     * @param content  二维码内容
     * @param outPutFile 二维码输出路径
     */
    public void QREncode(String content,String outPutFile) throws Exception {
        // 开始生成二维码
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, heigth, paramMap);
        // 导出到指定目录
        Path path = new File(outPutFile).toPath();
        MatrixToImageWriter.writeToPath(bitMatrix, format, path);
    }

    /**
     * 解析二维码
     *
     * @param filePath 待解析的二维码路径
     * @return 二维码内容
     */
    public String QRReader(String filePath) throws Exception {
        File file = new File(filePath);
        MultiFormatReader formatReader = new MultiFormatReader();
        // 读取指定的二维码文件
        BufferedImage bufferedImage = ImageIO.read(file);
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        HybridBinarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap binaryBitmap= new BinaryBitmap(binarizer);
        //定义二维码参数
        Map hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, CharacterSetECI.UTF8);
        Result result = formatReader.decode(binaryBitmap, hints);
        bufferedImage.flush();
        return result.getText();
    }

}
