package com.blog.server.controller;

import com.blog.server.aspect.ApiOperationLog;
import com.blog.server.entity.Category;
import com.blog.server.entity.Result;
import com.blog.server.entity.vo.CategoryVO;
import com.blog.server.exception.CommonException;
import com.blog.server.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "分类接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperationLog(description = "返回所有分类")
    @Operation(summary = "返回所有分类")
    @GetMapping("/public/category")
    public Result<List<CategoryVO>> getCategoryList(){
        return Result.success(categoryService.getCategoryList());
    }

    @ApiOperationLog(description = "添加分类")
    @Operation(summary ="添加分类")
    @PostMapping("/protected/category")
    public Result<Null> addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }

    @ApiOperationLog(description = "删除分类")
    @Operation(summary ="删除分类")
    @DeleteMapping("/protected/category")
    public Result<Null> deleteCategory(@RequestBody List<Integer> categories){
        return categoryService.deleteCategory(categories);
    }

    @ApiOperationLog(description = "根据id修改分类")
    @Operation(summary ="根据id修改分类")
    @PostMapping("/protected/category/{id}")
    public Result<Void> updateCategory(@PathVariable Integer id,@Valid @RequestBody Category category){
        if (id == null){
            throw new CommonException(500, "操作失败");
        }
        categoryService.updateCategory(id, category);
        return Result.success();
    }
}
