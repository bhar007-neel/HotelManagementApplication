package com.hotel.dao;

import com.hotel.model.Booking; // Importing booking model class
import com.hotel.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class bookingDao {

    // Retrieve all bookings
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>(); // creating a list to store all bookings
        String query = "SELECT * FROM BOOKING";

        try (Connection conn = DBConnection.getConnection(); // getting a connection to the database
             Statement stmt = conn.createStatement();   // creating a statement to execute sql queries
             ResultSet rs = stmt.executeQuery(query)) {   // executing the query and storing its result

            while (rs.next()) { //looping through the each row in the result
                Booking booking = new Booking(
                        rs.getInt("BOOKING_ID"),
                        rs.getDate("START_DATE"),
                        rs.getDate("END_DATE"),
                        rs.getString("STATUS")
                );
                bookings.add(booking); // adding the booking to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;  // returning the booking
    }

    // Retrieve booking by ID
    public Booking getBookingById(int bookingId) {
        Booking booking = null;
        String query = "SELECT * FROM BOOKING WHERE BOOKING_ID = ?";

        try (Connection conn = DBConnection.getConnection();  // connecting the database to perform the query
             PreparedStatement stmt = conn.prepareStatement(query)) { //preapared statement is the placeholder for the bookingID value and helps prevent SQL injection attacks

            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                booking = new Booking(
                        rs.getInt("BOOKING_ID"),
                        rs.getDate("START_DATE"),
                        rs.getDate("END_DATE"),
                        rs.getString("STATUS")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booking;
    }

    // Add a new booking
    public void addBooking(Booking booking) {
        String query = "INSERT INTO BOOKING (START_DATE, END_DATE, STATUS) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDate(1, booking.getStartDate());
            stmt.setDate(2, booking.getEndDate());
            stmt.setString(3, booking.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update an existing booking
    public void updateBooking(Booking booking) {
        String query = "UPDATE BOOKING SET START_DATE=?, END_DATE=?, STATUS=? WHERE BOOKING_ID=?";

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
        String query = "DELETE FROM BOOKING WHERE BOOKING_ID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, bookingId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
