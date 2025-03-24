<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.hotel.model.Room" %>
<%@ page import="java.util.List" %>
<%
    String startDate = (String) request.getAttribute("startDate");
    String endDate = (String) request.getAttribute("endDate");
%>
<html>
<head>
    <title>Room Results</title>
    <link rel="stylesheet" href="style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .container {
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 40px;
        }
        table {
            border-collapse: collapse;
            width: 90%;
            max-width: 1000px;
            text-align: center;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            border: 1px solid #999;
        }
        th {
            background-color: #f2f2f2;
        }
        .btn-back {
            margin-top: 30px;
            display: inline-block;
            text-decoration: none;
            color: #000;
            background: #ddd;
            padding: 8px 16px;
            border-radius: 4px;
        }
        .btn-back:hover {
            background-color: #bbb;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Available Rooms</h2>

    <%
        List<Room> rooms = (List<Room>) request.getAttribute("availableRooms");
        if (rooms != null && !rooms.isEmpty()) {
    %>
    <table>
        <tr>
            <th>Hotel Chain Name</th>
            <th>Hotel Address</th>
            <th>Price</th>
            <th>Room Type</th>
            <th>View</th>
            <th>Extendable</th>
            <th>Action</th>
        </tr>
        <%
            for (Room room : rooms) {
        %>
        <tr>
            <td><%= room.getChainName() %></td>
            <td><%= room.getHotelAddress() %></td>
            <td><%= room.getPrice() %></td>
            <td><%= room.getRoomType() %></td>
            <td><%= room.getView() %></td>
            <td><%= room.getExtendable() ? "Yes" : "No" %></td>
            <td>
                <form action="bookingform" method="get">
                    <input type="hidden" name="roomId" value="<%= room.getRoomID() %>"/>
                    <input type="hidden" name="startDate" value="<%= startDate %>"/>
                    <input type="hidden" name="endDate" value="<%= endDate %>"/>
                    <input type="submit" value="Book"/>
                </form>
            </td>
        </tr>
        <%
            }
        %>
    </table>
    <%
        } else {
    %>
        <p>No rooms found for your search criteria.</p>
    <%
        }
    %>

    <a class="btn-back" href="roomsearch.jsp">🔙 Back to Search</a>
</div>
</body>
</html>
