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

    public void savePayment(double amount, Date date, String paymentMethod) {
        String saveSql = "INSERT INTO \"PAYMENT\" (\"AMOUNT\", \"DATE\", \"PAYMENT_METHOD\") VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveSql)) {

            preparedStatement.setDouble(1, amount);
            preparedStatement.setDate(2, date);
            preparedStatement.setString(3, paymentMethod);
            preparedStatement.executeUpdate();
            System.out.println("Successfully inserted payment");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePayment(int paymentId) {
        String deleteSql = "DELETE FROM \"PAYMENT\" WHERE \"PAYMENT_ID\" = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {

            preparedStatement.setInt(1, paymentId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePayment(int paymentId, Double amount, Date date, String paymentMethod) {
        String updateSql = "UPDATE \"PAYMENT\" SET ";
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
            System.out.println("No fields updated");
            return;
        }

        updateSql = updateSql.substring(0, updateSql.length() - 2) + " WHERE \"PAYMENT_ID\" = ?";
        parameters.add(paymentId);

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {

            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Payment updated");
            } else {
                System.out.println("Payment not updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT * FROM \"PAYMENT\"";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {

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
