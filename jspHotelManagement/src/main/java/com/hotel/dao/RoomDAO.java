package com.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hotel.model.Room;
import com.hotel.utils.DBConnection;

public class RoomDAO {

    public void saveRoom(int hotelid, double price, String roomtype, String damage, String view, boolean extendable) {
        String savesql = "Insert INTO \"Room\" (\"HotelID\", \"Price\", \"RoomType\", \"Damage\", \"View\", \"Extendable\") VALUES (?,?,?,?,?,?)";
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(savesql)) {

            preparedStatement.setInt(1, hotelid);
            preparedStatement.setDouble(2, price);
            preparedStatement.setString(3, roomtype);
            preparedStatement.setString(4, damage);
            preparedStatement.setString(5, view);
            preparedStatement.setBoolean(6, extendable);

            preparedStatement.executeUpdate();
            System.out.println("Room added sucessfully");

        }
        catch (SQLException e) {
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
            e.printStackTrace();
    }
}



    public List<Room> filterRooms(Double minPrice, Double maxPrice, String roomType, String view) {
        List<Room> rooms = new ArrayList<>();

        String query = "SELECT * FROM \"Room\" WHERE 1=1";

        List<Object> parameters = new ArrayList<>();

        if (minPrice != null) {
            query += " AND \"Price\" >= ?";
            parameters.add(minPrice);
        }

        if (maxPrice != null) {
            query += " AND \"Price\" <= ?";
            parameters.add(maxPrice);
        }

        if (roomType != null && !roomType.isEmpty()) {
            query += " AND \"RoomType\" = ?";
            parameters.add(roomType);
        }

        if (view != null && !view.isEmpty()) {
            query += " AND \"View\" = ?";
            parameters.add(view);
        }

        try (Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            int index = 1;
            for (Object param : parameters) {
                preparedStatement.setObject(index++, param);
            }

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                rooms.add(new Room(
                rs.getInt("RoomID"),
                rs.getInt("HotelID"),
                rs.getDouble("Price"),
                rs.getString("RoomType"),
                rs.getString("Damage"),
                rs.getString("View"),
                rs.getBoolean("Extendable")
            ));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return rooms;
}



    


}




    
