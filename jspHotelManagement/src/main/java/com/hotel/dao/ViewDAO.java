package com.hotel.dao;

import com.hotel.model.AvailableRooms;
import com.hotel.model.RoomCapacity;
import com.hotel.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViewDAO {

    public List<AvailableRooms> getAvailableRooms() {
        List<AvailableRooms> availableRoomsList = new ArrayList<>();
        String sql = "SELECT * FROM \"AvailableRoomsPerArea\"";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String address = rs.getString("HotelAddress");
                int count = rs.getInt("AvailableRoomCount");
                availableRoomsList.add(new AvailableRooms(address, count));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableRoomsList;
    }

    public List<RoomCapacity> getRoomCapacities() {
        List<RoomCapacity> roomCapacities = new ArrayList<>();
        String sql = "SELECT * FROM \"RoomCapacityPerHotel\"";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String address = rs.getString("HotelAddress");
                int capacity = rs.getInt("TotalCapacity");
                roomCapacities.add(new RoomCapacity(address, capacity));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomCapacities;
    }
}


