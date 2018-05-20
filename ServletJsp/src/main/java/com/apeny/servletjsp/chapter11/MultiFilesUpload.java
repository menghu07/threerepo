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
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by apeny on 2018/5/20.
 */
@WebServlet(urlPatterns = {"/chapter11/multifilesupload.do"})
@MultipartConfig(maxRequestSize = 104800, maxFileSize = 102400)
public class MultiFilesUpload extends HttpServlet {
    /**
     * into input page
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/chapter11/MultiFilesUpload.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("upload file begin.....");
        Collection<Part> parts = req.getParts();
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();
        for (Part part : parts) {
            String fileName = getFileName(part);
            if (fileName != null && !fileName.isEmpty()) {
                part.write(getServletContext().getRealPath("/WEB-INF") + "/" + fileName);
            }
            pw.println("<br/>Field Name: " + part.getName());
            pw.println("<br/>Upload Mutli File Names: " + fileName);
            pw.println("<br/>Size: " + part.getSize());
        }
        pw.println("<br/>content-length: " + req.getContentLength());
        //write to browser
        String author = req.getParameter("author");
        pw.println("<br/> Author: " + author);
        pw.flush();
        System.out.println("upload file end...." + req);
    }

    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] elements = contentDisposition.split(";");
        System.out.println("post names: " + Arrays.toString(elements));
        for (String ele : elements) {
            if (ele.trim().startsWith("filename")) {
                return ele.substring(ele.indexOf("=") + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
