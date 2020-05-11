package com.ljy.bs.controller;

import com.ljy.bs.result.Result;
import com.ljy.bs.result.ResultFactory;
import com.ljy.bs.service.AdminMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Menu controller.关于菜单请求的api
 */
@RestController
public class MenuController {
    @Autowired
    AdminMenuService adminMenuService;

    //请求当前用户对应的菜单
    @GetMapping("/api/menu")
    public Result menu() {
        return ResultFactory.buildSuccessResult(adminMenuService.getMenusByCurrentUser());
    }

    //请求角色对应的菜单
    @GetMapping("/api/admin/role/menu")
    public Result listAllMenus() {
        return ResultFactory.buildSuccessResult(adminMenuService.getMenusByRoleId(1));
    }
}