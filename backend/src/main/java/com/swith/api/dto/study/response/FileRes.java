package com.swith.api.dto.study.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class FileRes {
    private Long fileId;
    private String originName;
    private String fileUrl;
    private Long fileSize;
}
