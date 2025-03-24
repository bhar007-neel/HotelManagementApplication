package com.hotel.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.hotel.model.Room;
import com.hotel.utils.DBConnection;

public class RoomDAO {

    public void saveRoom(int hotelid, double price, String roomtype, String damage, String view, boolean extendable) {
        String savesql = "Insert INTO \"Room\" (\"HotelID\", \"Price\", \"RoomType\", \"Damage\", \"View\", \"Extendable\") VALUES (?,?,?,?,?,?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(savesql)) {

            preparedStatement.setInt(1, hotelid);
            preparedStatement.setDouble(2, price);
            preparedStatement.setString(3, roomtype);
            if (damage == null || damage.isEmpty()) {
                preparedStatement.setNull(4, java.sql.Types.VARCHAR);
            } else {
                preparedStatement.setString(4, damage);
            }
            preparedStatement.setString(5, view);
            preparedStatement.setBoolean(6, extendable);

            preparedStatement.executeUpdate();
            System.out.println("Room added successfully");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateRoomPrice(int roomID, double newPrice) {
        String sql = "UPDATE \"Room\" SET \"Price\" = ? WHERE \"RoomID\" = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setDouble(1, newPrice);
            preparedStatement.setInt(2, roomID);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Room price updated successfully");
            } else {
                System.out.println("Room not found");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Room> filterRoomsWithAvailability(Double minPrice, Double maxPrice, String roomType, String view,
                                                  List<Integer> hotelIDs, Date startDate, Date endDate, String address) {
        List<Room> rooms = new ArrayList<>();
        StringBuilder query = new StringBuilder(
            "SELECT r.*, h.\"Address\" AS hotelAddress, hc.\"Name\" AS chainName " +
            "FROM \"Room\" r " +
            "JOIN \"Hotel\" h ON r.\"HotelID\" = h.\"HotelID\" " +
            "JOIN \"HotelChain\" hc ON h.\"ChainID\" = hc.\"ChainID\" " +
            "WHERE 1=1"
        );

        List<Object> parameters = new ArrayList<>();

        if (minPrice != null) {
            query.append(" AND r.\"Price\" >= ?");
            parameters.add(minPrice);
        }
        if (maxPrice != null) {
            query.append(" AND r.\"Price\" <= ?");
            parameters.add(maxPrice);
        }
        if (roomType != null && !roomType.isEmpty()) {
            query.append(" AND r.\"RoomType\" = ?");
            parameters.add(roomType);
        }
        if (view != null && !view.isEmpty()) {
            query.append(" AND r.\"View\" = ?");
            parameters.add(view);
        }
        if (hotelIDs != null && !hotelIDs.isEmpty()) {
            query.append(" AND r.\"HotelID\" IN (")
                 .append(hotelIDs.stream().map(id -> "?").collect(Collectors.joining(", ")))
                 .append(")");
            parameters.addAll(hotelIDs);
        }
        if (address != null && !address.isEmpty()) {
            query.append(" AND LOWER(h.\"Address\") LIKE LOWER(?)");
            parameters.add("%" + address + "%");
        }

        if (startDate != null && endDate != null) {
            query.append(" AND r.\"RoomID\" NOT IN (")
                 .append("SELECT \"RoomID\" FROM \"Booking\" b ")
                 .append("WHERE NOT (? > b.\"EndDate\" OR ? < b.\"StartDate\")")
                 .append(")");
            parameters.add(startDate);
            parameters.add(endDate);
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Room room = new Room(
                    rs.getInt("RoomID"),
                    rs.getInt("HotelID"),
                    rs.getDouble("Price"),
                    rs.getString("RoomType"),
                    rs.getString("Damage"),
                    rs.getString("View"),
                    rs.getBoolean("Extendable")
                );
                room.setHotelAddress(rs.getString("hotelAddress"));
                room.setChainName(rs.getString("chainName"));
                rooms.add(room);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms;
    }
}
