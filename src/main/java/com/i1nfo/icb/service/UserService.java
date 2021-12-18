/*
 * Copyright (c)  IInfo 2021.
 */

package com.i1nfo.icb.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.i1nfo.icb.mapper.UserMapper;
import com.i1nfo.icb.model.User;
import com.i1nfo.icb.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    public Long createUser(User user) {
        return null;
    }

    public Long getIdByNameAndPass(String name, String pass) throws NoSuchAlgorithmException {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        HashMap<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("passwd", SecurityUtils.calcMD5(pass));
        User user = baseMapper.selectOne(wrapper.select("id").allEq(params));
        if (user == null)
            return 0L;
        else
            return user.getId();
    }


}
