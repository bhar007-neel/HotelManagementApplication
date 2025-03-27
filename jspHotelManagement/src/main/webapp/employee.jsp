<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.hotel.model.Employee" %>
<%@ page import="com.hotel.dao.EmployeeDAO" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Employee Management</title>
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

        h1, h2 {
            text-align: center;
            text-shadow: 2px 2px 4px #000;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background-color: rgba(255, 255, 255, 0.1);
            margin-top: 20px;
        }

        th, td {
            border: 1px solid white;
            padding: 10px;
            text-align: center;
        }

        th {
            background-color: rgba(255, 255, 255, 0.2);
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 12px;
            max-width: 500px;
            margin: 30px auto;
            background: rgba(255, 255, 255, 0.1);
            padding: 30px;
            border-radius: 15px;
        }

        label {
            font-weight: bold;
            text-shadow: 1px 1px 2px #000;
        }

        input, select, button {
            padding: 10px;
            font-size: 1rem;
            border-radius: 5px;
            border: none;
            outline: none;
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

        nav {
            text-align: center;
            margin-bottom: 20px;
        }

        nav a {
            color: white;
            text-decoration: none;
            margin: 0 15px;
            font-weight: bold;
        }

        nav a:hover {
            text-decoration: underline;
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
        <h1>Employee Management</h1>
        <nav>
            <a href="index.jsp">Home</a> |
            <a href="booking.jsp">Booking</a> |
            <a href="renting.jsp">Renting</a>
        </nav>

        <h2>Employee List</h2>

        <table>
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
            <label>Name:</label>
            <input type="text" name="name" required>
            <label>Address:</label>
            <input type="text" name="address" required>
            <label>SSN:</label>
            <input type="text" name="ssn" required pattern="\d{9}" title="Enter 9-digit SSN">
            <label>Role:</label>
            <select name="role">
                <option value="Manager">Manager</option>
                <option value="Receptionist">Receptionist</option>
                <option value="Housekeeping">Housekeeping</option>
                <option value="Chef">Chef</option>
                <option value="Concierge">Concierge</option>
            </select>
            <button type="submit">Add Employee</button>
        </form>
    </div>
</body>
</html>
