package com.hotel.servlets;

import java.io.IOException;
import java.sql.Date;
import java.util.Collections;
import java.util.List;

import com.hotel.dao.HotelDAO;
import com.hotel.dao.RoomDAO;
import com.hotel.model.Room;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/roomsearch")
public class RoomSearchServlet extends HttpServlet {

    private final RoomDAO roomDAO = new RoomDAO();
    private final HotelDAO hotelDAO = new HotelDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<String> chainNames = hotelDAO.getAllHotelChainNames();
        System.out.println("✅ [GET] Hotel Chains Loaded: " + chainNames);
        request.setAttribute("chainNames", chainNames);

        // Preserve dates if passed from previous page
        request.setAttribute("startDate", request.getParameter("startDate"));
        request.setAttribute("endDate", request.getParameter("endDate"));

        request.getRequestDispatcher("roomsearch.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String start = request.getParameter("startDate");
            String end = request.getParameter("endDate");
            String chainName = request.getParameter("chainName");
            String roomType = request.getParameter("roomType");
            String view = request.getParameter("view");
            String minPriceStr = request.getParameter("minPrice");
            String maxPriceStr = request.getParameter("maxPrice");
            String address = request.getParameter("address");

            Date startDate = (start != null && !start.isEmpty()) ? Date.valueOf(start) : null;
            Date endDate = (end != null && !end.isEmpty()) ? Date.valueOf(end) : null;
            Double minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? Double.parseDouble(minPriceStr) : null;
            Double maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty()) ? Double.parseDouble(maxPriceStr) : null;

            // Convert chainName into hotelIDs
            List<Integer> hotelIDs = (chainName != null && !chainName.isEmpty()) ? hotelDAO.getHotelIdsFromChainName(chainName) : Collections.emptyList();
            System.out.println("🔍 [POST] Hotel IDs for chain '" + chainName + "': " + hotelIDs);

            //Fetch available rooms
            List<Room> availableRooms = roomDAO.filterRoomsWithAvailability(
                minPrice, maxPrice, roomType, view, hotelIDs, startDate, endDate, address
            );

            System.out.println("✅ [POST] Filtered Rooms Found: " + availableRooms.size());

            //Keep values for later display
            request.setAttribute("availableRooms", availableRooms);
            request.setAttribute("startDate", start);
            request.setAttribute("endDate", end);
            request.setAttribute("chainName", chainName);
            request.setAttribute("roomType", roomType);
            request.setAttribute("view", view);
            request.setAttribute("minPrice", minPriceStr);
            request.setAttribute("maxPrice", maxPriceStr);
            request.setAttribute("address", address);

            //Reload chain names for dropdown
            List<String> chainNames = hotelDAO.getAllHotelChainNames();
            System.out.println("✅ [POST] Hotel Chains Loaded: " + chainNames);
            request.setAttribute("chainNames", chainNames);

            //Forward to roomresults.jsp
            request.getRequestDispatcher("roomresults.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "❌ Invalid input. Please check your search criteria.");

            // Preserve dropdown and forward back
            request.setAttribute("chainNames", hotelDAO.getAllHotelChainNames());
            request.getRequestDispatcher("roomsearch.jsp").forward(request, response);
        }
    }
}
