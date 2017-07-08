<%@ tag pageEncoding="UTF-8"
	import="java.text.DateFormat,java.util.Date,javax.servlet.jsp.tagext.JspFragment,java.io.IOException"%>
<%@ attribute name="input" required="true" fragment="true"%>
<%@ attribute name="tagsf" required="true"%>
<%! 
    public String encodeHtmlString(String tags) {
		if (tags == null) {
			return null;
		}
		int length = tags.length();
		StringBuilder builder = new StringBuilder(2 * length);
		for (int i = 0; i < length; i++) {
			char c = tags.charAt(i);
			if (c == '<') {
				builder.append("&lt;");
			} else if (c == '>') {
				builder.append("&gt;");
			} else if (c == '\'') {
				builder.append("&#39;");
			} else if (c == '"') {
				builder.append("&quot;");
			} else if (c == '&') {
				builder.append("&amp;");
			} else if (c == ' ') {
				builder.append("&nbsp;");
			} else {
				builder.append(c);
			}
		}
		return builder.toString();
	}

	public void output(JspFragment inputFrag) throws IOException, JspException {
		inputFrag.invoke(null);
		System.out.println("input frag" + inputFrag.getJspContext());
	}%>
<%
	output(input);
	System.out.println("tag implicit object: " + jspContext + " request: " + request);
%>
<%=encodeHtmlString(tagsf)%>
<p>
	页面级变量input ${pageScope.input}
	<%=out == jspContext.getOut()%></p>
<!-- 调用页面可以访问的变量 -->
<%@ variable name-given="longDate"%>
<%@ variable name-given="shortDate"%>
<p>tag EL implicit object: ${pageContext.request}
	pageContext:${pageContext} ${header['cookie']}</p>
<%
	Date now = new Date();
	DateFormat longFormat = DateFormat.getDateInstance(DateFormat.LONG);
	DateFormat smallFormat = DateFormat.getDateInstance(DateFormat.SHORT);
	jspContext.setAttribute("longDate", longFormat.format(now));
	jspContext.setAttribute("shortDate", smallFormat.format(now));
%>
<jsp:doBody />