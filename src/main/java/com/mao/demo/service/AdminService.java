package com.mao.demo.service;

import com.mao.demo.entity.Copyright;
import com.mao.demo.entity.User;

import java.util.List;

/**
 * @author mhh
 * @date: 2021/6/28
 * @Description: 管理员业务接口
 */

public interface AdminService {

    List<User> getUsers() throws Exception;
    List<Copyright> getCopyrights() throws Exception;
    List<Copyright> queryCopyrights(int params) throws Exception;

}
