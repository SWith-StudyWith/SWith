package com.swith.api.dto.study.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemoUpdateReq {
    private String content;
    private Long color;
    private String transform;
}
