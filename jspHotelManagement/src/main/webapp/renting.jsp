<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Renting - e-Hotels</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <!-- Video Background -->
 <div class="video-container">
        <video autoplay muted loop>
            <source src="videos/video.mp4" type="video/mp4">
            Your browser does not support the video tag.
        </video>
 </div>

    <header>
        <h1>Rent a Room</h1>
    </header>

    <section class="container">
        <h2>Confirm Renting</h2>
        <form action="RentingServlet" method="post">
            <label>Payment Status:</label>
            <select name="payment_status">
                <option value="Paid">Paid</option>
                <option value="Unpaid">Unpaid</option>
            </select>
            <button type="submit" class="btn">Confirm</button>
        </form>
    </section>
</body>
</html>
