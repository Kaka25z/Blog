package com.blog.server.service;

import com.blog.server.entity.Note;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.server.entity.Result;
import com.blog.server.entity.bo.NoteSearchBO;
import com.blog.server.entity.vo.NoteVO;
import com.blog.server.entity.vo.PairVO;
import org.apache.ibatis.jdbc.Null;

import java.util.List;

/**
* @author root
* @description 针对表【notes(文章表)】的数据库操作Service
* @createDate 2024-04-23 08:34:03
*/
public interface NoteService extends IService<Note> {

    public void addNote(Note note);

    public List<Note> getNoteList();

    public Result<Null> updateNote(Integer id, Note note);

    public Result<Null> deleteNote(List<Integer> notes);

    public Result<Note> getNoteById(String id);

    public Result<List<Note>> getNotePages(Integer page, Integer pageSize);

    public List<NoteVO> searchNote(NoteSearchBO bo);

    public Result<List<Note>> getTopNoteList();

    public List<PairVO<Integer, Integer>> getNoteCountByCategoryKey(List<Integer> categoryKeyList);
}
