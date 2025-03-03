package com.blog.server.controller;

import com.blog.server.entity.Result;
import com.blog.server.entity.TagLevelOne;
import com.blog.server.service.TagService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/public/tagone")
    public Result<List<TagLevelOne>> getTagOne(){
        return tagService.getTagsOne();
    }

    @PostMapping("/protected/tagone")
    public Result<Null> addTagOne(@RequestBody TagLevelOne tagLevelOne){
        return tagService.addTagOne(tagLevelOne);
    }

    @DeleteMapping("/protected/tag")
    public Result<Null> deleteTag(@RequestBody List<Integer> tags){
        return tagService.deleteTags(tags);
    }
}
