package com.apeny.servletjsp.chapter10;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by apeny on 2018/5/22.
 */
@WebServlet(name = "controllerservlet", urlPatterns = {"/chapter10/product_input.do", "/chapter10/product_save.do", "/chapter10/product_search.do"})
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
            String action = req.getParameter("doaction");
            dispatchUrl = "/WEB-INF/pages/chapter10/ProductForm.jsp";
            if ("save".equals(action)) {
                //do nothing.....
                req.setAttribute("action", "chapter10/product_save.do");
            } else {
                req.setAttribute("action", "chapter10/product_search.do");
            }
        } else if (uri.endsWith("product_search.do")) {
            dispatchUrl = "/WEB-INF/pages/chapter10/ProductDetail.jsp";
            //组装ProductForm
            ProductForm productForm = new ProductForm();
            productForm.setName(req.getParameter("name"));
            SaveProductAction saveProductAction = new SaveProductAction();
            Product product = saveProductAction.queryOne(productForm.getName());
            req.setAttribute("product", product);
        } else {
            //组装ProductForm
            ProductForm productForm = new ProductForm();
            productForm.setName(req.getParameter("name"));
            productForm.setDescription(req.getParameter("description"));
            productForm.setPrice(req.getParameter("price"));
            List<String> errors = Validator.validate(productForm);
            if (errors.isEmpty()) {
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
                //从数据库中查询出来后再显示
                product = saveProductAction.queryOne(product.getName());
                //product 可序列化
                req.setAttribute("product", product);
                dispatchUrl = "/WEB-INF/pages/chapter10/ProductDetail.jsp";
            } else {
                req.setAttribute("errors", errors);
                req.setAttribute("form", productForm);
                dispatchUrl = "/WEB-INF/pages/chapter10/ProductForm.jsp";
            }
        }
        req.getRequestDispatcher(dispatchUrl).forward(req, resp);
    }
}
