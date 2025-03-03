package com.blog.server.entity.vo;

import com.blog.server.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CategoryVO {

    private Integer categoryKey;

    private String categoryTitle;

    private String pathName;

    private String introduce;

    private String icon;

    private String color;

    private Integer noteCount = 0;

    public CategoryVO(Category category) {
        this.categoryKey = category.getCategoryKey();
        this.categoryTitle = category.getCategoryTitle();
        this.pathName = category.getPathName();
        this.introduce = category.getIntroduce();
        this.icon = category.getIcon();
        this.color = category.getColor();
    }


    public CategoryVO(Category category,Integer noteCount) {
        this(category);
        this.noteCount = noteCount;
    }
}
