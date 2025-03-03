package com.blog.server.exception;


import com.blog.server.entity.Result;

public class CommonException extends RuntimeException{

    private int code;

    private String msg;

    public CommonException(Result<?> result){
        super("异常状态码为：" + result.code() + "；具体异常信息为：" + result.message());
        this.code = result.code();
        this.msg = result.message();
    }

    public CommonException(int code, String msg){
        super("异常状态码为：" + code + "；具体异常信息为：" + msg);
        this.code = code;
        this.msg = msg;
    }

    public CommonException(){
        this(Result.success(null));
    }

    public static CommonException of(int code, String msg) {
        return new CommonException(code, msg);
    }

    public static void thr(Result<?> responseStatusEnum) {
        throw new CommonException(responseStatusEnum);
    }

}
