package com.blog.server.service;

import com.blog.server.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.server.entity.Result;
import com.blog.server.entity.vo.CategoryVO;
import org.apache.ibatis.jdbc.Null;

import java.util.List;

/**
* @author root
* @description 针对表【categories(分类表)】的数据库操作Service
* @createDate 2024-04-23 08:25:11
*/
public interface CategoryService extends IService<Category> {

    public List<CategoryVO> getCategoryList();

    public Result<Null> addCategory(Category category);

    public Result<Null> deleteCategory(List<Integer> categories);

    public void updateCategory(Integer id, Category category);
}
