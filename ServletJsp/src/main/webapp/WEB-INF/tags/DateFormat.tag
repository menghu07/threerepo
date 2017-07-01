<%@ tag import="java.util.Date,java.text.DateFormat" pageEncoding="UTF-8" %>
<% 
	DateFormat format = DateFormat.getDateInstance(DateFormat.LONG);
	out.println(format.format(new Date()) + "汉字");
%>
<p>中文</p>
<%@ include file="../pages/htmls/IncludedTagHtml.html" %>
<%@ include file="tagfs/IncludedTagf.tagf" %>
