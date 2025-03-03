package com.blog.server.entity.dto;

import lombok.Data;

@Data
public class UserLoginDTO {

    private String username;

    private String password;

    private String verifyCodeKey;

    private String verifyCode;
}
