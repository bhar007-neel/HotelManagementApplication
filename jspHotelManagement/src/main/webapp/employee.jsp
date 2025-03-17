<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.hotel.model.Employee" %>
<%@ page import="com.hotel.dao.EmployeeDAO" %>
<!DOCTYPE html>
<html>
<head>
    <title>Employee Management</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>

<header>
    <h1>Employee Management</h1>
    <nav>
        <a href="index.jsp">Home</a> |
        <a href="booking.jsp">Booking</a> |
        <a href="renting.jsp">Renting</a>
    </nav>
</header>

<section class="container">
    <h2>Employee List</h2>

    <table border="1">
        <tr>
            <th>Employee ID</th>
            <th>Name</th>
            <th>Address</th>
            <th>SSN</th>
            <th>Role</th>
            <th>Action</th>
        </tr>

        <%
            EmployeeDAO employeeDAO = new EmployeeDAO();
            List<Employee> employees = employeeDAO.getAllEmployees();

            for (Employee emp : employees) {
        %>
        <tr>
            <td><%= emp.getEmployeeId() %></td>
            <td><%= emp.getName() %></td>
            <td><%= emp.getAddress() %></td>
            <td><%= emp.getSsn() %></td>
            <td><%= emp.getRole() %></td>
            <td>
                <form action="EmployeeServlet" method="post">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="employeeId" value="<%= emp.getEmployeeId() %>">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
        <% } %>
    </table>

    <h2>Add Employee</h2>
    <form action="EmployeeServlet" method="post">
        <input type="hidden" name="action" value="add">
        <label>Name:</label> <input type="text" name="name" required><br>
        <label>Address:</label> <input type="text" name="address" required><br>
        <label>SSN:</label> <input type="text" name="ssn" required pattern="\d{9}" title="Enter 9-digit SSN"><br>
        <label>Role:</label>
        <select name="role">
            <option value="Manager">Manager</option>
            <option value="Receptionist">Receptionist</option>
            <option value="Housekeeping">Housekeeping</option>
            <option value="Chef">Chef</option>
            <option value="Concierge">Concierge</option>
        </select><br>
        <button type="submit">Add Employee</button>
    </form>

</section>

</body>
</html>
