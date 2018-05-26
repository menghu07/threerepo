package com.apeny.servletjsp.chapter12;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by apeny on 2018/5/26.
 */
@WebServlet(name = "loginservlet", urlPatterns = {"/download/login.do"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        if ("apeny".equals(userName) && "123456".equals(password)) {
            req.getSession().setAttribute("logined", "logined");
            resp.sendRedirect(req.getContextPath() + "/download/download.do");
        } else {
            req.getRequestDispatcher("/WEB-INF/pages/chapter12/Login.jsp").forward(req, resp);
        }
    }
}
