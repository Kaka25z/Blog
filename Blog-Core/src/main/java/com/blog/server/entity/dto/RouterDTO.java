package com.blog.server.entity.dto;

import com.blog.server.entity.Menu;
import com.blog.server.utils.JacksonUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class RouterDTO {

    public RouterDTO(Menu entity) {
        if (entity != null) {
            this.name = entity.getRouterName();
            this.path = entity.getPath();
            this.component = entity.getComponent();
            if (JacksonUtil.isJson(entity.getMetaInfo())) {
                this.meta = JacksonUtil.from(entity.getMetaInfo(), MetaDTO.class);
            } else {
                this.meta = new MetaDTO();
            }
//            this.meta.setAuths(Lists.newArrayList(entity.getPermission()));
        }
    }

    private String name;

    /**
     * 路由路径地址
     */
    private String path;

    /**
     * 路由重定向
     */
    private String redirect;

    /**
     * 组件地址
     */
    private String component;

    /**
     * 一级菜单排序值（排序仅支持一级菜单）
     */
    private Integer rank;


    /**
     * 其他元素
     */
    private MetaDTO meta;

    /**
     * 子路由
     */
    private List<RouterDTO> children;
}
