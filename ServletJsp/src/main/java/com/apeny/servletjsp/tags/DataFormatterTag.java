package com.apeny.servletjsp.tags;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class DataFormatterTag extends SimpleTagSupport {
	private String head1;
	private String head2;
	private String items;
	private String[] flowers = {"chrysanthemum", "ross", "lily"};
	
	@Override
	public void doTag() throws IOException, JspException {
		JspContext context = getJspContext();
		JspWriter out = context.getOut();
		out.println("<table style='border:1px solid green'>");
		out.println("<tr style='font-weight:bold'><td>" + head1 + "</td><td>"+  head2 +"</td></tr>");
		StringTokenizer tokenizer = new StringTokenizer(items, ",");
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			String[] spi = token.split(":");
			out.println("<tr style='background:cyan'><td>" + spi[0] + "</td><td>"+  spi[1] +"</td></tr>");			
		}
		out.println("</table>");
		out.println("<select>");
		//调用多次body
		for (String flower : flowers) {
			context.setAttribute("name", flower);
			context.setAttribute("value", flower);
			getJspBody().invoke(out);
		}
		out.println("</select>");
	}
	
	public String getHead1() {
		return head1;
	}
	public void setHead1(String head1) {
		this.head1 = head1;
	}
	public String getHead2() {
		return head2;
	}
	public void setHead2(String head2) {
		this.head2 = head2;
	}
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
}
