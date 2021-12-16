package com.i1nfo.icb.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.i1nfo.icb.validate.UserLogin;
import com.i1nfo.icb.validate.UserRegister;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("users")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;

    @NotEmpty(groups = {UserLogin.class})
    private String name;

    @Email(groups = {UserRegister.class})
    private String email;

    @Size(min = 6, max = 40, groups = {UserLogin.class})
    private String passwd;
}
