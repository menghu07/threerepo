package com.apeny.servletjsp.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Created by ahu on 2017年09月11日.
 */
public class AroundFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init拦截自动跳转页面");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("before request...." + request.getServletContext().getContextPath() + ((HttpServletRequest) request).getServletPath());
        chain.doFilter(request, response);
        System.out.println("after request...." + request.getServletContext().getContextPath() + " " + ((HttpServletRequest) request).getServletPath());
        PipedInputStream pipedInputStream = new PipedInputStream(new PipedOutputStream());
    }

    @Override
    public void destroy() {
        System.out.println("destroy拦截自动跳转页面");
    }
}
