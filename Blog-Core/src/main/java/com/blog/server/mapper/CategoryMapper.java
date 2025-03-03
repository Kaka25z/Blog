package com.blog.server.mapper;
import java.util.List;

import com.blog.server.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author root
* @description 针对表【categories(分类表)】的数据库操作Mapper
* @createDate 2024-04-23 08:25:11
* @Entity com.blog.server.entity.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    List<Category> getCategories();

    void addCategory(Category category);

    void deleteCategory(List<Integer> categories);

    void updateCategory(Integer id, Category category);
}




