package com.mao.demo;

import com.mao.demo.contract.PictureCopyright;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.DefaultGasProvider;

@SpringBootTest
public class ContractDeployTest {

    @Autowired
    private Web3j web3j;

    @Autowired
    private Credentials credentials;

    @Test
    void version() throws Exception {
        String version = web3j.web3ClientVersion().send().getWeb3ClientVersion();
        System.out.println(version);
    }

    @Test
    void deploy() throws Exception {
        PictureCopyright contract = PictureCopyright.deploy(web3j, credentials,new DefaultGasProvider()).send();
        // 将合约地址写入到配置文件 contractAddress ----> application.yml
        System.out.println(contract.getContractAddress());
    }
}
