package com.mao.demo.common.utils;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public class HashUtils {

    public static byte[] toBytes32(String str){
        byte[] strBytes = str.getBytes();
        byte[] bytes32 = new byte[32];
        System.arraycopy(strBytes, 0, bytes32, 0, strBytes.length);
        return bytes32;
    }

    /**
     * 获取文件的md5值
     *
     * @param filePath	文件路径
     * @return 文件的哈希值
     */
    public static String fileMD5HashCode(String filePath) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            // 拿到一个MD5转换器,如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 分多次将一个文件读入，对于大型文件而言，比较推荐这种方式，占用内存比较少。
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            fis.close();
            // 转换并返回包含16个元素字节数组,返回数值范围为-128到127
            byte[] md5Bytes  = md.digest();
            // 1代表绝对值
            BigInteger bigInt = new BigInteger(1, md5Bytes);
            // 转换为16进制
            return bigInt.toString(16).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
            return "null";
        }
    }

    /**
     * 获取字符串的哈希
     *
     * @param str 字符串内容
     * @return 字符串的哈希
     */
    public static String strMD5HashCode(String str)  {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return "null";
        }
    }
}
