package com.blog.server.aspect;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface ApiOperationLog {
    /**
     * 接口 功能描述
     *
     * @return
     */
    String description() default "";
}
