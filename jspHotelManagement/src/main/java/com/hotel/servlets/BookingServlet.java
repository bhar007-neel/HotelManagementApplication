package com.hotel.servlets;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.hotel.dao.CustomerDAO;
import com.hotel.dao.bookingDao;
import com.hotel.model.Booking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {

    private final CustomerDAO customerDAO = new CustomerDAO();
    private final bookingDao bookingDAO = new bookingDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String idType = request.getParameter("idType");
            String idNumber = request.getParameter("idNumber");
            String registrationDate = new SimpleDateFormat("yyyyMMdd").format(new java.util.Date());

            // ID validation
            if ((idType.equals("SSN") || idType.equals("SIN")) && idNumber.length() != 9) {
                throw new IllegalArgumentException("ID number must be 9 characters for SSN/SIN");
            } else if (idType.equals("Driver's License") && idNumber.length() > 19) {
                throw new IllegalArgumentException("Driver's License number too long");
            }

            Integer customerId = customerDAO.findCustomerId(idType, idNumber);
            if (customerId == null) {
                customerId = customerDAO.addCustomer(name, address, idType, idNumber, registrationDate);
                if (customerId == -1) {
                    throw new IllegalStateException("Failed to create customer.");
                }
            }

            String roomIdStr = request.getParameter("roomId");
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");

            if (roomIdStr == null || startDateStr == null || endDateStr == null) {
                throw new IllegalArgumentException("Missing booking information.");
            }

            int roomId = Integer.parseInt(roomIdStr);
            Date startDate = Date.valueOf(startDateStr);
            Date endDate = Date.valueOf(endDateStr);

            Booking booking = new Booking();
            booking.setRoomId(roomId);
            booking.setStartDate(startDate);
            booking.setEndDate(endDate);
            booking.setStatus("Pending");
            booking.setCustomerId(customerId);
            bookingDAO.addBooking(booking);

            request.setAttribute("message", "Booking successfully submitted!");
            request.getRequestDispatcher("confirmation.jsp").forward(request, response);

        } catch (IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
            request.setAttribute("error", "Booking failed: " + e.getMessage());
            request.getRequestDispatcher("bookingform.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Unexpected error occurred.");
            request.getRequestDispatcher("bookingform.jsp").forward(request, response);
        }
    }
}
