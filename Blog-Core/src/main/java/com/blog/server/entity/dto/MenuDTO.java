package com.blog.server.entity.dto;

import cn.hutool.core.util.StrUtil;
import com.blog.server.entity.Menu;
import com.blog.server.enums.MenuTypeEnum;
import com.blog.server.enums.StatusEnum;
import com.blog.server.utils.BasicEnumUtil;
import com.blog.server.utils.JacksonUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class MenuDTO {

    public MenuDTO(Menu menu) {
        if (menu != null){
            this.id = menu.getMenuId();
            this.parentId = menu.getParentId();
            this.menuName = menu.getMenuName();
            this.routerName = menu.getRouterName();
            this.path = menu.getPath();
            this.status = menu.getStatus();
            this.isButton = menu.getIsButton();
            this.statusStr = BasicEnumUtil.getDescriptionByValue(StatusEnum.class, menu.getStatus());

            if (!menu.getIsButton()){
                this.menuType = menu.getMenuType();
                this.menuTypeStr = BasicEnumUtil.getDescriptionByValue(MenuTypeEnum.class, menu.getMenuType());
            } else {
                this.menuType = 0;
            }

            if (StrUtil.isNotEmpty(menu.getMetaInfo()) && JacksonUtil.isJson(menu.getMetaInfo())){
                MetaDTO metaDTO = JacksonUtil.from(menu.getMetaInfo(), MetaDTO.class);
                this.rank = metaDTO.getRank();
                this.icon = metaDTO.getIcon();
            }
        }

    }

    private Long id;

    private Long parentId;

    private String menuName;

    private String routerName;

    private String path;

    private Integer rank;

    private Integer menuType;

    private String menuTypeStr;

    private Boolean isButton;

    private Integer status;

    private String statusStr;

    private Date createTime;

    private String icon;
}
