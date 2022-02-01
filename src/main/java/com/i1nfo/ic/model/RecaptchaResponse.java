/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.ic.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RecaptchaResponse {

    private boolean success;

    private Date challenge_ts;

    private String hostname;

    @JsonProperty(value = "error-codes")
    private List<String> errorCodes;

}
