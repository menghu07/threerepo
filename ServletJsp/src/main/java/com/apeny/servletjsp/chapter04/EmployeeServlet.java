package com.apeny.servletjsp.chapter04;

import com.apeny.servletjsp.domain.Address;
import com.apeny.servletjsp.domain.Book;
import com.apeny.servletjsp.domain.Employee;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "EmployeeServlet", value = {"*.ch04do", "*.ch05do", "*.ch06do", "*.ch07do"})
public class EmployeeServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 7105201612251707912L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Address addr = new Address();
        addr.setStreetNumber("0001");
        addr.setStreetName("Reu D'you");
        addr.setZipCode("902341");
        addr.setCity("Trenton");
        addr.setState("New Jersey");
        addr.setCountry("USA");
        Employee emp = new Employee();
        emp.setId(9);
        emp.setName("zhi&z'i");
        emp.setAddress(addr);
        Book book1 = new Book();
        book1.setIsbn("978-090839616");
        book1.setTitle("Java 7： A Beginner's Tutorial");
        book1.setPrice(34.00);
        Book book2 = new Book();
        book2.setIsbn("978-09080331608");
        book2.setTitle("Struts 2 Design and Programming: A Tutorial");
        book2.setPrice(48.02);
        Book book3 = new Book();
        book3.setIsbn("978-0975212820");
        book3.setPrice(92.34);
        book3.setTitle("Dimensinal Data Warehouseing with MySQL: A Tutorial");
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        Map<String, String> map = new HashMap<>();
        map.put("China", "BeiJing");
        map.put("Austria", "Vienna");
        map.put("Austrilia", "Canberra");
        map.put("Canada", "Ottawa");
        Map<String, String[]> bigCities = new HashMap<>();
        bigCities.put("Austrilia", new String[]{"Sydney", "Canberra", "Melbourne", "Perth"});
        bigCities.put("New Zealand", new String[]{"Auckland", "Christchurch", "Wellington"});
        bigCities.put("Indonesia", new String[]{"Jakarta", "Surabaya", "Meda"});
        req.setAttribute("capitals", map);
        req.setAttribute("employee", emp);
        req.setAttribute("books", books);
        req.setAttribute("bigCities", bigCities);
        req.setAttribute("now", new Date());
        req.setAttribute("name1", "new name");
        req.setAttribute("name1", null);
        String servletPath = req.getServletPath();
        servletPath = servletPath.replaceAll("\\.ch0.do", "\\.jsp");
        System.out.println("Employee Servlet Show");
        HttpSession httpsSession = req.getSession();
        httpsSession.setAttribute("book2", book1);
        HttpSession session = req.getSession();
        session.setAttribute("book3", book2);
        System.out.println("session1 == session2 " + (session == httpsSession));
        httpsSession.setAttribute("book4", book1);
        req.getRequestDispatcher("/WEB-INF" + servletPath).include(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
