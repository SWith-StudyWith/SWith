package com.swith.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseResponse {
    private Boolean isSuccess;
    private Integer code;
    private String message;
}
