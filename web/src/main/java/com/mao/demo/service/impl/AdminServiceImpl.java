package com.mao.demo.service.impl;

import com.mao.demo.contract.PictureCopyright;
import com.mao.demo.entity.Copyright;
import com.mao.demo.entity.User;
import com.mao.demo.service.AdminService;
import com.mao.demo.utils.WalletUtils;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple6;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl  implements AdminService {

    @Resource
    private Web3j web3j;

    @Resource
    private PictureCopyright contract;

    @Override
    public List<User> getUsers() throws Exception {
        List<User> userList = new ArrayList<>();
        BigInteger count = contract.userNumber().send();
        for (int i = 0; i < count.intValue(); i++) {
            Tuple4<String, String, String, BigInteger> tuple = contract.users(BigInteger.valueOf(i)).send();
            User user = new User(tuple);
            user.setId(i);
            String ethBlance = WalletUtils.getBlanceOf(web3j, user.getEthAddress());
            user.setEthBlance(ethBlance);
            userList.add(user);
        }
        return userList;
    }

    @Override
    public List<Copyright> getCopyrights() throws Exception {
        List<Copyright> copyrightList = new ArrayList<>();
        BigInteger count = contract.copyrightNumber().send();
        for (int i = 0; i < count.intValue(); i++) {
            Tuple6<BigInteger, String, String, String, String, BigInteger> tuple = contract.copyrights(BigInteger.valueOf(i)).send();
            Copyright temp = new Copyright(tuple);
            temp.setId(i);
            copyrightList.add(temp);
        }
        return copyrightList;
    }

    @Override
    public List<Copyright> queryCopyrights(int params) throws Exception {
        Tuple6<BigInteger, String, String, String, String, BigInteger> tuple6 = contract.queryCopyrights(BigInteger.valueOf(params)).send();
        List<Copyright> copyrightList = new ArrayList<>();
        copyrightList.add(new Copyright(tuple6));
        return copyrightList;
    }
}
