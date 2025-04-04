package com.hotel.servlets;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import com.hotel.dao.RoomDAO;
import com.hotel.model.Room;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/room")
public class RoomServlet extends HttpServlet {

    private RoomDAO roomDAO = new RoomDAO();

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
            case "delete":
                deleteRoom(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    private void listRooms(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Date startDate = Date.valueOf("2025-01-01");
        Date endDate = Date.valueOf("2025-12-31");
        
        List<Room> rooms = roomDAO.filterRoomsWithAvailability(null, null, null, null, null, startDate, endDate, null);
        request.setAttribute("rooms", rooms);
        request.getRequestDispatcher("manage_rooms.jsp").forward(request, response);
        
    }

    private void filterRooms(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Double minPrice = request.getParameter("minPrice") != null ? Double.parseDouble(request.getParameter("minPrice")) : null;
        Double maxPrice = request.getParameter("maxPrice") != null ? Double.parseDouble(request.getParameter("maxPrice")) : null;
        String roomType = request.getParameter("roomType");
        String view = request.getParameter("view");
        String address = request.getParameter("address");

        Date startDate = request.getParameter("startDate") != null ? Date.valueOf(request.getParameter("startDate")) : null;
        Date endDate = request.getParameter("endDate") != null ? Date.valueOf(request.getParameter("endDate")) : null;


        List<Room> rooms = roomDAO.filterRoomsWithAvailability(minPrice, maxPrice, roomType, view, null, startDate, endDate, address);

        request.setAttribute("rooms", rooms);
        request.getRequestDispatcher("rooms.jsp").forward(request, response);

    }

    private void addRoom(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int hotelID = Integer.parseInt(request.getParameter("hotelID"));
            double price = Double.parseDouble(request.getParameter("price"));
            String roomType = request.getParameter("roomType");
            roomType = roomType.substring(0, 1).toUpperCase() + roomType.substring(1).toLowerCase();
            String damage = request.getParameter("damage");
            String view = request.getParameter("view");
            view = view.substring(0, 1).toUpperCase() + view.substring(1).toLowerCase();
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
            response.sendRedirect("manage_rooms.jsp");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format");    
        }
    }
    private void deleteRoom(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int roomID = Integer.parseInt(request.getParameter("roomID"));
            roomDAO.deleteRoom(roomID);
            response.sendRedirect("room?action=list");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Room ID");
        }
    }

}