package com.blog.server.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetaDTO {
    // 菜单名称
    private String title;
    // 菜单图标
    private String icon;
    // 是否显示该菜单
    private Boolean showLink;
    // 是否显示父级菜单
    private Boolean showParent;
    // 页面级别权限设置
    private List<String> roles;
    // 按钮级别权限设置
//    private List<String> auths;
    // 需要内嵌的iframe链接地址
    private String frameSrc;

    private Boolean isFrameSrcInternal;

    /**
     *  菜单排序，值越高排的越后（只针对顶级路由）
     */
    private Integer rank;
}
