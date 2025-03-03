package com.blog.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("tag_level_1")
public class TagLevelOne {

    private int tagKey;

    private String title;

    private int level;

    private String color;

}
