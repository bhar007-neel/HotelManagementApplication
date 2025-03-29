<%@ page import="com.hotel.dao.rentingDao" %>
<%@ page import="com.hotel.model.Renting" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    int rentingId = Integer.parseInt(request.getParameter("rentingId"));
    Renting renting = new rentingDao().getRentingById(rentingId);
%>

<!DOCTYPE html>
<html>
<head>
    <title>Confirm Payment</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            padding: 40px;
            background-color: #f5f5f5;
        }
        form {
            background: white;
            padding: 30px;
            max-width: 400px;
            margin: auto;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0,0,0,0.2);
        }
        h2 {
            text-align: center;
        }
        label {
            display: block;
            margin-top: 15px;
            font-weight: bold;
        }
        input, select {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        button {
            margin-top: 20px;
            padding: 10px;
            width: 100%;
            background-color: #3498db;
            color: white;
            font-weight: bold;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        button:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>

    <form action="ConfirmPaymentServlet" method="post">
        <h2>Confirm Payment</h2>

        <input type="hidden" name="rentingId" value="<%= renting.getRentingId() %>">

        <label>Amount:</label>
        <input type="text" name="amount" required>

        <label>Payment Date:</label>
        <input type="date" name="paymentDate" required>

        <label>Payment Method:</label>
        <select name="paymentMethod" required>
            <option value="">--Select--</option>
            <option value="Credit Card">Credit Card</option>
            <option value="PayPal">PayPal</option>
            <option value="Debit Card">Debit Card</option>
            <option value="Cash">Cash</option>
        </select>

        <button type="submit">Submit Payment</button>
    </form>

</body>
</html>
