package com.blog.server.service;

import com.blog.server.entity.User;
import com.blog.server.entity.UserInfo;
import com.blog.server.entity.VerifyCode;

import java.io.IOException;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.server.entity.dto.UserLoginDTO;
import com.blog.server.entity.vo.RegisterRequestVO;

/**
* @author root
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-04-22 16:09:22
*/
public interface AccountService extends IService<User> {

    String login(UserLoginDTO user);

    String listUserinfo();

    String updateUser(User user);

    String deleteUser(int id);

    String updateUserAvatar(String base64Image, String updateTime, int id);

    VerifyCode generateVerifyCode() throws IOException;

    String registerEmailVerifyCode(String type, String email, String ip);

    String registerAccount(RegisterRequestVO register);

    UserInfo findUserById(String id);

    String updateUserInfo(UserInfo userInfo);
}
