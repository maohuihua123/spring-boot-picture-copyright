package com.mao.demo.service;

import com.mao.demo.entity.Copyright;
import com.mao.demo.entity.User;

import java.util.List;

/**
 * @author mhh
 * @description 用户业务接口
 */
public interface UserService {

    void addUser(User user, String privateKey) throws Exception;
    User getUserInfo(String privateKey) throws Exception;
    List<Copyright> getUserCopyrights(String privateKey) throws Exception;
    String addCopyright(Copyright copyright, String privateKey) throws Exception;
}


