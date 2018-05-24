package com.apeny.servletjsp.chapter10;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by apeny on 2018/5/24.
 */
@WebFilter(filterName = "dispatcherfilter", urlPatterns = {"/dispatcherfilter/productinput.filter", "/dispatcherfilter/productsave.filter"})
public class DispatcherFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        //URI
        String uri = httpServletRequest.getRequestURI();
        int lastSplash = uri.lastIndexOf('/');
        String dispatchUrl = null;
        if (uri.endsWith("productinput.filter")) {
            //do nothing.....
            dispatchUrl = "/WEB-INF/pages/chapter10/ProductForm.jsp";
        } else if (uri.endsWith("productsave.filter")) {
            //组装ProductForm
            ProductForm productForm = new ProductForm();
            productForm.setName(httpServletRequest.getParameter("name"));
            productForm.setDescription(httpServletRequest.getParameter("description"));
            productForm.setPrice(httpServletRequest.getParameter("price"));
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
            httpServletRequest.setAttribute("product", product);
            dispatchUrl = "/WEB-INF/pages/chapter10/ProductDetail.jsp";
        }
        if (dispatchUrl != null) {
            httpServletRequest.getRequestDispatcher(dispatchUrl).forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
