package com.blog.server.controller;

import com.blog.server.aspect.ApiOperationLog;
import com.blog.server.entity.Note;
import com.blog.server.entity.Result;
import com.blog.server.entity.bo.NoteSearchBO;
import com.blog.server.entity.vo.NoteVO;
import com.blog.server.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @ApiOperationLog(description = "添加文章接口")
    @Operation(summary = "添加文章")
    @PostMapping("/protected/notes")
    public Result<Void> addNote(@RequestBody Note note) {
        noteService.addNote(note);
        return Result.success();
    }

    @ApiOperationLog(description = "获取所有文章")
    @Operation(summary = "获取所有文章")
    @GetMapping("/public/notes")
    public Result<List<Note>> getNoteList() {
        return Result.success(noteService.getNoteList());
    }

    @ApiOperationLog(description = "获取加精文章")
    @Operation(summary = "获取加精文章")
    @GetMapping("/public/topnotes")
    public Result<List<Note>> getTopNoteList() {
        return noteService.getTopNoteList();
    }

    @ApiOperationLog(description = "根据id获取文章")
    @Operation(summary = "根据id获取文章")
    @GetMapping("/public/notes/{id}")
    public Result<Note> getNoteById(@PathVariable String id) {
        return noteService.getNoteById(id);
    }

    @ApiOperationLog(description = "分页获取文章")
    @Operation(summary = "分页获取文章")
    @GetMapping("/public/notes/page")
    public Result<List<Note>> getNotePages(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "4") Integer pageSize) {
        return noteService.getNotePages(page, pageSize);
    }

    @ApiOperationLog(description = "删除文章")
    @Operation(summary = "删除文章")
    @DeleteMapping("/protected/notes")
    public Result<Null> deleteNote(@RequestBody List<Integer> notes) {
        return noteService.deleteNote(notes);
    }

    @ApiOperationLog(description = "修改文章")
    @Operation(summary = "修改文章")
    @PostMapping("/protected/notes/{id}")
    public Result<Null> updateNote(@PathVariable Integer id,@RequestBody Note note) {
        return noteService.updateNote(id,note);
    }

    @ApiOperationLog(description = "搜索文章")
    @Operation(summary = "搜索文章")
    @PostMapping("/public/notes/search")
    public Result<List<NoteVO>> searchNote(@RequestBody NoteSearchBO query) {
        List<NoteVO> noteVOS = noteService.searchNote(query);
        return Result.success(noteVOS);
    }

}
