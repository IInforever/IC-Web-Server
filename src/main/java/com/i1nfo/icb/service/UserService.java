/*
 * Copyright (c) IInfo 2021.
 */

package com.i1nfo.icb.service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
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
        HashMap<SFunction<User, ?>, Object> params = new HashMap<>();
        params.put(User::getName, name);
        params.put(User::getPasswd, SecurityUtils.calcMD5(pass));
        User user = lambdaQuery().select(User::getId).allEq(params).one();
        if (user == null)
            return null;
        updateLoginTime(user.getId());
        return user.getId();
    }

    public void updateLoginTime(Long id) {
        if (!lambdaUpdate().eq(User::getId, id).setSql("last_login_time = current_timestamp").update())
            log.warn(String.format("Update user %d last login time fail", id));
    }

    public User getBasicInfoByID(Long id) {
        return lambdaQuery()
                .select(
                        User::getId,
                        User::getName,
                        User::getEmail,
                        User::getLastLoginTime)
                .eq(User::getId, id)
                .one();
    }

}
