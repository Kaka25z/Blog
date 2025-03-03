package com.blog.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.server.entity.Result;
import com.blog.server.entity.Talk;
import com.blog.server.service.TalkService;
import com.blog.server.mapper.TalkMapper;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author root
* @description 针对表【talks(说说表)】的数据库操作Service实现
* @createDate 2024-04-23 07:01:40
*/
@Service
public class TalkServiceImpl extends ServiceImpl<TalkMapper, Talk>
    implements TalkService{

    @Autowired
    private TalkMapper talkMapper;

    @Override
    public Result<Null> addTalk(Talk talk) {
        talkMapper.addTalk(talk);
        return Result.success();
    }

    @Override
    public Result<List<Talk>> getTalkList() {
        List<Talk> talks = talkMapper.getTalkList();
        return new Result<>(200, talks, "请求成功");
    }

    @Override
    public Result<Null> delTalk(int id) {
        talkMapper.delTalk(id);
        return Result.success();
    }

    @Override
    public Result<Null> updateTalk(Integer id, Talk talk) {
        updateTalkDao(id, talk);
        return Result.success();
    }

    private void updateTalkDao(Integer id,Talk talk){
        talkMapper.updateTalk(id, talk);
    }
}




