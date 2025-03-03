package com.blog.server.service;

import com.blog.server.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.server.entity.dto.MenuDTO;
import com.blog.server.entity.dto.RouterDTO;

import java.util.List;

/**
* @author root
* @description 针对表【menu】的数据库操作Service
* @createDate 2024-04-27 14:42:05
*/
public interface MenuService extends IService<Menu> {

    public String ListMenu();

    boolean hasChildrenMenu(Long menuId);

    List<MenuDTO> getMenuList();

    List<RouterDTO> getRouterTree();
}
