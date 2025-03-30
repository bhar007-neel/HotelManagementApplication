<%@ page import="java.util.List" %>
<%@ page import="com.hotel.dao.RoomDAO" %>
<%@ page import="com.hotel.model.Room" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Rooms</title>
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
        input[type="text"], input[type="number"] {
            padding: 5px;
            margin: 5px;
            width: 180px;
        }
    </style>
</head>
<body>

<h2>Manage Rooms</h2>

<h3>Add New Room</h3>
<form method="post" action="room" style="width: 60%; margin: auto;">
    <input type="hidden" name="action" value="add" />
    <label>Hotel ID: <input type="number" name="hotelID" required /></label><br/><br/>
    <label>Price: <input type="number" step="0.01" name="price" required /></label><br/><br/>
    <label>Room Type: <input type="text" name="roomType" required /></label><br/><br/>
    <label>Damage: <input type="text" name="damage" /></label><br/><br/>
    <label>View: <input type="text" name="view" required /></label><br/><br/>
    <label>Extendable: <input type="checkbox" name="extendable" value="true" /></label><br/><br/>
    <input type="submit" value="Add Room" />
</form>

<hr style="width:80%; margin: 30px auto;" />

<%
    RoomDAO dao = new RoomDAO();
    List<Room> rooms = dao.filterRoomsWithAvailability(null, null, null, null, null, null, null, null);
%>

<table>
    <tr>
        <th>Room ID</th>
        <th>Hotel ID</th>
        <th>Price</th>
        <th>Type</th>
        <th>Damage</th>
        <th>View</th>
        <th>Extendable</th>
        <th>Actions</th>
    </tr>
    <% for (Room room : rooms) { %>
        <tr>
            <td><%= room.getRoomID() %></td>
            <td><%= room.getHotelID() %></td>
            <td><%= room.getPrice() %></td>
            <td><%= room.getRoomType() %></td>
            <td><%= room.getDamage() != null ? room.getDamage() : "None" %></td>
            <td><%= room.getView() %></td>
            <td><%= room.getExtendable() ? "Yes" : "No" %></td>
            <td>
                <form method="post" action="room" style="display:inline;">
                    <input type="hidden" name="action" value="delete"/>
                    <input type="hidden" name="roomID" value="<%= room.getRoomID() %>"/>
                    <input type="submit" value="Delete" onclick="return confirm('Delete this room?')"/>
                </form>
                <br/><br/>
                <form method="post" action="room">
                    <input type="hidden" name="action" value="updatePrice"/>
                    <input type="hidden" name="roomID" value="<%= room.getRoomID() %>"/>
                    <input type="number" step="0.01" name="newPrice" placeholder="New Price" />
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
