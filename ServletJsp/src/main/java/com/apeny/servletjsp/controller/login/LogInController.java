package com.apeny.servletjsp.controller.login;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录处理
 * Created by monis on 2018/10/12.
 */
@Controller
public class LogInController {

    @RequestMapping("/toLogIn.dox")
    public String toLogIn() {
        return "/sitole/authentication/Login";
    }

    @RequestMapping("/login.dox")
    public String login(String username, String password, ModelMap model) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            model.addAttribute("username", "用户名错误！");
            return "/sitole/authentication/Login";
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            model.addAttribute("password", "密码错误");
            return "/sitole/authentication/Login";
        }
        return "redirect:/success.dox";
    }
}
