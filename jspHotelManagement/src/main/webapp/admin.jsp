<%@ page import="jakarta.servlet.http.HttpSession" %>

<%
    HttpSession adminSession = request.getSession(false);
    if (adminSession == null || adminSession.getAttribute("admin") == null) {
        response.sendRedirect("admin-login.jsp");
        return;
    }
%>


<h2>Welcome, Admin</h2>
<p>You can now modify room features.</p>
<a href="update-room.jsp">Modify Room Features</a>
<a href="logout.jsp">Logout</a>
