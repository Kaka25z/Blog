package com.blog.server.mapper;

import com.blog.server.entity.TagLevelOne;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TagMapper {

    @Select("select * from tag_level_1")
    List<TagLevelOne> getTagsOne();

    @Insert("insert into tag_level_1 (title,color) values (#{title},#{color})")
    void addTagOne(TagLevelOne tagLevelOne);

    @Delete("delete from tag_level_1 where tag_key = #{tag}")
    void deleteTagOne(Integer tag);

}
