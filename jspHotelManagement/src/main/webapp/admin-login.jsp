<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Login</title>
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

        .login-container {
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            flex-direction: column;
            backdrop-filter: blur(5px);
        }

        .login-container h2 {
            margin-bottom: 25px;
            text-shadow: 2px 2px 4px #000;
        }

        form {
            background: rgba(255, 255, 255, 0.1);
            padding: 30px 40px;
            border-radius: 15px;
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        label {
            font-size: 1.1rem;
            text-shadow: 1px 1px 2px #000;
        }

        input {
            padding: 10px;
            border-radius: 5px;
            border: none;
            outline: none;
            font-size: 1rem;
        }

        button {
            padding: 10px;
            border-radius: 5px;
            border: none;
            background-color: #ffffffcc;
            color: black;
            font-size: 1rem;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        button:hover {
            background-color: black;
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

    <div class="login-container">
        <h2>Admin Login</h2>
        <form action="AdminLoginServlet" method="post">
            <label>Username:</label>
            <input type="text" name="username" required>

            <label>Password:</label>
            <input type="password" name="password" required>

            <button type="submit">Login</button>
        </form>
    </div>
</body>
</html>

