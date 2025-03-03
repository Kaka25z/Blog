package com.blog.server.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import com.blog.server.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
* @author root
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2024-04-22 16:09:22
* @Entity com.blog.server.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    User findUser(@Param("username") String username, @Param("password") String password);

    @SuppressWarnings("MybatisXMapperMethodInspection")
    List<User> listUser();

    @Update("update user set username = #{username},password = #{password}, phone = #{phone}, email = #{email}, user_avatar = #{userAvatar}, update_time = #{updateTime} where id = #{id}")
    int updateUser(User user);

    @Delete("delete from user where id = #{id}")
    int deleteUser(@Param("id") int id);

    @Update("update user set user_avatar = #{userAvatar,jdbcType=VARCHAR}, update_time = #{updateTime,jdbcType=DATE} where id = #{id}")
    int updateUserAvatarById(@Param("userAvatar") String userAvatar, @Param("updateTime") String updateTime, @Param("id") int id);
}




