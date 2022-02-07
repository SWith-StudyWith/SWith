package com.swith.api.dto.study.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileRes {
    private Long fileId;
    private String originName;
    private String fileUrl;
    private Long fileSize;
}
