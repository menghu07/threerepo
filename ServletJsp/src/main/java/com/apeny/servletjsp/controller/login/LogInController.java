package com.apeny.servletjsp.controller.login;

import org.springframework.stereotype.Controller;
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
}
