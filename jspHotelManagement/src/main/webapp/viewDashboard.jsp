<%@ page import="java.util.*, com.hotel.model.*" %>
<html>
<head><title>Hotel View Dashboard</title></head>
<body>
<h2>Available Rooms Per Area</h2>
<table border="1">
    <tr><th>Hotel Address</th><th>Available Rooms</th></tr>
    <%
        List<AvailableRooms> availableRooms = (List<AvailableRooms>) request.getAttribute("availableRooms");
        for (AvailableRooms room : availableRooms) {
    %>
    <tr>
        <td><%= room.getHotelAddress() %></td>
        <td><%= room.getAvailableRoomCount() %></td>
    </tr>
    <% } %>
</table>

<h2>Total Room Capacity Per Hotel</h2>
<table border="1">
    <tr><th>Hotel Address</th><th>Total Capacity</th></tr>
    <%
        List<RoomCapacity> capacities = (List<RoomCapacity>) request.getAttribute("roomCapacities");
        for (RoomCapacity rc : capacities) {
    %>
    <tr>
        <td><%= rc.getHotelAddress() %></td>
        <td><%= rc.getTotalCapacity() %></td>
    </tr>
    <% } %>
</table>
</body>
</html>
