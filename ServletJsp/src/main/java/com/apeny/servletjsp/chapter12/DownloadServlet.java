package com.apeny.servletjsp.chapter12;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

/**
 * Created by apeny on 2018/5/26.
 */
@WebServlet(name = "downloadservlet", urlPatterns = {"/download/download.do"})
public class DownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String referer = req.getHeader("referer");
        HttpSession httpSession = req.getSession();
        if (httpSession == null || httpSession.getAttribute("logined") == null) {
            req.getRequestDispatcher("/WEB-INF/pages/chapter12/Login.jsp").forward(req, resp);
            return;
        }
        if (!(referer != null && referer.contains("localhost:8080"))) {
            return;
        }
        String realPath = req.getServletContext().getRealPath("/WEB-INF/data");
        File servletJsp = new File(realPath, "servletjsp-learn.pdf");
        if (servletJsp.exists()) {
            resp.setContentType("application/pdf");
            resp.addHeader("Content-Disposition", "attachment; filename=servletjsp-learn.pdf");
            byte[] buffer = new byte[1024];
            FileInputStream fileInputStream = null;
            BufferedInputStream bufferedInputStream = null;
            try {
                fileInputStream = new FileInputStream(servletJsp);
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                OutputStream outputStream = resp.getOutputStream();
                int i = bufferedInputStream.read(buffer);
                while (i != -1) {
                    outputStream.write(buffer, 0, i);
                    i = bufferedInputStream.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
