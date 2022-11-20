package com.mao.demo.service;

import com.mao.demo.entity.Copyright;
import com.mao.demo.entity.User;

import java.util.List;

public interface AdminService {

    List<User> getUsers() throws Exception;

    List<Copyright> getCopyrights() throws Exception;

    List<Copyright> queryCopyrights(int params) throws Exception;

}
