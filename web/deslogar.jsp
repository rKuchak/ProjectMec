<%
    request.getSession(false).invalidate();
    response.sendRedirect("index.jsp");
%>