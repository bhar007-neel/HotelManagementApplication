<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String roomId = request.getParameter("roomId") != null ? request.getParameter("roomId") : "";
    String startDate = request.getParameter("startDate") != null ? request.getParameter("startDate") : "";
    String endDate = request.getParameter("endDate") != null ? request.getParameter("endDate") : "";
    String error = (String) request.getAttribute("error");
%>
<html>
<head>
    <title>Booking Form</title>
    <link rel="stylesheet" href="style.css">
    <style>
        body {
            font-family: "Segoe UI", sans-serif;
            background-color: #f2f5f7;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 500px;
            margin: 80px auto;
            padding: 30px;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.15);
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
            color: #333;
        }

        label {
            display: block;
            margin-top: 15px;
            margin-bottom: 5px;
            font-weight: bold;
            color: #444;
        }

        input[type="text"],
        select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
            box-sizing: border-box;
        }

        button {
            margin-top: 25px;
            width: 100%;
            padding: 12px;
            background-color: #007bff;
            color: white;
            font-size: 16px;
            font-weight: bold;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }

        button:hover {
            background-color: #0056b3;
        }

        p {
            color: red;
            text-align: center;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Complete Your Booking</h2>

    <% if (error != null && !error.isEmpty()) { %>
        <p><%= error %></p>
    <% } %>

    <form action="booking" method="post">
        <input type="hidden" name="roomId" value="<%= roomId %>"/>
        <input type="hidden" name="startDate" value="<%= startDate %>"/>
        <input type="hidden" name="endDate" value="<%= endDate %>"/>

        <label>Name:</label>
        <input type="text" name="name" required/>

        <label>Address:</label>
        <input type="text" name="address" required/>

        <label>ID Type:</label>
        <select name="idType" required>
            <option value="">-- Select --</option>
            <option value="SSN">SSN</option>
            <option value="SIN">SIN</option>
            <option value="Driver's License">Driver's License</option>
        </select>

        <label>ID Number:</label>
        <input type="text" name="idNumber" required/>

        <button type="submit">Confirm Booking</button>
    </form>
</div>
</body>
</html>
