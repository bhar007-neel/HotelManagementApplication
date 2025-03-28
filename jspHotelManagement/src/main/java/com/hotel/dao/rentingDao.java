package com.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hotel.model.Renting;
import com.hotel.utils.DBConnection;

public class rentingDao {

    // Retrieve all rentings
    public List<Renting> getAllRentings() {
        List<Renting> rentings = new ArrayList<>();
        String query = "SELECT * FROM \"Renting\"";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Renting rent = new Renting(
                        rs.getInt("RENTING_ID"),
                        rs.getInt("BOOKING_ID"),
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
        String query = "SELECT * FROM \"Renting\" WHERE \"RENTING_ID\" = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, rentingId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                renting = new Renting(
                        rs.getInt("RENTING_ID"),
                        rs.getInt("BOOKING_ID"),
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
        String insertQuery = "INSERT INTO Renting (BOOKING_ID, PAYMENT_STATUS) VALUES (?, ?)";
        String archiveQuery = "INSERT INTO ArchivedRenting (OriginalRentingID, BOOKING_ID, PAYMENT_STATUS) VALUES (?, ?, ?)";
    
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
    
            // Insert into RENTING
            insertStmt.setInt(1, renting.getBookingId());
            insertStmt.setString(2, renting.getPaymentStatus());
            insertStmt.executeUpdate();
    
            // Get generated RENTING_ID
            try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedRentingId = generatedKeys.getInt(1);
    
                    // Insert into ArchivedRenting
                    try (PreparedStatement archiveStmt = conn.prepareStatement(archiveQuery)) {
                        archiveStmt.setInt(1, generatedRentingId); // OriginalRentingID
                        archiveStmt.setInt(2, renting.getBookingId());
                        archiveStmt.setString(3, renting.getPaymentStatus());
                        archiveStmt.executeUpdate();
                    }
                }
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    // Update an existing renting
    public void updateRenting(Renting renting) {
        String updateRentingSql = "UPDATE RENTING SET PAYMENT_STATUS = ? WHERE RENTING_ID = ?";
        String updateArchiveSql = "UPDATE ArchivedRenting SET PAYMENT_STATUS = ? WHERE OriginalRentingID = ?";
    
        try (Connection conn = DBConnection.getConnection()) {
            // Update in RENTING table
            try (PreparedStatement stmt = conn.prepareStatement(updateRentingSql)) {
                stmt.setString(1, renting.getPaymentStatus());
                stmt.setInt(2, renting.getRentingId());
                stmt.executeUpdate();
            }
    
            // Update in ArchivedRenting table
            try (PreparedStatement archiveStmt = conn.prepareStatement(updateArchiveSql)) {
                archiveStmt.setString(1, renting.getPaymentStatus());
                archiveStmt.setInt(2, renting.getRentingId());
                archiveStmt.executeUpdate();
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    // Delete a renting
    public void deleteRenting(int rentingId) {
        String query = "DELETE FROM \"Renting\" WHERE \"RENTING_ID\" = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, rentingId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create renting entry from a booking
    public boolean createFromBooking(int bookingId) {
        String checkRentingQuery = "SELECT 1 FROM \"Renting\" WHERE \"BOOKING_ID\" = ?";
        String checkBookingQuery = "SELECT 1 FROM \"Booking\" WHERE \"BOOKING_ID\" = ?";
        String insertQuery = "INSERT INTO \"Renting\" (\"BOOKING_ID\", \"PAYMENT_STATUS\") VALUES (?, 'Unpaid')";
        String updateBookingQuery = "UPDATE \"Booking\" SET \"Status\" = 'Confirmed' WHERE \"BOOKING_ID\" = ?";
    
        try (Connection conn = DBConnection.getConnection()) {
    
            // Check if renting already exists
            try (PreparedStatement checkRenting = conn.prepareStatement(checkRentingQuery)) {
                checkRenting.setInt(1, bookingId);
                ResultSet rs = checkRenting.executeQuery();
                if (rs.next()) {
                    System.out.println("⚠️ Renting already exists for booking " + bookingId);
                    return false;
                }
            }
    
            // Ensure booking exists
            try (PreparedStatement checkBooking = conn.prepareStatement(checkBookingQuery)) {
                checkBooking.setInt(1, bookingId);
                ResultSet rs = checkBooking.executeQuery();
                if (!rs.next()) {
                    System.out.println("❌ Booking " + bookingId + " does not exist.");
                    return false;
                }
            }
    
            // Insert into renting
            try (PreparedStatement insertRenting = conn.prepareStatement(insertQuery)) {
                insertRenting.setInt(1, bookingId);
                insertRenting.executeUpdate();
                System.out.println("✅ Renting created for booking " + bookingId);
            }
    
            // Update booking status
            try (PreparedStatement updateBooking = conn.prepareStatement(updateBookingQuery)) {
                updateBooking.setInt(1, bookingId);
                updateBooking.executeUpdate();
                System.out.println("✅ Booking " + bookingId + " marked as Confirmed");
            }
    
            return true;
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
