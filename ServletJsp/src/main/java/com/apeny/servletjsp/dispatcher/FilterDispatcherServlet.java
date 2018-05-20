package com.apeny.servletjsp.dispatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 1. forward 之前执行flush会引起forwar执行抛出异常，PrintWrite.flush()会提交Response，但还是可以在flush的
 * 2. !!! response.getWriter()和response.getOutputStream()不能同时用
 * 3 在调试时，默认是useOutputStream=false,偶然出现问题啦。。。。，调试时报getOutputStream()已经用了
 */
@WebServlet(name = "dispatcherfilterservlet", value = "*.action")
public class FilterDispatcherServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 6230022484059652855L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("dispatcher filter servlet do not anything");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}