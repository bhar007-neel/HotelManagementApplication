<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.hotel.model.Employee" %>
<%@ page import="com.hotel.dao.EmployeeDAO" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Employees</title>
    <style>
        body, html {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', sans-serif;
            color: black;
            height: 100%;
        }

        .video-container {
            position: fixed;
            top: 0;
            left: 0;
            min-width: 100%;
            min-height: 100%;
            z-index: -1;
            overflow: hidden;
        }

        .video-container video {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .container {
            padding: 40px;
            backdrop-filter: blur(6px);
            min-height: 100vh;
        }

        h2 {
            text-align: center;
            text-shadow: 2px 2px 4px #000;
        }

        form {
            background: rgba(255, 255, 255, 0.1);
            padding: 25px;
            border-radius: 15px;
            max-width: 600px;
            margin: 20px auto;
            display: flex;
            flex-direction: column;
            gap: 12px;
        }

        input, select, button {
            padding: 10px;
            border-radius: 5px;
            border: none;
            font-size: 1rem;
        }

        button {
            background-color: #ffffffcc;
            color: black;
            font-weight: bold;
            cursor: pointer;
        }

        button:hover {
            background-color: white;
        }

        .inline-buttons {
            text-align: center;
            margin: 10px auto;
        }

        .inline-buttons form {
            display: inline-block;
        }

        .message {
            text-align: center;
            font-size: 1.1rem;
            margin: 10px 0;
        }

        .message.green { color: lightgreen; }
        .message.red { color: #ff5c5c; }

        table {
            width: 100%;
            margin-top: 20px;
            border-collapse: collapse;
            background: rgba(255, 255, 255, 0.1);
        }

        th, td {
            border: 1px solid white;
            padding: 10px;
            text-align: center;
        }

        th {
            background-color: rgba(255, 255, 255, 0.2);
        }
    </style>
</head>
<body>
<div class="video-container">
    <video autoplay muted loop>
        <source src="videos/video.mp4" type="video/mp4">
        Your browser does not support the video tag.
    </video>
</div>

<div class="container">
    <h2>Search Employee by SSN</h2>
    <form action="employee" method="get">
        <input type="hidden" name="action" value="searchBySSN">
        SSN: <input type="text" name="ssn" required />
        <button type="submit">Search</button>
    </form>

    <div class="inline-buttons">
        <form action="employee-edit.jsp" method="get">
            <input type="hidden" name="showAddForm" value="true" />
            <button type="submit">Add New Employee</button>
        </form>

        <form action="employee-edit.jsp" method="get">
            <input type="hidden" name="showList" value="true" />
            <button type="submit">List All Employees</button>
        </form>
    </div>

    <%
        String error = (String) request.getAttribute("error");
        String message = (String) request.getAttribute("message");

        if (message != null) {
    %>
        <p class="message green"><%= message %></p>
    <%
        }

        if (error != null) {
    %>
        <p class="message red"><%= error %></p>
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
        <input type="text" name="name" value="<%= emp.getName() %>" required placeholder="Name">
        <input type="text" name="address" value="<%= emp.getAddress() %>" required placeholder="Address">
        <input type="text" name="ssn" value="<%= emp.getSsn() %>" required placeholder="SSN">
        <input type="text" name="role" value="<%= emp.getRole() %>" required placeholder="Role">
        <button type="submit">Update</button>
    </form>

    <form action="employee" method="post">
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
        <input type="number" name="hotelId" required placeholder="Hotel ID">
        <input type="text" name="name" required placeholder="Name">
        <input type="text" name="address" required placeholder="Address">
        <input type="text" name="ssn" value="<%= request.getParameter("ssn") != null ? request.getParameter("ssn") : "" %>" required placeholder="SSN">
        <input type="text" name="role" required placeholder="Role">
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
    <table>
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
    <p class="message">No employees found.</p>
    <%
            }
        }
    %>
</div>
</body>
</html>
