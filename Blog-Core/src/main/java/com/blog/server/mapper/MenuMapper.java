package com.blog.server.mapper;

import com.blog.server.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author root
* @description 针对表【menu】的数据库操作Mapper
* @createDate 2024-04-27 14:42:05
* @Entity com.blog.server.entity.Menu
*/
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

}




