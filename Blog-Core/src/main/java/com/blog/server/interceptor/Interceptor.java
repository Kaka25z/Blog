package com.blog.server.interceptor;

import com.blog.server.utils.JwtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

@Component
public class Interceptor implements HandlerInterceptor {
    @Resource
    private JwtUtils jwtUtils;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        System.out.println("token: " + token);
        if(token == null || !jwtUtils.verifyToken(token)){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Access Forbidden");
            return false;
        }
        return true;
    }
}
