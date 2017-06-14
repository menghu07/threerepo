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

@WebServlet(name = "top10servlet", value={"/top10servlet/sub10", "/top10servlet/sub11"})
public class Top10Servlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4393725897323367012L;
	private List<String> londonAttractions;
	private List<String> parisAttractions;
	
	public void init() throws ServletException {
		londonAttractions = new ArrayList<>();
		londonAttractions.add("Buckingham Palace");
		londonAttractions.add("London Eye");
		londonAttractions.add("British Museum");
		londonAttractions.add("National Galery");
		londonAttractions.add("Big Ben");
		londonAttractions.add("Tower of London");
		londonAttractions.add("National History Meseum");
		londonAttractions.add("Canary Wharf");
		londonAttractions.add("2012 Olypic Park");
		londonAttractions.add("St Paul's Cathedral");
		
		parisAttractions = new ArrayList<>();
		parisAttractions.add("Eiffel Tower");
		parisAttractions.add("Notre Dame");
		parisAttractions.add("The Louvre");
		parisAttractions.add("Champs Elysees");
		parisAttractions.add("Arc de Triomphe");
		parisAttractions.add("Sainte Chapelle Church");
		parisAttractions.add("Les Invalides");
		parisAttractions.add("Musee d'Orsey");
		parisAttractions.add("Montmarte");
		parisAttractions.add("Sacre Couer Basilica");
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String city = req.getParameter("city");
		if (city != null && ("london".equals(city) || "paris".equals(city))) {
			showAttractions(req, res, city);
		} else {
			showMainPage(req, res);
		}
	}
	
	private void showMainPage(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String br = "<br/>";
		res.setContentType("text/html;charset=utf-8");
		PrintWriter pw = res.getWriter();
		StringBuilder builder = new StringBuilder();
		builder.append("<html>")
		.append("<head>")
		.append("<title>show city attraction, please select a city</title>")
		.append("</head>")
		.append("<body>")
		.append("<a href='?city=london'>London</a>" + br)
		.append("<a href='?city=paris'>Paris</a>" + br)
		.append("</body>")
		.append("</html>");		
		pw.print(builder);
	}
	
	private void showAttractions(HttpServletRequest req, HttpServletResponse res, String city) throws ServletException, IOException {
		String br = "<br/>";
		String page = req.getParameter("page");
		int pageNo = 1;
		if (page != null) {
			 pageNo = Integer.parseInt(page);
		}
		List<String> attractions = new ArrayList<>();
		if ("london".equals(city)) {
			attractions = londonAttractions;
		} else if ("paris".equals(city)) {
			attractions = parisAttractions;
		}
		
		res.setContentType("text/html;charset=utf-8");
		PrintWriter pw = res.getWriter();
		StringBuilder builder = new StringBuilder();
		builder.append("<html>")
		.append("<head>")
		.append("<title>show city attraction, please select a city</title>")
		.append("</head>")
		.append("<body>");
		int start = pageNo * 5  - 5;
		for (int i = start; i < start + 5; i++) {
			builder.append("<p>" + attractions.get(i).replaceAll("'", "&#39;") + "</p>");
		}
		builder.append("<a href='?city=" + city + "&page=1'>Page 1</a>" + br)
		.append("<a href='?city=" + city + "&page=2'>Page 2</a>" + br)
		.append("<a href='sub11'>Main Page</a>" + br)		
		.append("</body>")
		.append("</html>");		
		pw.print(builder);
	}
}
