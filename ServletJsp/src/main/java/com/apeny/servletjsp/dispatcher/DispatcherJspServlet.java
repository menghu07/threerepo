package com.apeny.servletjsp.dispatcher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "dispatcherservlet", value="*.do")
public class DispatcherJspServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6230022484059652855L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String servletPath = req.getServletPath();
		servletPath = servletPath.replace(".do", ".jsp");
		req.getRequestDispatcher("/WEB-INF" + servletPath).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String servletPath = req.getServletPath();
		servletPath = servletPath.replace(".do", ".jsp");
		req.getRequestDispatcher("/WEB-INF" + servletPath).forward(req, resp);
	}

	
}
