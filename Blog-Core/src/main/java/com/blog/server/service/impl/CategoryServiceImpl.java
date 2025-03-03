package com.blog.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.server.entity.Category;
import com.blog.server.entity.Result;
import com.blog.server.entity.vo.CategoryVO;
import com.blog.server.entity.vo.PairVO;
import com.blog.server.service.CategoryService;
import com.blog.server.mapper.CategoryMapper;
import com.blog.server.service.NoteService;
import jakarta.annotation.Resource;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author root
* @description 针对表【categories(分类表)】的数据库操作Service实现
* @createDate 2024-04-23 08:25:11
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Autowired
    private CategoryMapper categoryMapper;

    @Resource
    private NoteService noteService;

    @Override
    public List<CategoryVO> getCategoryList() {

        List<Category> categories = categoryMapper.getCategories();
        if (categories.isEmpty()) {
            return Collections.emptyList();
        }

        ArrayList<CategoryVO> list = new ArrayList<>();

        List<Integer> categoryKeyList = categories.stream().map(Category::getCategoryKey).collect(Collectors.toList());

        List<PairVO<Integer,Integer>> pairList =  noteService.getNoteCountByCategoryKey(categoryKeyList);

        for (Category category : categories) {
            CategoryVO categoryVO = new CategoryVO(category);
            for (PairVO<Integer, Integer> pair : pairList) {
                if (categoryVO.getCategoryKey().equals(pair.getPairKey())) {
                    categoryVO.setNoteCount(((Number)pair.getPairValue()).intValue());
                    break;
                }
            }
            list.add(categoryVO);
        }
        return list;
    }

    @Override
    public Result<Null> addCategory(Category category) {
        categoryMapper.addCategory(category);
        return Result.success();
    }

    @Override
    public Result<Null> deleteCategory(List<Integer> categories) {
        categoryMapper.deleteCategory(categories);
        return Result.success();
    }

    @Override
    public void updateCategory(Integer id, Category category) {
        categoryMapper.updateCategory(id, category);
    }
}




