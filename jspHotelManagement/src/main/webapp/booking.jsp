<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Booking - e-Hotels</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>

<header>
    <h1>Book a Room</h1>
</header>

<section class="container">
    <h2>Make a Booking</h2>
    <form action="BookingServlet" method="post">
        <label>Start Date:</label>
        <input type="date" name="start_date" required>

        <label>End Date:</label>
        <input type="date" name="end_date" required>

        <label>Status:</label>
        <select name="status">
            <option value="Confirmed">Confirmed</option>
            <option value="Pending">Pending</option>
            <option value="Cancelled">Cancelled</option>
        </select>

        <button type="submit" class="btn">Submit</button>
    </form>
</section>

</body>
</html>
