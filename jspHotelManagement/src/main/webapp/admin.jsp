<%@ page import="jakarta.servlet.http.HttpSession" %>

<%
    HttpSession adminSession = request.getSession(false);
    if (adminSession == null || adminSession.getAttribute("admin") == null) {
        response.sendRedirect("admin-login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <style>
        body, html {
            margin: 0;
            padding: 0;
            height: 100%;
            font-family: 'Segoe UI', sans-serif;
            color: black;
            overflow: hidden;
        }

        .video-container {
            position: fixed;
            top: 0;
            left: 0;
            min-width: 100%;
            min-height: 100%;
            z-index: -1;
        }

        .video-container video {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .dashboard-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            text-align: center;
            height: 100vh;
            padding: 40px;
            background: rgba(255, 255, 255, 0.7);
            margin: 0 auto;
            max-width: 600px;
            border-radius: 20px;
            box-shadow: 0 0 20px rgba(0,0,0,0.3);
            z-index: 1;
            position: relative;
        }

        h2 {
            font-size: 2.5rem;
            margin-bottom: 10px;
        }

        p {
            font-size: 1.1rem;
            margin: 10px 0;
        }

        a {
            display: inline-block;
            margin: 8px 0;
            text-decoration: none;
            font-weight: bold;
            background: #eee;
            color: #222;
            padding: 10px 20px;
            border-radius: 8px;
            transition: background 0.3s;
        }

        a:hover {
            background: #ddd;
        }
    </style>
</head>
<body>
    <div class="video-container">
        <video autoplay muted loop>
            <source src="videos/video.mp4" type="video/mp4">
            Your browser does not support the video tag.
        </video>
    </div>

    <div class="dashboard-container">
        <h2>Welcome, Admin</h2>
        <p>You can now manage hotel information.</p>

        <a href="AdminCustomerServlet">Manage Customers</a><br>
        <a href="AdminBookingServlet">Manage Bookings</a><br>
        <a href="employee-edit.jsp">Manage Employees</a><br>
        <a href="dateselection.jsp">Book a Room</a><br><br>
        <a href="logout.jsp">Logout</a>
    </div>
</body>
</html>
