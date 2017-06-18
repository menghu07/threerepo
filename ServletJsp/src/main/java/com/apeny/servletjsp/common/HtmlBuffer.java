package com.apeny.servletjsp.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * 生成Html文本
 * @author ahu
 *
 */
public class HtmlBuffer {
	
	private String title = "";
	private String style = "body {text-align:center;}"
			+ " table {/*水平居中*/ margin:0 auto; font-size:small; background:NavajoWhite}";
	private String script = "";
	private String headExcape = "";
	private String body = "";
	
	public HtmlBuffer(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}

	public void appendTitle(String title) {
		this.title += title;
	}

	public String getStyle() {
		return style;
	}

	public void appendStyle(String style) {
		this.style += style;
	}

	public String getScript() {
		return script;
	}

	public void appendScript(String script) {
		this.script += script;
	}

	public String getHeadExcape() {
		return headExcape;
	}

	public void appendHeadExcape(String headExcape) {
		this.headExcape += headExcape;
	}

	public String getBody() {
		return body;
	}

	public void appendBody(String body) {
		this.body += body;
	}

	@Override
	public String toString() {
		String html = "<html><head><title>" + title + "</title><script>" + script + "</script><style>"
				+ style + "</style></head><body>" + body + "</body></html>";
		return html;
	}
	
	/**
	 * 输出到客户端
	 * @param resp
	 * @throws IOException
	 */
	public void output(HttpServletResponse resp) throws IOException {
		if (resp == null) {
			return;
		}
		PrintWriter pw = resp.getWriter();
		pw.print(this);
		pw.flush();
		pw.close();
	}
}
