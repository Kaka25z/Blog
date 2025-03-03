package com.blog.server.entity.dto;

import cn.hutool.core.util.StrUtil;
import com.blog.server.entity.Menu;
import com.blog.server.utils.JacksonUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MenuDetailDTO extends MenuDTO{

    public MenuDetailDTO(Menu entity) {
        super(entity);
        if (entity == null) {
            return;
        }
        if (StrUtil.isNotEmpty(entity.getMetaInfo()) && JacksonUtil.isJson(entity.getMetaInfo())) {
            this.meta = JacksonUtil.from(entity.getMetaInfo(), MetaDTO.class);
        }
        this.permission = entity.getPermission();
    }

    private String permission;
    private MetaDTO meta;

}
