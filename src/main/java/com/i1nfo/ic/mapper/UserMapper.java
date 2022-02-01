/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.ic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.i1nfo.ic.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
