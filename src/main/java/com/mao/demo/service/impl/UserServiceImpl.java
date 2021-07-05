package com.mao.demo.service.impl;

import com.mao.demo.common.utils.WalletUtils;
import com.mao.demo.dao.PictureCopyright;
import com.mao.demo.entity.Copyright;
import com.mao.demo.entity.User;
import com.mao.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mhh
 * @description 用户业务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Value("${web3j.contract.address}")
    private String contractAddress;

    private Web3j web3j;

    private Credentials adminCredentials;

    @Autowired
    public void setAdminCredentials(Credentials adminCredentials) {
        this.adminCredentials = adminCredentials;
    }

    @Autowired
    public void setWeb3j(Web3j web3j) {
        this.web3j = web3j;
    }

    @Override
    public void addUser(User user, String privateKey) throws Exception {
        // 获取凭证
        Credentials credentials = Credentials.create(privateKey);
        // 给新用户2个代币
        WalletUtils.transferTo(web3j,adminCredentials,credentials.getAddress(),2.0);
        // 新用户调用合约进行注册 会消耗代币
        PictureCopyright contract = PictureCopyright.load(contractAddress, web3j, credentials, new DefaultGasProvider());
        TransactionReceipt send = contract.createUser(user.getNickName(), user.getEmail()).send();
    }

    @Override
    public User getUserInfo(String privateKey) throws Exception {
        // 1.创建凭证
        Credentials credentials = Credentials.create(privateKey);
        // 2.加载合约
        PictureCopyright contract = PictureCopyright.load(contractAddress, web3j, credentials, new DefaultGasProvider());
        // 3.查询用户
        BigInteger index = contract.getUserIndex().send();
        Tuple4<String, String, String, BigInteger> tuple4 = contract.users(index).send();
        User user = new User(tuple4);
        // 4.查询用户余额
        String ethBlance = WalletUtils.getBlanceOf(web3j, credentials.getAddress());
        user.setEthBlance(ethBlance);
        return user;
    }

    @Override
    public List<Copyright> getUserCopyrights(String privateKey) throws Exception {
        // 获取凭证
        Credentials credentials = Credentials.create(privateKey);
        // 加载合约
        PictureCopyright contract = PictureCopyright.load(contractAddress, web3j, credentials, new DefaultGasProvider());
        // 查询
        String ethAddress = credentials.getAddress();
        BigInteger count = contract.getUserCopyrightsNumber(ethAddress).send();
        List<Copyright> copyrightList = new ArrayList<>();
        for (int i = 0; i < count.intValue(); i++) {
            Tuple5<BigInteger, String, String, String, BigInteger> tuple5 = contract.getUserCopyright(ethAddress,BigInteger.valueOf(i)).send();
            Copyright copyright = new Copyright(tuple5);
            copyright.setId(i);
            copyright.setEthAddress(ethAddress);
            copyrightList.add(copyright);
        }
        return copyrightList;
    }

    @Override
    public String addCopyright(Copyright copyright, String privateKey) throws Exception {
        // 1.获取凭证
        Credentials credentials = Credentials.create(privateKey);
        // 2.加载合约
        PictureCopyright contract = PictureCopyright.load(contractAddress, web3j, credentials, new DefaultGasProvider());
        TransactionReceipt send = contract.setCopyright(copyright.getPictureHash(), copyright.getWaterMark(), copyright.getPicturePath()).send();
        // 3.获得Event事件的值
        List<PictureCopyright.SetCopyrightEventEventResponse> events = contract.getSetCopyrightEventEvents(send);
        PictureCopyright.SetCopyrightEventEventResponse setCopyrightEventEventResponse = events.get(0);
        String ethAddress = setCopyrightEventEventResponse.ethAddress;
        BigInteger copyrightID = setCopyrightEventEventResponse.index;
        // 4.给用户转一个代币 鼓励使用
        WalletUtils.transferTo(web3j, adminCredentials, ethAddress,1.0);
        return "userAddress :" + ethAddress;
    }
}
