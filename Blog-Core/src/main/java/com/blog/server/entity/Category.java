package com.blog.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("categories")
public class Category {

    @TableId(value = "category_key", type = IdType.AUTO)
    private Integer categoryKey;

    private String categoryTitle;

    private String pathName;

    private String introduce;

    private String icon;

    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Color must start with '#' and followed by six or three hexadecimal characters.")
    private String color;

}
