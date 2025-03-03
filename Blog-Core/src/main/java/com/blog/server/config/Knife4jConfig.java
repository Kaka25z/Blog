package com.blog.server.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Knife4jConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                // 接口文档标题
                .info(new Info().title("Memory Blog接口文档")
                        // 接口文档简介
                        .description("一个风格可爱的个人主题博客，欢迎你来体验 Memory 的魅力！")
                        // 接口文档版本
                        .version("1.0版本")
                        // 开发者联系方式
                        .description("后台接口文档"));
    }
}
