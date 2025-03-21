package com.hotel.servlets;
import java.io.IOException;
import java.util.List;

import com.hotel.dao.HotelDAO;
import com.hotel.model.Hotel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/hotel") 
public class HotelServlet extends HttpServlet {

    private HotelDAO hotelDAO = new HotelDAO(); 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list"; // Default action 
        }

        switch (action) {
            case "list":
                listHotels(request, response);
                break;
            case "search":
                searchHotels(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing");
            return;
        }

        switch (action) {
            case "add":
                addHotel(request, response);
                break;
            case "update":
                updateHotel(request, response);
                break;
            case "delete":
                deleteHotel(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    private void listHotels(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Hotel> hotels = hotelDAO.getAllHotels();
        request.setAttribute("hotels", hotels); 
        request.getRequestDispatcher("hotels.jsp").forward(request, response); 
    }

    private void searchHotels(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        Integer stars = request.getParameter("stars") != null ? Integer.parseInt(request.getParameter("stars")) : null;
        String address = request.getParameter("address");

        List<Hotel> hotels = hotelDAO.filterHotel(name, stars, address);
        request.setAttribute("hotels", hotels);
        request.getRequestDispatcher("hotels.jsp").forward(request, response);
    }

    private void addHotel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int chainID = Integer.parseInt(request.getParameter("chainID"));
            int stars = Integer.parseInt(request.getParameter("stars"));
            int numberOfRooms = Integer.parseInt(request.getParameter("numberOfRooms"));
            String address = request.getParameter("address");
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phoneNumber");

            hotelDAO.saveHotel(chainID, stars, numberOfRooms, address, email, phoneNumber);
            response.sendRedirect("hotel?action=list"); 
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format");
        }
    }

    private void updateHotel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int hotelID = Integer.parseInt(request.getParameter("hotelID"));
            Integer stars = request.getParameter("stars") != null ? Integer.parseInt(request.getParameter("stars")) : null;
            Integer numberofroom = request.getParameter("numberofroom") != null ? Integer.parseInt(request.getParameter("numberofroom")) : null;
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phoneNumber");

            hotelDAO.updateHotel(hotelID, stars, numberofroom, email, phoneNumber);
            response.sendRedirect("hotel?action=list"); 
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format");
        }
    }

    private void deleteHotel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int hotelID = Integer.parseInt(request.getParameter("hotelID"));
            hotelDAO.deleteHotel(hotelID);
            response.sendRedirect("hotel?action=list"); 
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid hotel ID format");
        }
    }
}
