<!DOCTYPE html>
<html>
<head>
    <title>Select Booking Dates</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<div class="container">
    <h2>Select Your Booking Dates</h2>
    <form method="get" action="roomsearch">
        <label for="startDate">Start Date:</label>
        <input type="date" id="startDate" name="startDate" required><br>

        <label for="endDate">End Date:</label>
        <input type="date" id="endDate" name="endDate" required><br>

        <input type="submit" value="Search Available Rooms" class="btn">
    </form>
</div>
</body>
</html>
