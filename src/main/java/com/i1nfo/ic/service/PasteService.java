/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.ic.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.i1nfo.ic.mapper.PasteMapper;
import com.i1nfo.ic.model.Paste;
import com.i1nfo.ic.utils.SecurityUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PasteService extends ServiceImpl<PasteMapper, Paste> {

    public Long create(@NotNull Paste paste) {
        paste.setExpireTime(new Date(System.currentTimeMillis() + paste.getExpireDuration() * 1000));
        paste.setPasswd(SecurityUtils.calcMD5(paste.getPasswd()));
        return save(paste) ? paste.getId() : null;
    }

    public List<Paste> getByUid(Long uid) {
        QueryWrapper<Paste> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(
                "id",
                "title",
                "is_private",
                "expire_time",
                "create_time",
                "type",
                "!ISNULL(passwd) AS hasPasswd"
        );
        queryWrapper.lambda()
                .eq(Paste::getUid, uid)
                .ge(Paste::getExpireTime, new Date());
        return baseMapper.selectList(queryWrapper);
    }

    public IPage<Paste> getALl(IPage<Paste> page) {
        return baseMapper.selectPage(page, null);
    }

    public int updateById(Long id, @NotNull Paste entity, Long uid) {
        if (entity.getExpireDuration() != null)
            entity.setExpireTime(new Date(System.currentTimeMillis() + entity.getExpireDuration() * 1000));
        entity.setPasswd(SecurityUtils.calcMD5(entity.getPasswd()));
        LambdaUpdateWrapper<Paste> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(Paste::getId, id)
                .eq(Paste::getUid, uid)
                .ge(Paste::getExpireTime, new Date());
        return baseMapper.update(entity, updateWrapper);
    }

    public boolean updateAll(Long id, @NotNull Paste entity, Long uid) {
        entity.setExpireTime(new Date(System.currentTimeMillis() + entity.getExpireDuration() * 1000));
        entity.setPasswd(SecurityUtils.calcMD5(entity.getPasswd()));
        return lambdaUpdate()
                .set(Paste::getTitle, entity.getTitle())
                .set(Paste::getContent, entity.getContent())
                .set(Paste::getPasswd, entity.getPasswd())
                .set(Paste::getExpireTime, entity.getExpireTime())
                .set(Paste::getIsPrivate, entity.getIsPrivate())
                .set(Paste::getType, entity.getType())
                .eq(Paste::getId, id)
                .eq(Paste::getUid, uid)
                .ge(Paste::getExpireTime, new Date())
                .update();
    }

    public boolean removeById(Long id, Long uid) {
        LambdaQueryWrapper<Paste> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Paste::getId, id)
                .eq(Paste::getUid, uid);
        return baseMapper.delete(queryWrapper) != 0;
    }

    public int cleanUpExpiredPaste() {
        LambdaQueryWrapper<Paste> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.lt(Paste::getExpireTime, new Date());
        return baseMapper.delete(queryWrapper);
    }

}
