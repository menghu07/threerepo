package com.apeny.servletjsp.tags;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTag;

public class SimpleTagImpl implements SimpleTag {
	private JspContext context;
	private JspFragment body;
	private JspTag parent;
	
	
	@Override
	public void doTag() throws JspException, IOException {
		System.out.println("doTag simple tag is called");
		context.getOut().write("SimpleTagImpl is output");
	}

	@Override
	public void setParent(JspTag parent) {
		System.out.println("parent jsp tag");
		this.parent = parent;
	}

	@Override
	public JspTag getParent() {
		return this.parent;
	}

	@Override
	public void setJspContext(JspContext pc) {
		System.out.println("set context is called");
		this.context = pc;
	}

	@Override
	public void setJspBody(JspFragment jspBody) {
		System.out.println("set body is called");
		this.body = jspBody;
	}
	
	protected JspFragment getBody() {
		return this.body;
	}
	
	protected JspContext getJspContext() {
		return this.context;
	}
}
