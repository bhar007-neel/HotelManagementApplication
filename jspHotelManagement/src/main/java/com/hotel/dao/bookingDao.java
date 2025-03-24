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

    // Retrieve all bookings
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM \"Booking\"";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getInt("BOOKING_ID"),
                        rs.getInt("CustomerID"),
                        rs.getInt("RoomID"),
                        rs.getDate("StartDate"),
                        rs.getDate("EndDate"),
                        rs.getString("Status")
                );
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
        String query = "SELECT * FROM \"Booking\" WHERE \"BOOKING_ID\" = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                booking = new Booking(
                        rs.getInt("BOOKING_ID"),
                        rs.getInt("CustomerID"),
                        rs.getInt("RoomID"),
                        rs.getDate("StartDate"),
                        rs.getDate("EndDate"),
                        rs.getString("Status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booking;
    }

    // Add a new booking
    public void addBooking(Booking booking) {
        String query = "INSERT INTO \"Booking\" (\"CustomerID\", \"RoomID\", \"StartDate\", \"EndDate\", \"Status\") VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, booking.getCustomerId());
            stmt.setInt(2, booking.getRoomId());
            stmt.setDate(3, booking.getStartDate());
            stmt.setDate(4, booking.getEndDate());
            stmt.setString(5, booking.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update an existing booking
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

    // Delete a booking
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
