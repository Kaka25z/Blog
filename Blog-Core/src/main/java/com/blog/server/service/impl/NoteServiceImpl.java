package com.blog.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.server.entity.Note;
import com.blog.server.entity.Result;
import com.blog.server.entity.bo.NoteSearchBO;
import com.blog.server.entity.vo.NoteVO;
import com.blog.server.entity.vo.PairVO;
import com.blog.server.service.NoteService;
import com.blog.server.mapper.NoteMapper;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
* @author root
* @description 针对表【notes(文章表)】的数据库操作Service实现
* @createDate 2024-04-23 08:34:03
*/
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note>
    implements NoteService{

    @Autowired
    private NoteMapper noteMapper;

    @Override
    public void addNote(Note note) {
        noteMapper.addNote(note);
    }

    @Override
    public List<Note> getNoteList() {
        return noteMapper.getNoteList();
    }

    @Override
    public Result<Null> updateNote(Integer id, Note note) {
        noteMapper.updateNote(id, note);
        return Result.success();
    }

    @Override
    public Result<Null> deleteNote(List<Integer> notes) {
        noteMapper.deleteNote(notes);
        return Result.success();
    }

    @Override
    public Result<Note> getNoteById(String id) {
        Note note = noteMapper.getNoteById(id);
        return Result.success(note);
    }

    @Override
    public Result<List<Note>> getNotePages(Integer page, Integer pageSize) {
        int start = (page - 1) * pageSize;
        List<Note> noteList = noteMapper.getNotePages(start, pageSize);
        return Result.success(noteList);
    }

    @Override
    public List<NoteVO> searchNote(NoteSearchBO bo) {
        return noteMapper.searchNote(bo);
    }

    @Override
    public Result<List<Note>> getTopNoteList() {
        List<Note> noteList = noteMapper.getTopNoteList();
        return Result.success(noteList);
    }

    @Override
    public List<PairVO<Integer, Integer>> getNoteCountByCategoryKey(List<Integer> categoryKeyList) {
        if (categoryKeyList.isEmpty()){
            return Collections.emptyList();
        }

        return noteMapper.getNoteCountByCategoryKey(categoryKeyList);
    }
}




