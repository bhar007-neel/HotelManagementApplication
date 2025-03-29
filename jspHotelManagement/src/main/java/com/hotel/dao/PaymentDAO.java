package com.hotel.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hotel.model.Payment;
import com.hotel.utils.DBConnection;

public class PaymentDAO {

    public void savePayment(int rentingId, double amount, String date, String method) {
        String query = "INSERT INTO \"Payment\" (\"RENTING_ID\", \"AMOUNT\", \"DATE\", \"PAYMENT_METHOD\") VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, rentingId);
            stmt.setDouble(2, amount);
            stmt.setDate(3, Date.valueOf(date));
            stmt.setString(4, method);
            stmt.executeUpdate();
            System.out.println("Payment inserted successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePayment(int paymentId) {
        String deleteSql = "DELETE FROM \"Payment\" WHERE \"PAYMENT_ID\" = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteSql)) {

            stmt.setInt(1, paymentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePayment(int paymentId, Double amount, Date date, String paymentMethod) {
        String updateSql = "UPDATE \"Payment\" SET ";
        List<Object> parameters = new ArrayList<>();

        if (amount != null) {
            updateSql += "\"AMOUNT\" = ?, ";
            parameters.add(amount);
        }
        if (date != null) {
            updateSql += "\"DATE\" = ?, ";
            parameters.add(date);
        }
        if (paymentMethod != null && !paymentMethod.isEmpty()) {
            updateSql += "\"PAYMENT_METHOD\" = ?, ";
            parameters.add(paymentMethod);
        }

        if (parameters.isEmpty()) {
            System.out.println("⚠ No fields to update.");
            return;
        }

        updateSql = updateSql.substring(0, updateSql.length() - 2) + " WHERE \"PAYMENT_ID\" = ?";
        parameters.add(paymentId);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateSql)) {

            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "Payment updated." : "Payment not found.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT * FROM \"Payment\"";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                payments.add(new Payment(
                        rs.getInt("PAYMENT_ID"),
                        rs.getDouble("AMOUNT"),
                        rs.getDate("DATE"),
                        rs.getString("PAYMENT_METHOD")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return payments;
    }
}
