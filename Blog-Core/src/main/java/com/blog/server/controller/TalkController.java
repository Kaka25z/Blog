package com.blog.server.controller;

import com.blog.server.aspect.ApiOperationLog;
import com.blog.server.entity.Result;
import com.blog.server.entity.Talk;
import com.blog.server.service.TalkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "说说接口")
public class TalkController {

    @Autowired
    private TalkService talkService;

    @ApiOperationLog(description = "添加说说")
    @Operation(summary = "添加说说")
    @PostMapping("/protect/talk")
    public Result<Null> addComment(@RequestBody Talk talk){
        System.out.println(talk.toString());
        return talkService.addTalk(talk);
    }

    @ApiOperationLog(description = "获取说说")
    @Operation(summary = "获取说说")
    @GetMapping("/public/talk")
    public Result<List<Talk>> getTalkList(){
        return talkService.getTalkList();
    }

    @ApiOperationLog(description = "删除说说")
    @Operation(summary = "删除说说")
    @DeleteMapping("/protect/talk/{id}")
    public Result<Null> delTalk(@PathVariable Integer id){
        return talkService.delTalk(id);
    }

    @ApiOperationLog(description = "修改说说")
    @Operation(summary = "修改说说")
    @PostMapping("/protect/talk/{id}")
    public Result<Null> updateTalk(@PathVariable Integer id, @RequestBody Talk talk){
        System.out.println(id + "  " + talk.toString());
        return talkService.updateTalk(id, talk);
    }
}
