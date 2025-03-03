package com.blog.server.entity.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class RegisterRequestVO {

    @Email
    private String email;

    @Length(max =6 , min = 6)
    private String code;

    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$")
    @Length(max = 20, min = 1)
    private String username;

    @Length(max = 20, min = 6)
    private String password;
}
