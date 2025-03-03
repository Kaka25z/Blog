package com.blog.server.service;

import com.blog.server.entity.Result;
import com.blog.server.entity.Talk;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.jdbc.Null;

import java.util.List;

/**
* @author root
* @description 针对表【talks(说说表)】的数据库操作Service
* @createDate 2024-04-23 07:01:40
*/
public interface TalkService extends IService<Talk> {

    public Result<Null> addTalk(Talk talk);

    public Result<List<Talk>> getTalkList();

    public Result<Null> delTalk(int id);

    public Result<Null> updateTalk(Integer id,Talk talk);

}
