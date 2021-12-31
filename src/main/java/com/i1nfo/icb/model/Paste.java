/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.icb.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.i1nfo.icb.validate.AnonymousPaste;
import com.i1nfo.icb.validate.PasteValidate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("pastes")
public class Paste {

    @Null
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Null
    private Long uid;

    @Size(min = 1, max = 15, groups = {AnonymousPaste.class, PasteValidate.class})
    private String title;

    @Null(groups = AnonymousPaste.class)
    @NotNull(groups = PasteValidate.class)
    @TableField("private")
    private Boolean isPrivate;

    @TableField(exist = false)
    @NotNull(groups = {AnonymousPaste.class, PasteValidate.class})
    @Min(value = 600, groups = {AnonymousPaste.class, PasteValidate.class})
    @Max(value = 86400, groups = {AnonymousPaste.class, PasteValidate.class})
    private Integer expireDuration;

    @Null
    private Date expireTime;

    @Size(min = 1, max = 30, groups = {AnonymousPaste.class, PasteValidate.class})
    private String passwd;

    @NotBlank
    private String paste;

}
