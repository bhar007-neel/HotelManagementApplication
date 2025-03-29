package com.hotel.servlets;

import java.io.IOException;
import java.util.List;

import com.hotel.dao.rentingDao;
import com.hotel.model.Renting;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AdminRentingServlet")
public class AdminRentingServlet extends HttpServlet {

    private final rentingDao rentingDAO = new rentingDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Renting> rentings = rentingDAO.getAllRentings();
        request.setAttribute("rentings", rentings);
        request.getRequestDispatcher("manage-rentings.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String action = request.getParameter("action");

            if ("update".equals(action)) {
                int rentingId = Integer.parseInt(request.getParameter("rentingId"));
                String paymentStatus = request.getParameter("paymentStatus");

                Renting renting = new Renting();
                renting.setRentingId(rentingId);
                renting.setPaymentStatus(paymentStatus);

                rentingDAO.updateRenting(renting);
            }

            response.sendRedirect("AdminRentingServlet");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "❌ Failed to update renting.");
            doGet(request, response); // fallback to reload with error
        }
    }
}
