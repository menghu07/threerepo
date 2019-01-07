package com.apeny.servletjsp.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 失败处理
 * Created by monis on 2018/10/14.
 */
@Controller
public class FailureController {

    @RequestMapping("/failure.dox")
    @ResponseBody
    public String failure() {
        return "shiro failure";
    }
}
