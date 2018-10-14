package com.apeny.servletjsp.controller.login;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 成功处理
 * Created by monis on 2018/10/14.
 */
public class SuccessController {

    /**
     * 成功处理
     * @return
     */
    @RequestMapping("/success.dox")
    public String success() {
        return "shiro success";
    }
}
