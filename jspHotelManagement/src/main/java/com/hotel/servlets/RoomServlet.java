package com.hotel.servlets;
import java.io.IOException;
import java.util.List;

import com.hotel.dao.RoomDAO;
import com.hotel.model.Room;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/room") // Maps servlet to room
public class RoomServlet extends HttpServlet {

    private RoomDAO roomDAO = new RoomDAO(); // DAO instance for room operations

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list"; 
        }

        switch (action) {
            case "list":
                listRooms(request, response);
                break;
            case "search":
                filterRooms(request, response);
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
                addRoom(request, response);
                break;
            case "updatePrice":
                updateRoomPrice(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    private void listRooms(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Room> rooms = roomDAO.filterRooms(null, null, null, null); // Fetch all rooms
        request.setAttribute("rooms", rooms);
        request.getRequestDispatcher("rooms.jsp").forward(request, response);
    }

    private void filterRooms(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Double minPrice = request.getParameter("minPrice") != null ? Double.parseDouble(request.getParameter("minPrice")) : null;
        Double maxPrice = request.getParameter("maxPrice") != null ? Double.parseDouble(request.getParameter("maxPrice")) : null;
        String roomType = request.getParameter("roomType");
        String view = request.getParameter("view");

        List<Room> rooms = roomDAO.filterRooms(minPrice, maxPrice, roomType, view);
        request.setAttribute("rooms", rooms);
        request.getRequestDispatcher("rooms.jsp").forward(request, response);
    }

    private void addRoom(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int hotelID = Integer.parseInt(request.getParameter("hotelID"));
            double price = Double.parseDouble(request.getParameter("price"));
            String roomType = request.getParameter("roomType");
            String damage = request.getParameter("damage");
            String view = request.getParameter("view");
            boolean extendable = Boolean.parseBoolean(request.getParameter("extendable"));

            roomDAO.saveRoom(hotelID, price, roomType, damage, view, extendable);
            response.sendRedirect("room?action=list"); 

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format");
        }
    }

    private void updateRoomPrice(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int roomID = Integer.parseInt(request.getParameter("roomID"));
            double newPrice = Double.parseDouble(request.getParameter("newPrice"));

            roomDAO.updateRoomPrice(roomID, newPrice);
            response.sendRedirect("room?action=list"); 

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format");
        }
    }
}
