<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.hotel.model.Employee" %>
<%@ page import="com.hotel.dao.EmployeeDAO" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <title>Manage Employees</title>
</head>
<body>

<h2>Search Employee by SSN</h2>
<form action="employee" method="get">
    <input type="hidden" name="action" value="searchBySSN">
    SSN: <input type="text" name="ssn" required />
    <button type="submit">Search</button>
</form>

<!-- Buttons for Add and List -->
<form action="employee-edit.jsp" method="get" style="display:inline;">
    <input type="hidden" name="showAddForm" value="true" />
    <button type="submit">Add New Employee</button>
</form>

<form action="employee-edit.jsp" method="get" style="display:inline;">
    <input type="hidden" name="showList" value="true" />
    <button type="submit">List All Employees</button>
</form>

<hr>

<%
    String error = (String) request.getAttribute("error");
    String message = (String) request.getAttribute("message");

    if (message != null) {
%>
    <p style="color:green;"><%= message %></p>
<%
    }

    if (error != null) {
%>
    <p style="color:red;"><%= error %></p>
<%
    }

    Employee emp = (Employee) request.getAttribute("employee");
    String showAddForm = request.getParameter("showAddForm");
    String showList = request.getParameter("showList");

    if (emp != null) {
%>

<h2>Update/Delete Employee</h2>
<form action="employee" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="employeeId" value="<%= emp.getEmployeeId() %>" />

    Name: <input type="text" name="name" value="<%= emp.getName() %>" required><br>
    Address: <input type="text" name="address" value="<%= emp.getAddress() %>" required><br>
    SSN: <input type="text" name="ssn" value="<%= emp.getSsn() %>" required><br>
    Role: <input type="text" name="role" value="<%= emp.getRole() %>" required><br>

    <button type="submit">Update</button>
</form>

<form action="employee" method="post" style="margin-top:10px;">
    <input type="hidden" name="action" value="delete">
    <input type="hidden" name="employeeId" value="<%= emp.getEmployeeId() %>" />
    <button type="submit" onclick="return confirm('Are you sure you want to delete this employee?')">Delete</button>
</form>

<%
    } else if ((request.getParameter("ssn") != null && error == null) || "true".equals(showAddForm)) {
%>

<h2>Add New Employee</h2>
<form action="employee" method="post">
    <input type="hidden" name="action" value="add">
    Hotel ID: <input type="number" name="hotelId" required><br>
    Name: <input type="text" name="name" required><br>
    Address: <input type="text" name="address" required><br>
    SSN: <input type="text" name="ssn" value="<%= request.getParameter("ssn") != null ? request.getParameter("ssn") : "" %>" required><br>
    Role: <input type="text" name="role" required><br>

    <button type="submit">Add Employee</button>
</form>

<%
    }

    if ("true".equals(showList)) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        List<Employee> employees = employeeDAO.getAllEmployees();

        if (!employees.isEmpty()) {
%>

<h2>All Employees</h2>
<table border="1">
    <tr>
        <th>ID</th><th>Name</th><th>Address</th><th>SSN</th><th>Role</th>
    </tr>
<%
        for (Employee e : employees) {
%>
    <tr>
        <td><%= e.getEmployeeId() %></td>
        <td><%= e.getName() %></td>
        <td><%= e.getAddress() %></td>
        <td><%= e.getSsn() %></td>
        <td><%= e.getRole() %></td>
    </tr>
<%
        }
%>
</table>
<%
        } else {
%>
<p>No employees found.</p>
<%
        }
    }
%>

</body>
</html>