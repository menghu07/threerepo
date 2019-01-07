package com.apeny.servletjsp.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 成功处理
 * Created by monis on 2018/10/14.
 */
@Controller
public class SuccessController {

    /**
     * 成功处理
     * @return
     */
    @RequestMapping("/success.dox")
    @ResponseBody
    public String success() {
        return "shiro success";
    }
}
