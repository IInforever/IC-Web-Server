/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.icb.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.i1nfo.icb.validate.AnonymousPasteCreateValidate;
import com.i1nfo.icb.validate.PasteCreateValidate;
import com.i1nfo.icb.validate.PasteUpdateValidate;
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
    @TableId(type = IdType.ASSIGN_ID) // Using custom id generator
    private Long id;

    @Null
    private Long uid;

    @Size(min = 1, max = 20, groups = {
            AnonymousPasteCreateValidate.class,
            PasteCreateValidate.class,
            PasteUpdateValidate.class
    })
    private String title;

    @Null(groups = AnonymousPasteCreateValidate.class)
    @NotNull(groups = PasteCreateValidate.class)
    private Boolean isPrivate;

    @TableField(exist = false)
    @NotNull(groups = {AnonymousPasteCreateValidate.class, PasteCreateValidate.class})
    @Min(value = 600, groups = {
            AnonymousPasteCreateValidate.class,
            PasteCreateValidate.class,
            PasteUpdateValidate.class
    })
    @Max(value = 86400, groups = {
            AnonymousPasteCreateValidate.class,
            PasteCreateValidate.class,
            PasteUpdateValidate.class
    })
    private Integer expireDuration;

    @Null
    private Date expireTime;

    @Null
    private Date createTime;

    @Size(min = 4, max = 32, groups = {
            AnonymousPasteCreateValidate.class,
            PasteCreateValidate.class,
            PasteUpdateValidate.class
    })
    private String passwd;

    @NotBlank(groups = {
            AnonymousPasteCreateValidate.class,
            PasteCreateValidate.class
    })
    @Size(min = 1, max = 5000, groups = {
            AnonymousPasteCreateValidate.class,
            PasteCreateValidate.class,
            PasteUpdateValidate.class
    })
    private String paste;

    @NotBlank(groups = {
            AnonymousPasteCreateValidate.class,
            PasteCreateValidate.class
    })
    @Size(min = 1, max = 15, groups = {
            AnonymousPasteCreateValidate.class,
            PasteCreateValidate.class,
            PasteUpdateValidate.class
    })
    private String type;

    @Null
    @TableField(exist = false)
    private Boolean hasPasswd;

    @JsonIgnore
    public boolean hasPasswd() {
        return this.hasPasswd = passwd != null;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return this.title == null
                && this.paste == null
                && this.passwd == null
                && this.expireDuration == null
                && this.isPrivate == null
                && this.type == null;
    }

}
