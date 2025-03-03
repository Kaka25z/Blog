package com.blog.server.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoVO {

    private String fileName;

    private String lastModifiedTime;

    private String fileSize;

    private String contentType;
}
