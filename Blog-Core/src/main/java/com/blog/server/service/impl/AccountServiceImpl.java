package com.blog.server.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.server.constants.Const;
import com.blog.server.entity.Result;
import com.blog.server.entity.User;
import com.blog.server.entity.UserInfo;
import com.blog.server.entity.VerifyCode;
import com.blog.server.entity.dto.LoginResultDTO;
import com.blog.server.entity.dto.UserLoginDTO;
import com.blog.server.entity.vo.RegisterRequestVO;
import com.blog.server.service.FileService;
import com.blog.server.service.AccountService;
import com.blog.server.mapper.UserMapper;
import com.blog.server.utils.EncryptUtil;
import com.blog.server.utils.FlowLimitUtil;
import com.blog.server.utils.JwtUtils;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;

import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
* @author root
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-04-22 16:09:22
*/
@Service
@Slf4j
public class AccountServiceImpl extends ServiceImpl<UserMapper, User>
    implements AccountService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileService fileService;

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Resource
    private FlowLimitUtil flowLimitUtil;

    @Override
    public String login(UserLoginDTO user) {
        
        String actual = user.getVerifyCode();

        if (redisTemplate.getExpire(user.getVerifyCodeKey(), TimeUnit.SECONDS) < 0) {

            redisTemplate.delete(user.getVerifyCodeKey());

            return Result.failure(400, "验证码已过期").asJsonString();
        }

        String expected = redisTemplate.opsForValue().get(user.getVerifyCodeKey());

        if (!StringUtils.hasText(expected) || !expected.equals(actual)) {

            return Result.failure(400, "验证码错误").asJsonString();
        }
        String password = EncryptUtil.encrypt(user.getPassword());
        User res = userMapper.findUser(user.getUsername(), password);
        if (res != null ) {
            LoginResultDTO resultDTO = new LoginResultDTO();
            resultDTO.setToken(jwtUtils.createToken(user.getUsername(), res.getRole()));
            resultDTO.setUserAvatar(res.getUserAvatar());
            resultDTO.setUsername(res.getUsername());
            resultDTO.setRoles(res.getRole());
            resultDTO.setId(res.getId());
            redisTemplate.delete(user.getVerifyCodeKey());
            return new Result<>(200, resultDTO, "登录成功").asJsonString();
        }

        return Result.failure(400, "用户名或密码错误").asJsonString();
    }

    @Override
    public String listUserinfo() {
        List<User> user = userMapper.listUser();
        for (User u : user) {
            u.setPassword(null);
        }
        return new Result<>(200, user, "获取成功").asJsonString();
    }

    @Override
    public String updateUser(User user) {
        String password = EncryptUtil.encrypt(user.getPassword());
        user.setPassword(password);
        userMapper.updateUser(user);
        return Result.success().asJsonString();
    }

    @Override
    public String deleteUser(int headerId) {
        int isSuccess = userMapper.deleteUser(headerId);
        if (isSuccess > 0){
            return Result.success().asJsonString();
        }else {
            return Result.failure(400,"删除失败").asJsonString();
        }
    }

    @Override
    public String updateUserAvatar(String base64Image, String updateTime, int id) {
        String url = fileService.handleBase64Img(base64Image);
        int isSuccess = userMapper.updateUserAvatarById(url, updateTime, id);
        if (isSuccess > 0) return new Result<>(200, url, "更新成功").asJsonString();
        else return Result.failure(400,"更新失败").asJsonString();
    }

    @Data
    public static class UserRequest{
        private String username;
    }

    @Override
    public VerifyCode generateVerifyCode() throws IOException {
        
        Captcha captcha = new ArithmeticCaptcha();
        captcha.setLen(3);

        String verifyCodeKey = UUID.randomUUID().toString();
        String verifyCode = captcha.text();

        VerifyCode verifyCodeObj = new VerifyCode(verifyCodeKey, captcha.toBase64(), verifyCode);

        redisTemplate.opsForValue().set(verifyCodeKey, verifyCode, 60, TimeUnit.SECONDS);

        return verifyCodeObj;
    }

    @Override
    public String registerEmailVerifyCode(String type, String email, String ip) {

        synchronized (ip.intern()){
            if (!this.isLimit(ip)) return "请求过于频繁，请稍后再试";

            Random random = new Random();
            int code = random.nextInt(900000) + 100000;
            Map<String, Object> data = Map.of("type", type, "email", email, "code", code);
            amqpTemplate.convertAndSend("mail", data);
            redisTemplate.opsForValue()
                    .set(Const.VERIFY_EMAIL_DATA + email, String.valueOf(code), 5, TimeUnit.MINUTES);
            return null;
        }
    }

    @Override
    public String registerAccount(RegisterRequestVO register) {
        String email = register.getEmail();
        String username = register.getUsername();
        String key = Const.VERIFY_EMAIL_DATA + email;
        String code = redisTemplate.opsForValue().get(key);
        if (code == null) return "请先获取验证码";
        if (!code.equals(register.getCode())) return "验证码错误";
        if (this.isEmailExist(email)) return "邮箱已被注册";
        if (this.isUsernameExist(username)) return "用户名已被注册";
        String password = EncryptUtil.encrypt(register.getPassword());
        User user = new User(null, username, password, register.getEmail(), null, null, "用户", null, null);
        if (this.save(user)) {
            redisTemplate.delete(key);
            return null;
        } else {
            return "系统内部错误";
        }
    }

    @Override
    public UserInfo findUserById(String id) {
        User res = this.baseMapper.selectById(id);
        return new UserInfo(res.getId(), res.getUsername(), null, res.getPhone(), res.getEmail(), res.getUserAvatar(), null);
    }

    @Override
    public String updateUserInfo(UserInfo userInfo) {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));
        Date DateNow = Date.from(now.toInstant());
        User user = new User(userInfo.getId(), userInfo.getUsername(), EncryptUtil.encrypt(userInfo.getPassword()), userInfo.getEmail(), userInfo.getPhone(),  userInfo.getUserAvatar(), "用户", DateNow, null);
        int isSuccess = userMapper.updateUser(user);
        if (isSuccess > 0) {
            return new Result<>(200, null, "更新成功").asJsonStringNotNull();           
        } else {
            return Result.failure(400, "更新失败").asJsonStringNotNull();
        }
        
    }

    private boolean isEmailExist(String email) {
        return this.baseMapper.exists(Wrappers.<User>query().eq("email", email));
    }

    private boolean isUsernameExist(String username) {
        return this.baseMapper.exists(Wrappers.<User>query().eq("username", username));
    }

    private boolean isLimit(String address) {
        String key = Const.VERIFY_EMAIL_LIMIT + address;
        String countKey = key + ":count";

        if (redisTemplate.hasKey(key)) {
            return false;
        }

        String countStr = redisTemplate.opsForValue().get(countKey);
        Integer count = null;
        if (countStr != null) {
            try {
                count = Integer.parseInt(countStr);
            } catch (NumberFormatException e) {
                e.getMessage();
            }
        }

        if (count == null) {
            redisTemplate.opsForValue().set(countKey, "1", 2, TimeUnit.MINUTES);
        } else if (count < 5) {
            redisTemplate.opsForValue().increment(countKey);
        } else {
            redisTemplate.opsForValue().set(key, "true", 5, TimeUnit.MINUTES);
            redisTemplate.delete(countKey);
            return true;
        }

        return flowLimitUtil.limitOnceCheck(key, 60);
    }
}