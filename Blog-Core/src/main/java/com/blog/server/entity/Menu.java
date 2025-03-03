package com.blog.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;

/**
 * 
 * @TableName menu
 */
@TableName(value ="menu")
@Data
public class Menu implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long menuId;

    private static final long serialVersionUID = 1L;

    @TableField("menu_name")
    private String menuName;

    @TableField("menu_type")
    private Integer menuType;

    @TableField("router_name")
    private String routerName;

    @TableField("parent_id")
    private Long parentId;

    @TableField("path")
    private String path;

    @TableField("is_button")
    private Boolean isButton;

    @TableField("permission")
    private String permission;

    @TableField("meta_info")
    private String metaInfo;

    @TableField("component")
    private String component;

    @TableField("`status`")
    private Integer status;

    @TableField("remark")
    private String remark;

    public Serializable pkVal() {
        return this.menuId;
    }

}
