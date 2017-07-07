<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="input" required="true" %>
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
%>
<%=encodeHtmlString(input)%>