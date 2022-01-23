package com.swith.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseDataResponse<T> {
    private Boolean isSuccess;
    private Integer code;
    private String message;
    private T data;
}
