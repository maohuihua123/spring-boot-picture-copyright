package com.mao.demo.service.impl;

import com.mao.demo.common.utils.WalletUtils;
import com.mao.demo.dao.PictureCopyright;
import com.mao.demo.entity.Copyright;
import com.mao.demo.entity.User;
import com.mao.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple6;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mhh
 * @date: 2021/6/28
 * @Description: 业务实现类
 */
@Service
public class AdminServiceImpl  implements AdminService {

    private Web3j web3j;

    private PictureCopyright contract;

    @Autowired
    public void setContract(PictureCopyright contract) {
        this.contract = contract;
    }

    @Autowired
    public void setWeb3j(Web3j web3j) {
        this.web3j = web3j;
    }

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
