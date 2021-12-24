/*
 * Copyright (c) IInfo 2021.
 */

package com.i1nfo.icb.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {
    String msg;
    Integer code;
    Object error;
}
