package com.ljy.bs.controller;

import com.ljy.bs.entity.User;
import com.ljy.bs.result.Result;
import com.ljy.bs.result.ResultFactory;
import com.ljy.bs.service.UserService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.validation.Valid;

/*controller层负责数据交互，对于前端请求进行响应，接收前端发送的数据，通过调用 Service 获得处理后的数据并返回*/
@RestController
public class LoginController {
    @Autowired
    UserService userService;

    @CrossOrigin //允许跨域请求的注解
    /*@PostMapping(value = "api/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser) {
        // 对 html 标签进行转义，防止 XSS 攻击
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);
        //  通过userService的get方法根据用户名和密码查询用户
        User user = userService.get(username,requestUser.getPassword());

        if (null==user) {
            System.out.println("login failed");
            return ResultFactory.buildFailResult("账号不存在");
        } else {
            System.out.println("login success");
            return ResultFactory.buildSuccessResult(username);
        }
    }*/
    @PostMapping(value = "/api/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser) {
        // 对 html 标签进行转义，防止 XSS 攻击
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);
        /*使用shiro验证登录，subject.login(usernamePasswordToken)就可以执行验证， 判断用户名密码字段是否正确
        通过Realm类里重写的doGetAuthenticationInfo 方法获取认证信息，再根据ShiroConfiguration配置类里定义的hashedCredentialsMatcher方法，
        获取数据库中存储的hash加密后的密码，根据salt和客户端传递的password进行加密得到hash值与数据库中存储的hash值进行对比是否相等
        * */
        Subject subject = SecurityUtils.getSubject();
//        subject.getSession().setTimeout(10000);
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, requestUser.getPassword());
        try {
            System.out.println("登录成功");
            subject.login(usernamePasswordToken);
            return ResultFactory.buildSuccessResult(username);
        } catch (IncorrectCredentialsException e) {
            return ResultFactory.buildFailResult("密码错误");
        } catch (UnknownAccountException e) {
            return ResultFactory.buildFailResult("账号不存在");
        }
    }


    /*注册请求的api，将用户提交的密码使用hash算法进行加密，后台将密码加上随机生成的盐并 hash，再将 hash 后的值作为密码存入数据库，盐也作为单独的字段存起来
    盐是随机生成的一段字符串，与用户设置的明文密码一起加密，提高安全性，不易破解
    * */
    @CrossOrigin //允许跨域请求的注解
    @PostMapping("/api/register")
    @ResponseBody
    public Result register(@RequestBody User user) {
        int status = userService.register(user);
        switch (status) {
            case 0:
                return ResultFactory.buildFailResult("用户名和密码不能为空");
            case 1:
                return ResultFactory.buildSuccessResult("注册成功");
            case 2:
                return ResultFactory.buildFailResult("用户已存在");
        }
        return ResultFactory.buildFailResult("未知错误");
    }


    /*退出请求的api
    * */
    @CrossOrigin
    @ResponseBody
    @GetMapping("/api/logout")
    public Result logout() {
        /*使用 shrio提供的subject就可以实现登出，subject.logout()方法会清除 session、principals，并把 authenticated 设置为 false
        * */
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        String message = "成功登出";
        System.out.println("退出，注销账号");
        return ResultFactory.buildSuccessResult(message);
    }

    //身份认证请求的 api
    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/api/authentication")
    public String authentication(){
        return "身份认证成功";
    }

}
