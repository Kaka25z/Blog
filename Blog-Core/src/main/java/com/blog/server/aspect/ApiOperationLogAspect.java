package com.blog.server.aspect;

import com.blog.server.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;


@Aspect
@Component
@Slf4j
public class ApiOperationLogAspect {
    /**
     * @deprecated 自定义切点，凡是添加该注解，都会执行环绕中的代码
     * */
    @Pointcut("@annotation(com.blog.server.aspect.ApiOperationLog)")
    public void apiOperationLog(){}

    /**
     * @deprecated 环绕
     * */
    @Around("apiOperationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            long startTime = System.currentTimeMillis();
            MDC.put("traceId", UUID.randomUUID().toString());
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();

            Object[] args = joinPoint.getArgs();
            String argsStr = Arrays.stream(args).map(JsonUtil::toJsonString).collect(Collectors.joining(", "));
            String description = getApiOperationLogDescription(joinPoint);
            log.info("请求开始：[{}]，请求类：{}，请求方法：{}",
                    description, className, methodName);
            // 执行方法
            Object result = joinPoint.proceed();
            // 执行耗时
            long executionTime = System.currentTimeMillis() - startTime;

            // 打印出参等相关信息
            log.info("请求结束: [{}], 耗时: {}ms, 出参: {}",
                    description, executionTime, JsonUtil.toJsonString(result));

            return result;

        }finally {
            MDC.clear();
        }
    }

    private String getApiOperationLogDescription(ProceedingJoinPoint joinPoint) {
        // 1. 从 ProceedingJoinPoint 获取 MethodSignature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        // 2. 使用 MethodSignature 获取当前被注解的 Method
        Method method = signature.getMethod();

        // 3. 从 Method 中提取 LogExecution 注解
        ApiOperationLog apiOperationLog = method.getAnnotation(ApiOperationLog.class);

        // 4. 从 LogExecution 注解中获取 description 属性
        return apiOperationLog.description();
    }
}
