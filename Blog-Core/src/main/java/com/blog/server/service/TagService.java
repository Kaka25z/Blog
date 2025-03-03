package com.blog.server.service;

import com.blog.server.entity.Result;
import com.blog.server.entity.TagLevelOne;
import org.apache.ibatis.jdbc.Null;

import java.util.List;

public interface TagService {

    public Result<List<TagLevelOne>> getTagsOne();

    public Result<Null> addTagOne(TagLevelOne tagLevelOne);

    public Result<Null> deleteTags(List<Integer> tags);
}
