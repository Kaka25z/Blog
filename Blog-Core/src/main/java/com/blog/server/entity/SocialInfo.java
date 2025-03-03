package com.blog.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SocialInfo {

    private String socialGithub;

    private String socialEmail;

    private String socialBilibili;

    private String socialQQ;

    private String socialNeteaseCloud;
    
}
