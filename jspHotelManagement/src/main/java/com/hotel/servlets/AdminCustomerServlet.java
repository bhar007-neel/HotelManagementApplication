package com.hotel.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hotel.model.Customer;
import com.hotel.utils.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AdminCustomerServlet")
public class AdminCustomerServlet extends HttpServlet {

//    // Direct database connection details
//    private static final String URL = "jdbc:postgresql://localhost:5432/HotelManagement";
//    private static final String USER = "postgres";
//    private static final String PASSWORD = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if admin is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect("admin-login.jsp");
            return;
        }

        List<Customer> customers = new ArrayList<>();

        // Try connecting to the database directly
        try {
//            // Load the PostgreSQL JDBC driver
//            Class.forName("org.postgresql.Driver");
//
//            // Establish connection
//            Connection connection = com.hotel.dao.DBConnection.getConnection();
            Connection connection = DBConnection.getConnection();



            String query = "SELECT * FROM \"Customer\"";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                customers.add(new Customer(
                        rs.getInt("CustomerID"),
                        rs.getString("Name"),
                        rs.getString("Address"),
                        rs.getString("IDType"),
                        rs.getString("IDNumber"),
                        rs.getString("RegistrationDate")
                ));
            }

            // Clean up
            rs.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new ServletException("Database connection failed.", e);
        }

        request.setAttribute("customers", customers);
        request.getRequestDispatcher("admin-customers.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("---------- AdminCustomerServlet POST ----------");

        String action = request.getParameter("action");
        String name = request.getParameter("name");
        String address = request.getParameter("address");

        System.out.println("Action: " + action);
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);

        try {
//            Class.forName("org.postgresql.Driver");
            Connection conn = DBConnection.getConnection();


            if ("add".equals(action)) {
                String idType = request.getParameter("idType");
                String idNumber = request.getParameter("idNumber");

                String today = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());

                String insertSql = "INSERT INTO \"Customer\" (\"Name\", \"Address\", \"IDType\", \"IDNumber\", \"RegistrationDate\") VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(insertSql);
                stmt.setString(1, name);
                stmt.setString(2, address);
                stmt.setString(3, idType);
                stmt.setString(4, idNumber);
                stmt.setString(5, today);

                int rows = stmt.executeUpdate();
                System.out.println("Customer added. Rows inserted: " + rows);
                stmt.close();

            } else if ("update".equals(action)) {
                String idParam = request.getParameter("customerId");
                if (idParam == null || idParam.isEmpty()) {
                    System.out.println(" No customer ID for update.");
                    response.sendRedirect("AdminCustomerServlet");
                    return;
                }

                int customerId = Integer.parseInt(idParam);

                String sql = "UPDATE \"Customer\" SET \"Name\" = ?, \"Address\" = ? WHERE \"CustomerID\" = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setString(2, address);
                stmt.setInt(3, customerId);
                int rows = stmt.executeUpdate();
                System.out.println(" Rows updated: " + rows);
                stmt.close();

            } else if ("delete".equals(action)) {
                String idParam = request.getParameter("customerId");
                if (idParam == null || idParam.isEmpty()) {
                    System.out.println(" No customer ID for delete.");
                    response.sendRedirect("AdminCustomerServlet");
                    return;
                }

                int customerId = Integer.parseInt(idParam);

                String sql = "DELETE FROM \"Customer\" WHERE \"CustomerID\" = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, customerId);
                int rows = stmt.executeUpdate();
                System.out.println("🗑 Rows deleted: " + rows);
                stmt.close();
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(" Error processing request: " + e.getMessage(), e);
        }

        response.sendRedirect("AdminCustomerServlet");
    }



}
