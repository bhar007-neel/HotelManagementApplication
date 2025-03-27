<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>e-Hotels Management</title>
    <link rel="stylesheet" href="style.css">
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
            overflow: hidden;
        }

        .video-container video {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .center-content {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            text-align: center;
            backdrop-filter: blur(4px);
        }

        .center-content h1 {
            font-size: 3rem;
            margin-bottom: 30px;
            text-shadow: 2px 2px 4px #000;
        }

        .options {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        .options a {
            padding: 12px 30px;
            font-size: 1.2rem;
            background: rgba(255, 255, 255, 0.1);
            color: white;
            border: 2px solid white;
            text-decoration: none;
            border-radius: 25px;
            transition: all 0.3s ease;
        }

        .options a:hover {
            background-color: white;
            color: #333;
        }

        footer {
            position: absolute;
            bottom: 15px;
            width: 100%;
            text-align: center;
            color: white;
            font-size: 0.9rem;
            text-shadow: 1px 1px 2px #000;
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

    <div class="center-content">
        <h1>Welcome to e-Hotels</h1>
        <div class="options">
            <a href="dateselection.jsp">Booking</a>
            <a href="renting.jsp">Renting</a>
            <a href="employee.jsp">Employees</a>
            <a href="admin-login.jsp">Admin</a>
        </div>
    </div>

    <footer>
        <p>&copy; 2025 e-Hotels. All rights reserved.</p>
    </footer>
</body>
</html>
