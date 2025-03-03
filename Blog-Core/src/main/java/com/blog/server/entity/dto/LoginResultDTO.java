package com.blog.server.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResultDTO {

    private String token;

    private String userAvatar;

    private String username;

    private String roles;

    private Integer id;
}
