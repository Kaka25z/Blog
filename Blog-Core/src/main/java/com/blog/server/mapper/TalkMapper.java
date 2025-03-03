package com.blog.server.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.blog.server.entity.Talk;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author root
* @description 针对表【talks(说说表)】的数据库操作Mapper
* @createDate 2024-04-23 07:01:40
* @Entity com.blog.server.entity.Talk
*/
@Mapper
public interface TalkMapper extends BaseMapper<Talk> {

    void addTalk(Talk talk);

    List<Talk> getTalkList();

    void delTalk(@Param("id") int id);

    void updateTalk(Integer id, Talk talk);

}




