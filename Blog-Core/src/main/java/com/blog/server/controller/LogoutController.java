package com.blog.server.controller;

import com.blog.server.entity.Result;
import com.blog.server.utils.JwtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping("/api/protected")
public class LogoutController {

    @Resource
    private JwtUtils jwtUtils;

    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        String token = request.getHeader("Authorization");
        if (jwtUtils.invalidateJwt(token)){
            writer.write(Result.success("注销成功").asJsonString());
            return;
        }
        writer.write(Result.failure(400, "注销失败").asJsonString());
    }
}
