/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.ic.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {

    Integer code;

    String msg;

    Object error;
}
