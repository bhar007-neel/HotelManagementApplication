<%@ page import="java.util.List" %>
<%@ page import="com.hotel.dao.HotelDAO" %>
<%@ page import="com.hotel.model.Hotel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Hotels</title>
    <style>
        table {
            border-collapse: collapse;
            width: 95%;
            margin: auto;
        }
        th, td {
            border: 1px solid #333;
            padding: 10px;
            text-align: center;
        }
        h2, h3 {
            text-align: center;
        }
        form {
            margin-bottom: 20px;
        }
        .back-btn {
            display: block;
            text-align: center;
            margin: 20px;
        }
        input[type="text"], input[type="number"], input[type="email"] {
            padding: 5px;
            margin: 5px;
            width: 200px;
        }
    </style>
</head>
<body>

<h2>Manage Hotels</h2>

<h3>Add New Hotel</h3>
<form method="post" action="hotel" style="width: 60%; margin: auto;">
    <input type="hidden" name="action" value="add" />
    <label>Chain ID: <input type="number" name="chainID" required /></label><br/><br/>
    <label>Stars: <input type="number" name="stars" required /></label><br/><br/>
    <label>Number of Rooms: <input type="number" name="numberOfRooms" required /></label><br/><br/>
    <label>Address: <input type="text" name="address" required /></label><br/><br/>
    <label>Email: <input type="email" name="email" required /></label><br/><br/>
    <label>Phone Number: <input type="text" name="phoneNumber" required /></label><br/><br/>
    <input type="submit" value="Add Hotel" />
</form>

<hr style="width:80%; margin: 30px auto;" />

<%
    HotelDAO dao = new HotelDAO();
    List<Hotel> hotels = dao.getAllHotels();
%>

<table>
    <tr>
        <th>ID</th>
        <th>Stars</th>
        <th>Rooms</th>
        <th>Address</th>
        <th>Email</th>
        <th>Phone</th>
        <th>Actions</th>
    </tr>
    <% for (Hotel hotel : hotels) { %>
        <tr>
            <td><%= hotel.getHotelID() %></td>
            <td><%= hotel.getStars() %></td>
            <td><%= hotel.getNumberOfRoom() %></td>
            <td><%= hotel.getAddress() %></td>
            <td><%= hotel.getEmail() %></td>
            <td><%= hotel.getPhoneNumber() %></td>
            <td>
                <!-- Delete button -->
                <form method="post" action="hotel" style="display:inline;">
                    <input type="hidden" name="action" value="delete"/>
                    <input type="hidden" name="hotelID" value="<%= hotel.getHotelID() %>"/>
                    <input type="submit" value="Delete"
                           onclick="return confirm('Delete this hotel and all its rooms?')"/>
                </form>
                <br/><br/>
                <!-- Update form -->
                <form method="post" action="hotel">
                    <input type="hidden" name="action" value="update"/>
                    <input type="hidden" name="hotelID" value="<%= hotel.getHotelID() %>"/>
                    <input type="number" name="stars" placeholder="Stars" />
                    <input type="number" name="numberofroom" placeholder="Rooms" />
                    <input type="email" name="email" placeholder="Email" />
                    <input type="text" name="phoneNumber" placeholder="Phone" />
                    <input type="submit" value="Update" />
                </form>
            </td>
        </tr>
    <% } %>
</table>

<div class="back-btn">
    <a href="admin_dashboard.jsp">&larr; Back to Admin Dashboard</a>
</div>

</body>
</html>
