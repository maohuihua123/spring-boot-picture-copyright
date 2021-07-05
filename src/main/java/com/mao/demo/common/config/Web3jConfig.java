package com.mao.demo.common.config;

import com.mao.demo.dao.PictureCopyright;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

@Slf4j
@Configuration
public class Web3jConfig {

    @Value("${web3j.url}")
    private String url;

    @Value("${web3j.credentials.privatekey}")
    private String privatekey;

    @Value("${web3j.contract.address}")
    private String address;

    @Bean
    public Web3j web3j() {
        log.info("加载web3j====>：" + url);
        return Web3j.build(new HttpService(url));
    }

    @Bean
    public Credentials credentials() {
        log.info("加载credentials====>：" + privatekey);
        return Credentials.create(privatekey);
    }

    @Bean
    public PictureCopyright pictureCopyright() {
        log.info("加载pictureCopyright====>：" + address);
        return PictureCopyright.load(address, web3j(), credentials(), new DefaultGasProvider());
    }
}
