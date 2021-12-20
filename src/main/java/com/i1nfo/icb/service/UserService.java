/*
 * Copyright (c) IInfo 2021.
 */

package com.i1nfo.icb.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.i1nfo.icb.mapper.UserMapper;
import com.i1nfo.icb.model.User;
import com.i1nfo.icb.utils.SecurityUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    public boolean register(@NotNull User user) {
        user.setPasswd(SecurityUtils.calcMD5(user.getPasswd()));
        return save(user);
    }

    public Long login(String name, String pass) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        HashMap<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("passwd", SecurityUtils.calcMD5(pass));
        User user = baseMapper.selectOne(wrapper.select("id").allEq(params));
        if (user == null)
            return 0L;
        updateLoginTime(user.getId());
        return user.getId();
    }

    public void updateLoginTime(Long id) {
        if (!lambdaUpdate().eq(User::getId, id).setSql("last_login_time = current_timestamp").update())
            log.warn(String.format("Update user %d last login time fail", id));
    }

}
