package com.hotel.servlets;

import java.io.IOException;
import java.util.List;

import com.hotel.dao.bookingDao;
import com.hotel.dao.rentingDao;
import com.hotel.model.Booking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AdminBookingServlet")
public class AdminBookingServlet extends HttpServlet {

    private final bookingDao bookingDAO = new bookingDao();
    private final rentingDao rentingDAO = new rentingDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Booking> bookings = bookingDAO.getAllBookings();
        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("manage-bookings.jsp").forward(request, response);
    }

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String action = request.getParameter("action");

    try {
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));

        switch (action) {
            case "confirm":
                // First update the status to Confirmed (and archive)
                bookingDAO.updateStatus(bookingId, "Confirmed");

                // Then create renting
                boolean created = rentingDAO.createFromBooking(bookingId);
                if (!created) {
                    request.setAttribute("error", "⚠️ Booking is already confirmed or renting exists.");
                }
                break;

            case "cancel":
                // Cancel and archive
                bookingDAO.updateStatus(bookingId, "Cancelled");
                break;

                case "reject":
                bookingDAO.rejectAndArchive(bookingId);
                break;

            default:
                request.setAttribute("error", "❌ Unknown action.");
                break;
        }

        // Refresh list of bookings after action
        List<Booking> bookings = bookingDAO.getAllBookings();
        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("manage-bookings.jsp").forward(request, response);

    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("error", "❌ Failed to process booking: " + e.getMessage());
        request.getRequestDispatcher("manage-bookings.jsp").forward(request, response);
    }
}
}
