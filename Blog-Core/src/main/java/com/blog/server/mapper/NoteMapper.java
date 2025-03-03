package com.blog.server.mapper;
import com.blog.server.entity.bo.NoteSearchBO;
import com.blog.server.entity.vo.NoteVO;
import com.blog.server.entity.vo.PairVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.blog.server.entity.Note;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
* @author root
* @description 针对表【notes(文章表)】的数据库操作Mapper
* @createDate 2024-04-23 08:34:03
* @Entity com.blog.server.entity.Note
*/
@Mapper
public interface NoteMapper extends BaseMapper<Note> {

    @Insert("insert notes (note_title,note_content,cover,description,note_category,note_tags,is_top,status,update_time) " +
            "values (#{noteTitle},#{noteContent},#{cover},#{description},#{noteCategory},#{noteTags},#{isTop},#{status},#{updateTime})")
    void addNote(Note note);

    @Select("select * from notes")
    List<Note> getNoteList();


    void deleteNote(List<Integer> notesList);

    void updateNote(Integer id, Note note);

    @Select("select * from notes where note_key = #{id}")
    Note getNoteById(String id);

    @Select("SELECT * FROM notes WHERE is_top = 0 ORDER BY update_time DESC LIMIT #{start}, #{pageSize}")
    List<Note> getNotePages(Integer start, Integer pageSize);

    List<NoteVO> searchNote(@Param("bo") NoteSearchBO bo);

    @Select("select * from notes where is_top = 1")
    List<Note> getTopNoteList();

    List<PairVO<Integer, Integer>> getNoteCountByCategoryKey(@Param("categoryKeyList") List<Integer> categoryKeyList);

}




