package com.apeny.servletjsp.chapter04;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.apeny.servletjsp.domain.Address;
import com.apeny.servletjsp.domain.Employee;

@WebServlet(name = "EmployeeServlet", value={"*.ch04do", "*.ch05do"})
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
		 Map<String, String> map = new HashMap<>();
		 map.put("China", "BeiJing");
		 map.put("Austria", "Vienna");
		 map.put("Austrilia", "Canberra");
		 map.put("Canada", "Ottawa");
		 req.setAttribute("capitals", map);
		 req.setAttribute("employee", emp);
		 String servletPath = req.getServletPath();
		 servletPath = servletPath.replaceAll("\\.ch0.do", "\\.jsp");
		 req.getRequestDispatcher("/WEB-INF" + servletPath).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
