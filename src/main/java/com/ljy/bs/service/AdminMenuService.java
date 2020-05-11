package com.ljy.bs.service;

import com.ljy.bs.dao.AdminMenuDAO;
import com.ljy.bs.entity.AdminMenu;
import com.ljy.bs.entity.AdminRoleMenu;
import com.ljy.bs.entity.AdminUserRole;
import com.ljy.bs.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 实现根据当前用户查询所有菜单项
 */
@Service
public class AdminMenuService {
    @Autowired
    AdminMenuDAO adminMenuDAO;
    @Autowired
    UserService userService;
    @Autowired
    AdminUserRoleService adminUserRoleService;
    @Autowired
    AdminRoleMenuService adminRoleMenuService;

    public List<AdminMenu> getAllByParentId(int parentId) {
        return adminMenuDAO.findAllByParentId(parentId);
    }

    //根据当前用户查询所有菜单项
    public List<AdminMenu> getMenusByCurrentUser() {
        // 使用shiro获取当前系统登录的用户名
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        System.out.println("当前系统登录的用户是："+username);
        User user = userService.findByUsername(username);

        // 获得当前用户对应的所有角色的 id 列表
        List<Integer> rids = adminUserRoleService.listAllByUid(user.getId())
                .stream().map(AdminUserRole::getRid).collect(Collectors.toList());

        // 查询出这些角色对应的所有菜单项
        List<Integer> menuIds = adminRoleMenuService.findAllByRid(rids)
                .stream().map(AdminRoleMenu::getMid).collect(Collectors.toList());
        List<AdminMenu> menus = adminMenuDAO.findAllById(menuIds).stream().distinct().collect(Collectors.toList());

        // 处理菜单项的结构，将询出来的菜单数据列表整合成具有层级关系的菜单树
        handleMenus(menus);
        return menus;
    }

    public List<AdminMenu> getMenusByRoleId(int rid) {
        List<Integer> menuIds = adminRoleMenuService.findAllByRid(rid)
                .stream().map(AdminRoleMenu::getMid).collect(Collectors.toList());
        List<AdminMenu> menus = adminMenuDAO.findAllById(menuIds);

        handleMenus(menus);
        return menus;
    }

    /**
     * 处理菜单的结构
     *
     * @param menus Menu items list without structure
     */
    public void handleMenus(List<AdminMenu> menus) {
        for (AdminMenu menu : menus) {
            List<AdminMenu> children = getAllByParentId(menu.getId());
            menu.setChildren(children);
        }

        Iterator<AdminMenu> iterator = menus.iterator();
        while (iterator.hasNext()) {
            AdminMenu menu = iterator.next();
            if (menu.getParentId() != 0) {
                iterator.remove();
            }
        }
    }

    /*public void handleMenus(List<AdminMenu> menus) {
        //使用了 lambda 表达式
        menus.forEach(m -> {
            List<AdminMenu> children = getAllByParentId(m.getId());
            m.setChildren(children);
        });

        menus.removeIf(m -> m.getParentId() != 0);
    }*/
}