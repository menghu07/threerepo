package com.apeny.cors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by apeny on 2018/1/5.
 */
@WebServlet(name = "cors", value = "/cors.do")
public class CrossOriginResourceSharing extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("request is sent. cors");
        req.getRequestDispatcher("/WEB-INF/pages/cors/cors.jsp").forward(req, resp);
    }
}
