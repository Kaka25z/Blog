package com.blog.server.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.server.entity.Menu;
import com.blog.server.entity.dto.MenuDTO;
import com.blog.server.entity.dto.RouterDTO;
import com.blog.server.enums.StatusEnum;
import com.blog.server.service.MenuService;
import com.blog.server.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
* @author root
* @description 针对表【menu】的数据库操作Service实现
* @createDate 2024-04-27 14:42:05
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public String ListMenu() {
//        List<Menu> menuList = menuMapper.selectList(new QueryWrapper<>());
//        List<MenuVO> routers = new ArrayList<>();
//
//        for (Menu menu : menuList) {
//            MenuVO.Meta meta = new MenuVO.Meta();
//            meta.setIcon(menu.getIcon());
//            meta.setTitle(menu.getTitle());
//            meta.setRank(menu.getOrderNum());
//
//            MenuVO.Child firstChild = new MenuVO.Child();
//            firstChild.setPath(menu.getChildPath());
//            firstChild.setComponent(menu.getChildComponent());
//            firstChild.setName(menu.getChildName());
//            firstChild.setMeta(new MenuVO.Meta(null, menu.getTitle(), null, menu.getShowLink()));
//
//            MenuVO menus = new MenuVO();
//            menus.setPath(menu.getPath());
//            menus.setName(menu.getName());
//            menus.setComponent(menu.getComponent());
//            menus.setRedirect(menu.getRedirect());
//            menus.setMeta(meta);
//            menus.setChildren(Arrays.asList(firstChild));
//
//            routers.add(menus);
//        }
//
//        return new Result<>(200, routers, "查询成功").asJsonStringNotNull();
        return null;
    }

    @Override
    public boolean hasChildrenMenu(Long menuId) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", menuId);
        return baseMapper.exists(queryWrapper);
    }

    @Override
    public List<MenuDTO> getMenuList() {
        List<Menu> list = menuMapper.selectList(new QueryWrapper<>());
        return list.stream().map(MenuDTO::new)
                .sorted(Comparator.comparing(MenuDTO::getRank, Comparator.nullsLast(Integer::compareTo)))
                .collect(Collectors.toList());
    }

    @Override
    public List<RouterDTO> getRouterTree() {
        List<Tree<Long>> trees = buildMenuEntityTree();
        return buildRouterTree(trees);
    }

    private List<RouterDTO> buildRouterTree(List<Tree<Long>> trees) {
        List<RouterDTO> routers = new LinkedList<>();
        if (CollUtil.isNotEmpty(trees)) {
            for (Tree<Long> tree : trees) {
                Object entity = tree.get("entity");
                if (entity != null) {
                    RouterDTO routerDTO = new RouterDTO((Menu) entity);
                    List<Tree<Long>> children = tree.getChildren();
                    if (CollUtil.isNotEmpty(children)) {
                        routerDTO.setChildren(buildRouterTree(children));
                    }
                    routers.add(routerDTO);
                }

            }
        }
        return routers;
    }

    private List<Tree<Long>> buildMenuEntityTree() {
        List<Menu> allMenus = menuMapper.selectList(new QueryWrapper<>());

        // 传给前端的路由排除掉按钮和停用的菜单
        List<Menu> noButtonMenus = allMenus.stream()
                .filter(menu -> !menu.getIsButton())
                .filter(menu-> StatusEnum.ENABLE.getValue().equals(menu.getStatus()))
                .collect(Collectors.toList());

        TreeNodeConfig config = new TreeNodeConfig();
        // 默认为id 可以不设置
        config.setIdKey("menuId");

        return TreeUtil.build(noButtonMenus, 0L, config, (menu, tree) -> {
            tree.setId(menu.getMenuId());
            tree.setParentId(menu.getParentId());
            tree.putExtra("entity", menu);
        });

    }
}




