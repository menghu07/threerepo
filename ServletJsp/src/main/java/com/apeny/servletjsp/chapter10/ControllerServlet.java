package com.apeny.servletjsp.chapter10;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by apeny on 2018/5/22.
 */
@WebServlet(name = "controllerservlet", urlPatterns = {"/chapter10/product_input.do", "/chapter10/product_save.do"})
public class ControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //URI
        String uri = req.getRequestURI();
        int lastSplash = uri.lastIndexOf('/');
        String dispatchUrl = null;
        if (uri.endsWith("product_input.do")) {
            //do nothing.....
            dispatchUrl = "/WEB-INF/pages/chapter10/ProductForm.jsp";
        } else {
            //组装ProductForm
            ProductForm productForm = new ProductForm();
            productForm.setName(req.getParameter("name"));
            productForm.setDescription(req.getParameter("description"));
            productForm.setPrice(req.getParameter("price"));
            Product product = new Product();
            product.setName(productForm.getName());
            product.setDescription(productForm.getDescription());
            try {
                product.setPrice(Double.parseDouble(productForm.getPrice()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            SaveProductAction saveProductAction = new SaveProductAction();
            saveProductAction.save(product);
            //product 可序列化
            req.setAttribute("product", product);
            dispatchUrl = "/WEB-INF/pages/chapter10/ProductDetail.jsp";
        }
        req.getRequestDispatcher(dispatchUrl).forward(req, resp);
    }
}
