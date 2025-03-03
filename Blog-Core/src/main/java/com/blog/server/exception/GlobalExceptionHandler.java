package com.blog.server.exception;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.blog.server.entity.Result;
import jakarta.xml.bind.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.security.SignatureException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(CommonException.class)
    @ResponseBody
    public Result<Void> handleCommonException(Exception e){
        log.error("Resolve [{} : {}]", e.getClass().getName(), e.getMessage());
        return Result.failure(500, "业务异常");
    }

    @ExceptionHandler({SignatureException.class, TokenExpiredException.class, JWTDecodeException.class, SignatureVerificationException.class})
    @ResponseBody
    public Result<Void> returnJwtException(Exception e){
        log.error("Resolve [{} : {}]", e.getClass().getName(), e.getMessage());
        return Result.forbidden("JwtToken异常");
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseBody
    public Result<Void> handleValidationException(HandlerMethodValidationException e){
        log.error("Resolve [{} : {}]", e.getClass().getName(), e.getMessage());
        return Result.failure(400, "请求参数异常");
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<Void> handleException(Exception e){
        log.error("Resolve [{} : {}]", e.getClass().getName(), e.getMessage());
        return Result.failure(500, "系统内部异常");
    }
}
