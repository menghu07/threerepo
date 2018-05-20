package com.apeny.servletjsp.chapter11;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by apeny on 2018/5/20.
 */
@WebServlet(urlPatterns = {"/chapter11/singlefileupload.do"})
@MultipartConfig
public class SingleFileUpload extends HttpServlet {
    /**
     * into input page
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/chapter11/SingleFileUpload.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("upload file begin.....");
        Part part = req.getPart("fileName");
        String fileName = getFileName(part);
        if (fileName != null && !fileName.isEmpty()) {
            part.write(getServletContext().getRealPath("/WEB-INF") + "/" + fileName);
        }
        //write to browser
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();
        pw.println("<br/>Upload File Name: " + fileName);
        pw.println("<br/>Size: " + part.getSize());
        String author = req.getParameter("author");
        pw.println("<br/> Author: " + author);
        pw.flush();
        System.out.println("upload file end...." + req);
    }

    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] elements = contentDisposition.split(";");
        for (String ele : elements) {
            if (ele.trim().startsWith("filename")) {
                return ele.substring(ele.indexOf("=") + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
