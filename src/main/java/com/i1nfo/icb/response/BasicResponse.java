package com.i1nfo.icb.response;

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
