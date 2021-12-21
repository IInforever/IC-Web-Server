/*
 * Copyright (c) IInfo 2021.
 */

package com.i1nfo.icb.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.i1nfo.icb.validate.UserLogin;
import com.i1nfo.icb.validate.UserRegister;
import com.i1nfo.icb.validate.UserUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("users")
public class User {

    @TableId(type = IdType.AUTO)
    @Null
    private Long id;

    @Size(min = 4, max = 15, groups = {UserRegister.class, UserLogin.class, UserUpdate.class})
    @NotBlank(groups = {UserRegister.class, UserLogin.class})
    private String name;

    @Email(groups = {UserRegister.class, UserUpdate.class})
    @NotBlank(groups = {UserRegister.class})
    private String email;

    @Size(min = 4, max = 40, groups = {UserRegister.class, UserLogin.class, UserUpdate.class})
    @NotBlank(groups = {UserRegister.class, UserLogin.class})
    private String passwd;

    @Null
    private Date lastLoginTime;

    public boolean isEmpty() {
        return id == null && name == null && email == null && passwd == null && lastLoginTime == null;
    }

}
