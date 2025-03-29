package com.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hotel.model.Booking;
import com.hotel.utils.DBConnection;

public class bookingDao {

    // Retrieve all bookings with customer details
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT b.*, c.\"Name\" AS customer_name, c.\"IDType\", c.\"IDNumber\" " +
                       "FROM \"Booking\" b JOIN \"Customer\" c ON b.\"CustomerID\" = c.\"CustomerID\"";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("BOOKING_ID"));
                booking.setCustomerId(rs.getInt("CustomerID"));
                booking.setRoomId(rs.getInt("RoomID"));
                booking.setStartDate(rs.getDate("StartDate"));
                booking.setEndDate(rs.getDate("EndDate"));
                booking.setStatus(rs.getString("Status"));
                booking.setCustomerName(rs.getString("customer_name"));
                booking.setIdType(rs.getString("IDType"));
                booking.setIdNumber(rs.getString("IDNumber"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    // Retrieve booking by ID
    public Booking getBookingById(int bookingId) {
        Booking booking = null;
        String query = "SELECT b.*, c.\"Name\" AS customer_name, c.\"IDType\", c.\"IDNumber\" " +
                       "FROM \"Booking\" b JOIN \"Customer\" c ON b.\"CustomerID\" = c.\"CustomerID\" WHERE b.\"BOOKING_ID\" = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                booking = new Booking();
                booking.setBookingId(rs.getInt("BOOKING_ID"));
                booking.setCustomerId(rs.getInt("CustomerID"));
                booking.setRoomId(rs.getInt("RoomID"));
                booking.setStartDate(rs.getDate("StartDate"));
                booking.setEndDate(rs.getDate("EndDate"));
                booking.setStatus(rs.getString("Status"));
                booking.setCustomerName(rs.getString("customer_name"));
                booking.setIdType(rs.getString("IDType"));
                booking.setIdNumber(rs.getString("IDNumber"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booking;
    }

    // Add a new booking
    public void addBooking(Booking booking) {
        String insertQuery = "INSERT INTO \"Booking\" (\"CustomerID\", \"RoomID\", \"StartDate\", \"EndDate\", \"Status\") VALUES (?, ?, ?, ?, ?)";
    
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
    
            insertStmt.setInt(1, booking.getCustomerId());
            insertStmt.setInt(2, booking.getRoomId());
            insertStmt.setDate(3, booking.getStartDate());
            insertStmt.setDate(4, booking.getEndDate());
            insertStmt.setString(5, booking.getStatus());
            insertStmt.executeUpdate();
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(int bookingId, String status) {
        String sql = "UPDATE \"Booking\" SET \"Status\" = ? WHERE \"BOOKING_ID\" = ?";
        String archiveQuery = "INSERT INTO \"ArchivedBooking\" (\"OriginalBookingID\", \"CustomerID\", \"RoomID\", \"StartDate\", \"EndDate\", \"Status\") VALUES (?, ?, ?, ?, ?, ?)";
    
        try (Connection conn = DBConnection.getConnection()) {
            // 1. Update Booking
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, status);
                stmt.setInt(2, bookingId);
                stmt.executeUpdate();
            }
    
            // 2. Archive only if final decision
            if ("Confirmed".equalsIgnoreCase(status) || "Cancelled".equalsIgnoreCase(status) || "Rejected".equalsIgnoreCase(status)) {
                Booking booking = getBookingById(bookingId); // fetch full details
                if (booking != null) {
                    try (PreparedStatement archiveStmt = conn.prepareStatement(archiveQuery)) {
                        archiveStmt.setInt(1, booking.getBookingId());
                        archiveStmt.setInt(2, booking.getCustomerId());
                        archiveStmt.setInt(3, booking.getRoomId());
                        archiveStmt.setDate(4, booking.getStartDate());
                        archiveStmt.setDate(5, booking.getEndDate());
                        archiveStmt.setString(6, status);
                        archiveStmt.executeUpdate();
                        System.out.println("Booking archived after decision.");
                    }
                }
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void convertBookingToRenting(int bookingId) {
        String sql = "INSERT INTO \"RENTING\" (\"RENTING_ID\", \"PAYMENT_STATUS\") VALUES (?, 'Unpaid')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBooking(Booking booking) {
        String query = "UPDATE \"Booking\" SET \"StartDate\"=?, \"EndDate\"=?, \"Status\"=? WHERE \"BOOKING_ID\"=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDate(1, booking.getStartDate());
            stmt.setDate(2, booking.getEndDate());
            stmt.setString(3, booking.getStatus());
            stmt.setInt(4, booking.getBookingId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rejectAndArchive(int bookingId) {
        String archiveQuery = "INSERT INTO \"ArchivedBooking\" " +
                "(\"OriginalBookingID\", \"CustomerID\", \"RoomID\", \"StartDate\", \"EndDate\", \"Status\") " +
                "SELECT \"BOOKING_ID\", \"CustomerID\", \"RoomID\", \"StartDate\", \"EndDate\", 'Rejected' " +
                "FROM \"Booking\" WHERE \"BOOKING_ID\" = ?";
    
        String deleteQuery = "DELETE FROM \"Booking\" WHERE \"BOOKING_ID\" = ?";
    
        try (Connection conn = DBConnection.getConnection()) {
            // Archive first
            try (PreparedStatement archiveStmt = conn.prepareStatement(archiveQuery)) {
                archiveStmt.setInt(1, bookingId);
                int archived = archiveStmt.executeUpdate();
                System.out.println("🗃 Rejected booking archived: " + archived + " row(s)");
            }
    
            // Then delete
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                deleteStmt.setInt(1, bookingId);
                int deleted = deleteStmt.executeUpdate();
                System.out.println("Rejected booking deleted: " + deleted + " row(s)");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBooking(int bookingId) {
        String query = "DELETE FROM \"Booking\" WHERE \"BOOKING_ID\" = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, bookingId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
