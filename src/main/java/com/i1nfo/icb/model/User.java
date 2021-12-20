/*
 * Copyright (c) IInfo 2021.
 */

package com.i1nfo.icb.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.i1nfo.icb.validate.UserLogin;
import com.i1nfo.icb.validate.UserRegister;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("users")
public class User {
    @TableId(type = IdType.AUTO)
    @Positive
    @Null(groups = {UserRegister.class, UserLogin.class})
    private Long id;

    @NotEmpty(groups = {UserRegister.class, UserLogin.class})
    private String name;

    @Email(groups = {UserRegister.class})
    @NotBlank(groups = {UserRegister.class})
    private String email;

    @Size(min = 4, max = 40, groups = {UserRegister.class, UserLogin.class})
    private String passwd;

    @PastOrPresent
    @Null(groups = {UserRegister.class, UserLogin.class})
    private Date lastLoginTime;
}
