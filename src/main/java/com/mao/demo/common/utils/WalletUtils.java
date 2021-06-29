package com.mao.demo.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.web3j.crypto.*;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

@Slf4j
public class WalletUtils {
    //钱包临时文件
    // 临时文件前缀
    private static final String PREFIX = "key";
    // 临时文件后缀
    private static final String SUFFIX = ".tmp";

    /**
     * 临时文件
     *
     * @param content 钱包内容
     * @return 临时文件
     */
    public static File StoreTmpFile(byte[] content) {
        File tmp = null;
        try {
            tmp = File.createTempFile(PREFIX, SUFFIX);
            tmp.deleteOnExit();
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(tmp));
            out.write(content);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmp;
    }

    /**
     * 转账 单位默认为ETH
     *
     * @param web3j  web3j
     * @param credentials  fromAddress的账户凭证
     * @param toAddress toAddress 接收地址
     * @param value 转账金额
     */
    public static void transferTo(Web3j web3j, Credentials credentials, String toAddress, Double value) {
        if (web3j == null) return;
        if (credentials == null) return;
        TransactionReceipt send = null;
        try {
            send = Transfer.sendFunds(web3j, credentials, toAddress, BigDecimal.valueOf(value), Convert.Unit.ETHER).send();
            log.info("<===== 转账交易完成 =====>");
            log.info("trans hash=" + send.getTransactionHash());
            log.info("from :" + send.getFrom());
            log.info("to:" + send.getTo());
            log.info("gas used=" + send.getGasUsed());
            log.info("status: " + send.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param web3j web3j
     * @param address 账户地址
     * @return 账户余额 ETHER
     */
    public static String getBlanceOf(Web3j web3j, String address) {
        // 第二个参数：区块的参数，建议选最新区块
        EthGetBalance balance = null;
        String blanceETH = null;
        try {
            balance = web3j.ethGetBalance(address, DefaultBlockParameter.valueOf("latest")).send();
            // 格式转化 wei-ether
            blanceETH = Convert.fromWei(balance.getBalance().toString(), Convert.Unit.ETHER).toPlainString();
            // 输出账户余额
            // log.info("address："+address+"   balance："+blanceETH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return blanceETH;
    }


    /**
     * 解密keystore 得到私钥
     *
     * @param keystore 钱包文件路径
     * @param password 钱包密码
     */
    public static String decryptWallet(String keystore, String password) {
        String privateKey = null;
        ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
        try {
            WalletFile walletFile = objectMapper.readValue(keystore, WalletFile.class);
            ECKeyPair ecKeyPair = null;
            ecKeyPair = Wallet.decrypt(password, walletFile);
            privateKey = ecKeyPair.getPrivateKey().toString(16);
            // System.out.println(privateKey);
        } catch (CipherException e) {
            if ("Invalid password provided".equals(e.getMessage())) {
                log.error("密码错误");
            }
        } catch (IOException e) {
            log.error("读取钱包失败",e);
        }
        return privateKey;
    }

    /**
     * 创建钱包
     *
     * @param password 密码
     */
    public static String createWallet(String password)  {
        String jsonStr = null;
        try {
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            WalletFile walletFile = Wallet.createStandard(password, ecKeyPair);
            // System.out.println("address " + walletFile.getAddress());
            ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
            jsonStr = objectMapper.writeValueAsString(walletFile);
            // System.out.println("keystore json file " + jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

    public static void main(String[] args) throws IOException {
        String s = "A\rB\nC\r\nD";
        //封装ByteArrayInputStream-->InputStreamReader-->BufferedReader
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
        InputStreamReader streamReader = new InputStreamReader(arrayInputStream, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(streamReader);
        String line;
        while ( (line = br.readLine()) != null ) {
            System.out.println(line);
        }
    }
}
