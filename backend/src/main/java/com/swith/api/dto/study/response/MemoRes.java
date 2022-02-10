package com.swith.api.dto.study.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemoRes {
    private String content;
    private Long color;
    private String transform;
}
