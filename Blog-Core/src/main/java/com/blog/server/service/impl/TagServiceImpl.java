package com.blog.server.service.impl;


import com.blog.server.entity.Result;
import com.blog.server.entity.TagLevelOne;
import com.blog.server.mapper.TagMapper;
import com.blog.server.service.TagService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public Result<List<TagLevelOne>> getTagsOne() {
        List<TagLevelOne> result = tagMapper.getTagsOne();
        return Result.success(result);
    }

    @Override
    public Result<Null> addTagOne(TagLevelOne tagLevelOne) {
        tagMapper.addTagOne(tagLevelOne);
        return Result.success();
    }

    @Override
    public Result<Null> deleteTags(List<Integer> tags) {
        for (Integer tag : tags){
            tagMapper.deleteTagOne(tag);
        }

        return Result.success();
    }
}
