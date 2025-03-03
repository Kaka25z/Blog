package com.blog.server.controller;

import com.blog.server.aspect.ApiOperationLog;
import com.blog.server.entity.Result;
import com.blog.server.entity.dto.MenuDTO;
import com.blog.server.entity.dto.RouterDTO;
import com.blog.server.service.MenuService;
import com.blog.server.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/protected")
@Tag(name = "菜单接口")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Resource
    private JwtUtils jwtUtils;

    @ApiOperationLog(description = "获取所有菜单")
    @Operation(summary = "获取所有菜单")
    @GetMapping("/listMenu")
    public String listMenu(HttpServletRequest request){

        String role = jwtUtils.extractRoleFromToken(request.getHeader("Authorization"));

        if (!role.equals("管理员")) {
            return Result.forbidden("权限不足").asJsonStringNotNull();
        }

        List<RouterDTO> routerTrees = menuService.getRouterTree();

        return new Result<>(200, routerTrees, "获取菜单成功").asJsonStringNotNull();
    }
}
