package com.blog.server.controller;

import com.alibaba.fastjson2.JSONObject;
import com.blog.server.entity.Result;
import com.blog.server.entity.UserInfo;
import com.blog.server.entity.dto.UserLoginDTO;
import com.blog.server.entity.vo.RegisterRequestVO;
import com.blog.server.service.AccountService;
import com.blog.server.entity.User;
import com.blog.server.entity.VerifyCode;
import com.blog.server.aspect.ApiOperationLog;
import com.blog.server.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;
import java.util.function.Supplier;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.simpleframework.xml.core.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "认证接口")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Resource
    private JwtUtils jwtUtils;

    @ApiOperationLog(description = "管理员登录")
    @Operation(summary = "管理员登录")
    @PostMapping("/login")
    public String login(@RequestBody UserLoginDTO userLoginDTO) {
        return accountService.login(userLoginDTO);
    }

    @ApiOperationLog(description = "获取用户信息")
    @Operation(summary = "获取用户信息")
    @GetMapping("/public/user")
    public String listUserinfo() {
        return accountService.listUserinfo();
    }

    @ApiOperationLog(description = "修改用户信息")
    @Operation(summary = "修改用户信息")
    @PostMapping("/protected/updateuser")
    public String updateUser(@RequestBody User user) {
        return accountService.updateUser(user);
    }

    @ApiOperationLog(description = "删除用户")
    @Operation(summary = "删除用户")
    @DeleteMapping("/protected/deleteuser")
    public String deleteUser(@RequestBody int id, HttpServletRequest request) {
        if (!jwtUtils.extractRoleFromToken(request.getHeader("Authorization")).equals("管理员")) {
            return Result.forbidden("权限不足").asJsonStringNotNull();
        }
        return accountService.deleteUser(id);
    }

    @ApiOperationLog(description = "上传用户头像")
    @Operation(summary = "上传用户头像")
    @PostMapping("/protected/update-useravatar")
    public String updateUserAvatar(@RequestBody String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        String base64Image = jsonObject.getString("image");
        String updateTime = jsonObject.getString("updateTime");
        int id = jsonObject.getInteger("id");
        return accountService.updateUserAvatar(base64Image, updateTime, id);
    }

    @ApiOperationLog(description = "请求验证码")
    @Operation(summary = "请求验证码")
    @GetMapping("/public/verifycode")
    public VerifyCode getVerifyCode() throws IOException {
        return accountService.generateVerifyCode();
    }

    @ApiOperationLog(description = "请求邮箱验证码")
    @Operation(summary = "请求邮箱验证码")
    @GetMapping("/public/register-email")
    public Result<Void> sendRegisterEmail(@RequestParam @Email String email, @RequestParam @Pattern(regexp = "^(register|reset)$") String type, HttpServletRequest request) {
        return this.messageHandler(() -> accountService.registerEmailVerifyCode(type, email, request.getRemoteAddr()));
    }

    @ApiOperationLog(description = "用户注册")
    @Operation(summary = "用户注册")
    @PostMapping("/public/register")
    public Result<Void> register(@RequestBody RegisterRequestVO vo){
        return this.messageHandler(() -> accountService.registerAccount(vo));
    }

    @ApiOperationLog(description = "根据id查询用户信息")
    @Operation(summary = "根据id查询用户信息")
    @GetMapping("/public/userinfo")
    public String getUserInfo(@RequestParam String id){
        UserInfo userInfo = accountService.findUserById(id);
        if ( userInfo == null) {
            return new Result<>(400, null, "用户不存在").asJsonStringNotNull();
        }
        return new Result<>(200, userInfo, "获取用户信息成功").asJsonString();
    }

    @ApiOperationLog(description = "前端修改用户信息")
    @Operation(summary = "前端修改用户信息")
    @PostMapping("/protected/update-userinfo")
    public String updateUserInfo(@RequestBody UserInfo userInfo){
        return accountService.updateUserInfo(userInfo);
    }

    private Result<Void> messageHandler(Supplier<String> action){
        String message = action.get();
        return message == null ? Result.success() : Result.failure(400, message);
    }
}
