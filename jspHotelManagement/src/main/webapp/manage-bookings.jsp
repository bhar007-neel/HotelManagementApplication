<%@ page import="java.util.List" %>
<%@ page import="com.hotel.model.Booking" %>
<%@ page session="true" %>

<%
    Boolean isAdmin = (Boolean) session.getAttribute("admin");
    if (isAdmin == null || !isAdmin) {
        response.sendRedirect("admin-login.jsp");
        return;
    }

    List<Booking> bookings = (List<Booking>) request.getAttribute("bookings");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Bookings</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container">
        <h2>Manage Bookings</h2>

        <table border="1">
            <tr>
                <th>Booking ID</th>
                <th>Customer ID</th>
                <th>Room ID</th>
                <th>Start</th>
                <th>End</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            <% for (Booking booking : bookings) { %>
            <tr>
                <form method="post" action="AdminBookingServlet">
                    <td><%= booking.getBookingId() %></td>
                    <td>
                        <%= booking.getCustomerName() %> <br/>
                        (<%= booking.getIdType() %>: <%= booking.getIdNumber() %>)
                      </td>
                    <td><%= booking.getRoomId() %></td>
                    <td><%= booking.getStartDate() %></td>
                    <td><%= booking.getEndDate() %></td>
                    <td><%= booking.getStatus() %></td>
                    <td>
                        <input type="hidden" name="bookingId" value="<%= booking.getBookingId() %>"/>
                        <% if (!"Confirmed".equals(booking.getStatus())) { %>
                            <button type="submit" name="action" value="confirm">Confirm & Rent</button>
                            <button type="submit" name="action" value="reject" onclick="return confirm('Are you sure you want to reject this booking? This will delete it.')">Reject</button>
                        <% } %>
                    </td>   
                </form>
            </tr>
            <% } %>
        </table>

        <a href="admin.jsp">Back to Admin Home</a>
    </div>
</body>
</html>
