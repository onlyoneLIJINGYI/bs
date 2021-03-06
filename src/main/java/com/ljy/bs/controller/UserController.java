package com.ljy.bs.controller;

import com.ljy.bs.entity.*;
import com.ljy.bs.result.Result;
import com.ljy.bs.result.ResultFactory;
import com.ljy.bs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * User controller.
 *
 */

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AdminUserRoleService adminUserRoleService;

    //请求所有用户的用户信息和角色信息
    @CrossOrigin
    @GetMapping("/api/admin/user")
    public Result listUsers() {
        System.out.println("获取用户信息成功");
        return ResultFactory.buildSuccessResult(userService.list());
    }

    @CrossOrigin
    @PutMapping("/api/admin/user/status")
    public Result updateUserStatus(@RequestBody @Valid User requestUser) {
        userService.updateUserStatus(requestUser);
        return ResultFactory.buildSuccessResult("用户状态更新成功");
    }

    @CrossOrigin
    @PutMapping("/api/admin/user/password")
    public Result resetPassword(@RequestBody @Valid User requestUser) {
        userService.resetPassword(requestUser);
        return ResultFactory.buildSuccessResult("重置密码成功");
    }

    @PutMapping("/api/admin/user")
    public Result editUser(@RequestBody @Valid User requestUser) {
        userService.editUser(requestUser);
        return ResultFactory.buildSuccessResult("修改用户信息成功");
    }
}
