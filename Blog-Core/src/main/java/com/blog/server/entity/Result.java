package com.blog.server.entity;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Result<T> (int code, T data, String message) {
    public static <T> Result<T> success(T data){
        return new Result<>(200, data, "请求成功");
    }

    public static <T> Result<T> success(){
        return success(null);
    }

    public static <T> Result<T> failure(int code, String message){
        return new Result<>(code, null, message);
    }

    public static <T> Result<T> unauthorized(String message){
        return failure(401, message);
    }

    public static <T> Result<T> forbidden(String message){
        return failure(403, message);
    }

    public String asJsonString() {
        return JSONObject.toJSONString(this, JSONWriter.Feature.WriteNulls);
    }

    public String asJsonStringNotNull(){
        return JSONObject.toJSONString(this);
    }
}
