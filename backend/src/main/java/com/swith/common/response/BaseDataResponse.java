package com.swith.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseDataResponse<T> {
    private Boolean isSuccess;
    private Integer statusCode;
    private String message;
    private T data;
}