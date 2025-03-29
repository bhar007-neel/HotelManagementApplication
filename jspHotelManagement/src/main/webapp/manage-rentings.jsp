<%@ page import="com.hotel.model.Renting" %>
<%@ page import="java.util.List" %>
<%@ page session="true" %>

<%
    Boolean isAdmin = (Boolean) session.getAttribute("admin");
    if (isAdmin == null || !isAdmin) {
        response.sendRedirect("admin-login.jsp");
        return;
    }

    List<Renting> rentings = (List<Renting>) request.getAttribute("rentings");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Rentings</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            margin: 0;
            padding: 0;
            background: #f4f4f4;
        }

        .container {
            padding: 40px;
        }

        h2 {
            text-align: center;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 30px;
            background: white;
        }

        th, td {
            padding: 12px;
            border: 1px solid #ccc;
            text-align: center;
        }

        th {
            background: #e0e0e0;
        }

        button {
            padding: 8px 16px;
            border: none;
            background-color: #3498db;
            color: white;
            border-radius: 5px;
            cursor: pointer;
        }

        button:hover {
            background-color: #2980b9;
        }

        .back-link {
            display: block;
            margin-top: 20px;
            text-align: center;
        }

        .back-link a {
            color: #333;
            text-decoration: none;
            font-weight: bold;
        }

        .back-link a:hover {
            text-decoration: underline;
        }

        .action-buttons {
            display: flex;
            justify-content: center;
            gap: 10px;
        }

        .paid-label {
            font-weight: bold;
            color: green;
        }

        .unpaid-label {
            font-weight: bold;
            color: red;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Manage Rentings</h2>

        <table>
            <tr>
                <th>Renting ID</th>
                <th>Booking ID</th>
                <th>Payment Status</th>
                <th>Action</th>
            </tr>

            <% for (Renting renting : rentings) { %>
            <tr>
                <td><%= renting.getRentingId() %></td>
                <td><%= renting.getBookingId() %></td>
                <td>
                    <span class="<%= renting.getPaymentStatus().equals("Paid") ? "paid-label" : "unpaid-label" %>">
                        <%= renting.getPaymentStatus() %>
                    </span>
                </td>
                <td class="action-buttons">
                    <% if (!"Paid".equalsIgnoreCase(renting.getPaymentStatus())) { %>
                    <form method="get" action="ConfirmPayment.jsp">
                        <input type="hidden" name="rentingId" value="<%= renting.getRentingId() %>" />
                        <button type="submit">Confirm Payment</button>
                    </form>
                    <% } else { %>
                        <span>✓ Paid</span>
                    <% } %>
                </td>
            </tr>
            <% } %>
        </table>

        <div class="back-link">
            <a href="admin.jsp">← Back to Admin Home</a>
        </div>
    </div>
</body>
</html>
