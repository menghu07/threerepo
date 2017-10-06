package com.apeny.servletjsp.chapter01;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.apeny.servletjsp.domain.Customer;

/**
 * jvm 实验
 * 1 -Xmx32m  -Xms32m -XX:+PrintGCDetails -XX:+UseSerialGC -Xloggc:gcjvm.log -XX:+HeapDumpOnOutOfMemoryError -XX:PermSize=32m
 * 2
 * 隐藏域，管理session
 * @author ahu
 *
 */
@WebServlet(name = "CustomerServlet", urlPatterns = {"/customer", "/editCustomer", "/updateCustomer"})
public class CustomerServlet extends HttpServlet {
    
    /**
     * 
     */
    private static final long serialVersionUID = -1074632759302929837L;

    private List<Customer> customers = new ArrayList<>();
    
    private final String cr = "<br/>";
    
    @Override
    public void init() throws ServletException {
        Customer cust1 = new Customer();
        cust1.setId(1);
        cust1.setName("cix1");
        cust1.setCity("new york");
        customers.add(cust1);
        Customer cust2 = new Customer();
        cust2.setId(2);
        cust2.setName("cix2");
        cust2.setCity("washington dc");
        customers.add(cust2);
    }
    
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        StringBuffer url = req.getRequestURL();
        String uri = req.getRequestURI();
        System.out.println("url : " + url);
        if (uri.endsWith("/customer")) {
            sendCustomerList(res);
        } else if (uri.endsWith("/editCustomer")) {
            sendEditForm(req, res);
        }
    }
    
    /**
     * 处理表单提交
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=utf-8");
        Integer id = Integer.valueOf(req.getParameter("id"));
        Customer cust = getCustomer(id);
        String name = req.getParameter("name");
        String city = req.getParameter("city");
        cust.setName(name);
        cust.setCity(city);
        sendCustomerList(res);
    }

    /**
     * 显示Customer列表
     * @param res
     * @throws IOException
     */
    private void sendCustomerList(HttpServletResponse res) throws IOException {
        res.setContentType("text/html;charset=utf-8");
        PrintWriter pw = res.getWriter();
        StringBuilder builder = new StringBuilder();
        builder.append("<html>")
        .append("<head>")
        .append("<title>show all customers</title>")
        .append("</head>")
        .append("<body>")
        .append("<table >");        
        builder.append("<tr><td>Name</td><td>City</td><td>operation</a></tr>" + cr);
        for (Customer cust : customers) {
            builder.append("<tr><td>" + cust.getName() + "</td><td>" + cust.getCity() +  "</td><td><a href='editCustomer?id=" + cust.getId() + "'>edit</a></tr>" + cr);
        }
        builder.append("</body>")
        .append("</html>");     
        pw.print(builder);
    }
    
    /**
     * 编辑Form
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    private void sendEditForm(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=utf-8");
        PrintWriter pw = res.getWriter();
        Integer id = Integer.valueOf(req.getParameter("id"));
        Customer cust = getCustomer(id);
        StringBuilder builder = new StringBuilder();
        builder.append("<html>")
        .append("<head>")
        .append("<title>show all customers</title>")
        .append("</head>")
        .append("<body>")
        .append("<form action='updateCustomer' method='post'>")
        .append("<input hidden='true' name='id' value='" + req.getParameter("id") +"'/>" + cr)
        .append("<label>Name</label><input name='name' value='" + cust.getName() + "'/>" + cr)
        .append("<label>City</label><input name='city' value='" + cust.getCity() + "'/>" + cr)
        .append("<input type='submit' value='submit'>" + cr)
        .append("<input type='reset' value='reset'/>" + cr)
        .append("</form>")
        .append("</body>")
        .append("</html>");     
        pw.print(builder);
    }

    /**
     * 获取Customer
     * @param id
     * @return
     * @throws ServletException
     */
    private Customer getCustomer(Integer id) throws ServletException {
        for (Customer cust : customers) {
            if (cust.getId() == id) {
                return cust;
            }
        }
        throw new ServletException();
    }
}
