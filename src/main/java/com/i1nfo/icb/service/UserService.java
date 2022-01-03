/*
 * Copyright (c) IInfo 2022.
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

    public boolean create(@NotNull User user) {
        user.setPasswd(SecurityUtils.calcMD5(user.getPasswd()));
        return save(user);
    }

    public Long getIDByNameAndPasswd(String name, String pass) {
        HashMap<SFunction<User, ?>, Object> params = new HashMap<>();
        params.put(User::getName, name);
        params.put(User::getPasswd, SecurityUtils.calcMD5(pass));
        User user = lambdaQuery()
                .select(User::getId)
                .allEq(params)
                .one();
        if (user == null)
            return null;
        return user.getId();
    }

    public void updateLoginTime(Long id) {
        if (!lambdaUpdate()
                .eq(User::getId, id)
                .setSql("last_login_time = current_timestamp")
                .update())
            log.warn(String.format("Update user %d last login time fail", id));
    }

    public User getBasicInfoByID(Long id) {
        return lambdaQuery()
                .select(User::getId,
                        User::getName,
                        User::getEmail,
                        User::getCreateTime)
                .eq(User::getId, id)
                .one();
    }

    public boolean updateById(Long id, @NotNull User entity) {
        entity.setPasswd(SecurityUtils.calcMD5(entity.getPasswd()));
        entity.setId(id);
        return updateById(entity);
    }


}
