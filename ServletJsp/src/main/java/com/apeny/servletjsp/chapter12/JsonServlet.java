package com.apeny.servletjsp.chapter12;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by apeny on 2018/5/26.
 */
@WebServlet(name = "jsonservlet", urlPatterns = {"/corsjson/json.do"})
public class JsonServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        resp.setHeader("ContentType", "application/json");
        pw.println("{name:23, passwd:'abc'}");
    }
}
