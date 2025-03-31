package com.hotel.servlets;

import com.hotel.dao.ViewDAO;
import com.hotel.model.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ViewServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ViewDAO dao = new ViewDAO();

        List<AvailableRooms> availableRooms = dao.getAvailableRooms();
        List<RoomCapacity> roomCapacities = dao.getRoomCapacities();

        //System.out.println("Available Rooms:" + availableRooms.size());
        //System.out.println("Room Capacities:" + roomCapacities.size());

        request.setAttribute("availableRooms", availableRooms);
        request.setAttribute("roomCapacities", roomCapacities);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/viewDashboard.jsp");
        dispatcher.forward(request, response);

    }
}

