/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.icb.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.i1nfo.icb.validate.UserLoginValidate;
import com.i1nfo.icb.validate.UserRegisterValidate;
import com.i1nfo.icb.validate.UserUpdateValidate;
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

    @Size(min = 1, max = 15, groups = {UserRegisterValidate.class, UserLoginValidate.class, UserUpdateValidate.class})
    @NotBlank(groups = {UserRegisterValidate.class, UserLoginValidate.class})
    private String name;

    @Email(groups = {UserRegisterValidate.class, UserUpdateValidate.class})
    @NotBlank(groups = {UserRegisterValidate.class})
    private String email;

    @Size(min = 4, max = 40, groups = {UserRegisterValidate.class, UserLoginValidate.class, UserUpdateValidate.class})
    @NotBlank(groups = {UserRegisterValidate.class, UserLoginValidate.class})
    private String passwd;

    @Null
    private Date lastLoginTime;

    @Null
    private Date createTime;

    @JsonIgnore
    public boolean isEmpty() {
        return id == null && name == null && email == null && passwd == null && lastLoginTime == null;
    }

}
