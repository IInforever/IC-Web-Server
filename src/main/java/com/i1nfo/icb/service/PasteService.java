/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.icb.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.i1nfo.icb.mapper.PasteMapper;
import com.i1nfo.icb.model.Paste;
import com.i1nfo.icb.utils.SecurityUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PasteService extends ServiceImpl<PasteMapper, Paste> {

    public Long create(@NotNull Paste paste) {
        paste.setExpireTime(new Date(System.currentTimeMillis() + paste.getExpireDuration() * 1000));
        if (paste.getPasswd() != null)
            paste.setPasswd(SecurityUtils.calcMD5(paste.getPasswd()));
        return save(paste) ? paste.getId() : null;
    }

}
