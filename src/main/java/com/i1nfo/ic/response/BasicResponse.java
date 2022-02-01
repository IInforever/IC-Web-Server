/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.ic.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BasicResponse {
    String msg;
    Object data;
}
