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

<html>
<head><title>Manage Customers</title></head>
<body>
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
<hr>


<table border="1">
    <tr>
        <th>ID</th><th>Name</th><th>Address</th><th>ID Type</th><th>ID Number</th><th>Registered</th><th>Actions</th>
    </tr>

    <% for (Customer customer : customers) { %>
    <tr>
        <form method="post" action="AdminCustomerServlet">
            <td><%= customer.getCustomerId() %></td>
            <td><input type="text" name="name" value="<%= customer.getName() %>"/></td>
            <td><input type="text" name="address" value="<%= customer.getAddress() %>"/></td>
            <td><%= customer.getIdType() %></td>
            <td><%= customer.getIdNumber() %></td>
            <td><%= customer.getRegistrationDate() %></td>
            <td>
                <input type="hidden" name="customerId" value="<%= customer.getCustomerId() %>"/>
                <button type="submit" name="action" value="update">Update</button>
                <button type="submit" name="action" value="delete" onclick="return confirm('Are you sure?')">Delete</button>
            </td>


        </form>
    </tr>
    <% } %>
</table>

<a href="admin.jsp">Back to Admin Home</a>
</body>
</html>
