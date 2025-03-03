package com.blog.server.entity.vo;

import com.blog.server.entity.Note;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteVO {

    private int noteKey;

    private String noteTitle;

    private String noteContent;

    private String description;

    private String cover;

    private int noteCategory;

    private String noteCategoryName;

    private String noteTags;

    private int isTop;

    private String status ;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;

    public NoteVO(Note note){
        this.noteKey = note.getNoteKey();
        this.noteTitle = note.getNoteTitle();
        this.noteContent = note.getNoteContent();
        this.description = note.getDescription();
        this.cover = note.getCover();
        this.noteCategory = note.getNoteCategory();
        this.noteTags = note.getNoteTags();
        this.isTop = note.getIsTop();
        this.status = note.getStatus();
        this.createTime = note.getCreateTime();
        this.updateTime = note.getUpdateTime();
    }

    public NoteVO(Note note,String categoryName) {
        this(note);
        this.noteCategoryName = categoryName;
    }
}

