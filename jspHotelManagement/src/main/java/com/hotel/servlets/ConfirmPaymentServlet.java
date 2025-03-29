package com.hotel.servlets;

import java.io.IOException;
import java.sql.Date;

import com.hotel.dao.PaymentDAO;
import com.hotel.dao.rentingDao;
import com.hotel.model.Renting;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ConfirmPaymentServlet")
public class ConfirmPaymentServlet extends HttpServlet {

    private final PaymentDAO paymentDAO = new PaymentDAO();
    private final rentingDao rentingDAO = new rentingDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Get parameters
            int rentingId = Integer.parseInt(request.getParameter("rentingId"));
            double amount = Double.parseDouble(request.getParameter("amount"));
            String paymentMethod = request.getParameter("paymentMethod");
            String paymentDateStr = request.getParameter("paymentDate");

            // Validate payment date
            if (paymentDateStr == null || paymentDateStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Payment date is missing or invalid.");
            }

            Date paymentDate = Date.valueOf(paymentDateStr); // Must be yyyy-MM-dd

            // Save payment
            paymentDAO.savePayment(rentingId, amount, paymentDate.toString(), paymentMethod);

            // Update renting status to Paid and archive it
            Renting renting = new Renting(rentingId, 0, "Paid"); // Only ID and status needed
            rentingDAO.updateRenting(renting);

            // Redirect back to admin rentings page
            response.sendRedirect("AdminRentingServlet");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "❌ Payment failed: " + e.getMessage());
            request.getRequestDispatcher("ConfirmPayment.jsp").forward(request, response);
        }
    }
}
