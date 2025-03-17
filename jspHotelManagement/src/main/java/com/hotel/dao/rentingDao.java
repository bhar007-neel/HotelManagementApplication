package com.hotel.dao;


import com.hotel.model.Renting;
import com.hotel.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class rentingDao {
    // Retrieve all rentings
    public List<Renting> getAllRentings() {
        List<Renting> rentings = new ArrayList<>();
        String query = "SELECT * FROM RENTING";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Renting rent = new Renting(
                        rs.getInt("RENTING_ID"),
                        rs.getString("PAYMENT_STATUS")
                );
                rentings.add(rent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentings;
    }

    // Retrieve renting by ID
    public Renting getRentingById(int rentingId) {
        Renting renting = null;
        String query = "SELECT * FROM RENTING WHERE RENTING_ID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, rentingId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                renting = new Renting(
                        rs.getInt("RENTING_ID"),
                        rs.getString("PAYMENT_STATUS")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return renting;
    }

    // Add a new renting
    public void addRenting(Renting renting) {
        String query = "INSERT INTO RENTING (PAYMENT_STATUS) VALUES (?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, renting.getPaymentStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update an existing renting
    public void updateRenting(Renting renting) {
        String query = "UPDATE RENTING SET PAYMENT_STATUS=? WHERE RENTING_ID=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, renting.getPaymentStatus());
            stmt.setInt(2, renting.getRentingId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a renting
    public void deleteRenting(int rentingId) {
        String query = "DELETE FROM RENTING WHERE RENTING_ID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, rentingId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

