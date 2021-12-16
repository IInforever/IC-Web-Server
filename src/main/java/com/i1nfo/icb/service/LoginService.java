package com.i1nfo.icb.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.i1nfo.icb.mapper.UserMapper;
import com.i1nfo.icb.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

@Service
public class LoginService {

    private final UserMapper userMapper;

    public LoginService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Long getIdByNameAndPass(String name, String pass) throws NoSuchAlgorithmException {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        HashMap<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("passwd", calcMD5(pass));
        User user = userMapper.selectOne(wrapper.select("id").allEq(params));
        if (user == null)
            return 0L;
        else
            return user.getId();
    }

    protected String calcMD5(@NotNull String m) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] result = md.digest(m.getBytes(StandardCharsets.UTF_8));
        return new BigInteger(1, result).toString(16);
    }
}
