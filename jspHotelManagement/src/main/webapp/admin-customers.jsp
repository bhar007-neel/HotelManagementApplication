<%@ page import="com.hotel.model.Customer" %>
<%@ page import="java.util.List" %>
<%@ page session="true" %>

<%
    Boolean isAdmin = (Boolean) session.getAttribute("admin");
    if (isAdmin == null || !isAdmin) {
        response.sendRedirect("admin-login.jsp");
        return;
    }

    List<Customer> customers = (List<Customer>) request.getAttribute("customers");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Customers</title>
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

        h2, h3 {
            text-align: center;
            text-shadow: 1px 1px 3px #000;
        }

        form {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 10px;
            margin-bottom: 30px;
        }

        input, select, button {
            padding: 10px;
            border-radius: 5px;
            border: none;
            outline: none;
            font-size: 1rem;
        }

        button {
            background-color: #ffffffaa;
            color: #000;
            font-weight: bold;
            cursor: pointer;
        }

        button:hover {
            background-color: white;
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

        a {
            display: block;
            margin-top: 30px;
            text-align: center;
            color: #fff;
            text-decoration: underline;
        }

        a:hover {
            color: #ccc;
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
        <h2>Admin Panel: Manage Customers</h2>

        <h3>Add New Customer</h3>
        <form method="post" action="AdminCustomerServlet">
            <input type="text" name="name" placeholder="Name" required />
            <input type="text" name="address" placeholder="Address" required />
            <select name="idType" required>
                <option value="">Select ID Type</option>
                <option value="SSN">SSN</option>
                <option value="SIN">SIN</option>
                <option value="Driver's License">Driver's License</option>
            </select>
            <input type="text" name="idNumber" placeholder="ID Number" required />
            <input type="hidden" name="action" value="add" />
            <button type="submit">Add Customer</button>
        </form>

        <table>
            <tr>
                <th>ID</th><th>Name</th><th>Address</th><th>ID Type</th><th>ID Number</th><th>Registered</th><th>Actions</th>
            </tr>
            <% for (Customer customer : customers) { %>
            <tr>
                <form method="post" action="AdminCustomerServlet">
                    <td><%= customer.getCustomerId() %></td>
                    <td><input type="text" name="name" value="<%= customer.getName() %>" /></td>
                    <td><input type="text" name="address" value="<%= customer.getAddress() %>" /></td>
                    <td><%= customer.getIdType() %></td>
                    <td><%= customer.getIdNumber() %></td>
                    <td><%= customer.getRegistrationDate() %></td>
                    <td>
                        <input type="hidden" name="customerId" value="<%= customer.getCustomerId() %>" />
                        <button type="submit" name="action" value="update">Update</button>
                        <button type="submit" name="action" value="delete" onclick="return confirm('Are you sure?')">Delete</button>
                    </td>
                </form>
            </tr>
            <% } %>
        </table>

        <a href="admin.jsp">Back to Admin Home</a>
    </div>
</body>
</html>
