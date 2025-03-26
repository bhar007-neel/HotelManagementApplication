<%@ page import="jakarta.servlet.http.HttpSession" %>

<%
    HttpSession adminSession = request.getSession(false);
    if (adminSession == null || adminSession.getAttribute("admin") == null) {
        response.sendRedirect("admin-login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
</head>
<body>

    <h2>Welcome, Admin</h2>
    <p>You can now manage hotel information.</p>

    <!--  Link to Manage Customers -->
    <p><a href="AdminCustomerServlet">Manage Customers</a></p>

    <!--  Link to Employee Edit Page -->
    <p><a href="employee-edit.jsp">Manage Employees</a></p>

     <p><a href="dateselection.jsp">Book a room</a></p>

    <!--  Logout -->
    <br><br>
    <a href="logout.jsp">Logout</a>

</body>
</html>
