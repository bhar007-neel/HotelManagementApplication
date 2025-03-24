<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<html>
<head>
    <title>Room Search</title>
    <link rel="stylesheet" href="style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f2f5f7;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 600px;
            margin: 60px auto;
            padding: 30px;
            background-color: #fff;
            border-radius: 12px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
        }

        h2 {
            text-align: center;
            color: #333;
        }

        label {
            display: block;
            margin-top: 15px;
            font-weight: bold;
        }

        input[type="text"],
        input[type="number"],
        select {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border-radius: 6px;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        input[type="submit"] {
            margin-top: 25px;
            background-color: #007bff;
            color: white;
            padding: 12px;
            border: none;
            width: 100%;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }

        p {
            color: red;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Search for Available Rooms</h2>

    <!-- Error Message Display -->
    <%
        String error = (String) request.getAttribute("error");
        if (error != null && !error.isEmpty()) {
    %>
        <p><%= error %></p>
    <%
        }
    %>

    <form method="post" action="roomsearch">
        <input type="hidden" name="startDate" value="<%= request.getAttribute("startDate") != null ? request.getAttribute("startDate") : "" %>" />
        <input type="hidden" name="endDate" value="<%= request.getAttribute("endDate") != null ? request.getAttribute("endDate") : "" %>" />

        <!-- Hotel Chain Dropdown -->
        <label>Hotel Chain:</label>
        <select name="chainName">
            <option value="">Any</option>
            <%
                List<String> chainNames = (List<String>) request.getAttribute("chainNames");
                String selectedChain = request.getParameter("chainName");
                if (chainNames != null) {
                    for (String name : chainNames) {
            %>
                <option value="<%= name %>" <%= (name.equals(selectedChain) ? "selected" : "") %>><%= name %></option>
            <%
                    }
                }
            %>
        </select>

        <!-- Address -->
        <label>Address:</label>
        <input type="text" name="address" value="<%= Objects.toString(request.getAttribute("address"), "") %>">

        <!-- Price Range -->
        <label>Min Price:</label>
        <input type="number" name="minPrice" value="<%= Objects.toString(request.getAttribute("minPrice"), "") %>" step="0.01">

        <label>Max Price:</label>
        <input type="number" name="maxPrice" value="<%= Objects.toString(request.getAttribute("maxPrice"), "") %>" step="0.01">

        <!-- Room Type -->
        <label>Room Type:</label>
        <select name="roomType">
            <%
                String roomType = (String) request.getAttribute("roomType");
            %>
            <option value="">Any</option>
            <option value="Single" <%= "Single".equals(roomType) ? "selected" : "" %>>Single</option>
            <option value="Double" <%= "Double".equals(roomType) ? "selected" : "" %>>Double</option>
        </select>

        <!-- View -->
        <label>View:</label>
        <select name="view">
            <%
                String view = (String) request.getAttribute("view");
            %>
            <option value="">Any</option>
            <option value="Sea" <%= "Sea".equals(view) ? "selected" : "" %>>Sea</option>
            <option value="Mountain" <%= "Mountain".equals(view) ? "selected" : "" %>>Mountain</option>
            <option value="None" <%= "None".equals(view) ? "selected" : "" %>>None</option>
        </select>

        <!-- Submit -->
        <input type="submit" value="Search">
    </form>
</div>
</body>
</html>
