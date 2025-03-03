package com.blog.server.entity.bo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoteSearchBO {

    private String title;

    private String categories;

    private String tagsLab;

    private int top;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String status;

}
