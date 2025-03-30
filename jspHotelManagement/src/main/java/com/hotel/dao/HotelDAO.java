package com.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hotel.model.Hotel;
import com.hotel.utils.DBConnection;

public class HotelDAO {

    public void saveHotel(int chainid, int stars, int NumberOfRoom, String address, String email, String phonenumber) {
        String savesql = "INSERT INTO \"Hotel\" (\"ChainID\", \"Stars\", \"NumberOfRoom\", \"Address\", \"Email\", \"PhoneNumber\") VALUES (?,?,?,?,?,?)";

        try (Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(savesql)) {
            
            preparedStatement.setInt(1, chainid);
            preparedStatement.setInt(2, stars);
            preparedStatement.setInt(3, NumberOfRoom);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, phonenumber);
            preparedStatement.executeUpdate();
            System.out.println("Successfully inserted hotel");
            }

            

        catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
             e.printStackTrace();
        }
    }


    public void deleteHotel(int hotelid) {

        try (Connection connection = DBConnection.getConnection()) {

            // Step 1: Delete employees assigned to this hotel
            String deleteEmployeesSQL = "DELETE FROM \"Employee\" WHERE \"HotelID\" = ?";
            try (PreparedStatement deleteEmployees = connection.prepareStatement(deleteEmployeesSQL)) {
                deleteEmployees.setInt(1, hotelid);
                deleteEmployees.executeUpdate();
            }

            // Step 2: Delete the hotel
            String deleteHotelSQL = "DELETE FROM \"Hotel\" WHERE \"HotelID\" = ?";
            try (PreparedStatement deleteHotel = connection.prepareStatement(deleteHotelSQL)) {
                deleteHotel.setInt(1, hotelid);
                int rowsDeleted = deleteHotel.executeUpdate();

                if (rowsDeleted > 0) {
                    System.out.println("Hotel deleted successfully");
                } else {
                    System.out.println("Hotel not found");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error deleting hotel: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateHotel(int hotelid, Integer stars, Integer NumberOfRoom, String email, String phonenumber) {
        String updatesql = "UPDATE \"Hotel\" SET ";
        List<Object> parameters = new ArrayList<>();

        if (stars != null) {
            updatesql += "\"Stars\" = ?, ";
            parameters.add(stars);
        }
        if (email != null && !email.isEmpty()) {
            updatesql += "\"Email\" = ?, ";
            parameters.add(email);
     }
        if (phonenumber != null && !phonenumber.isEmpty()) {
            updatesql += "\"PhoneNumber\" = ?, ";
            parameters.add(phonenumber);
        }

        if (NumberOfRoom != null && NumberOfRoom > 0) {
            updatesql += "\"NumberOfRoom\" = ?, ";
            parameters.add(NumberOfRoom);
        }



        if (parameters.isEmpty()) {
            System.out.println("No fields updated");
            return;
         }

         updatesql = updatesql.substring(0, updatesql.length() - 2) + " WHERE \"HotelID\" = ?";
         parameters.add(hotelid);


        try (Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updatesql)) {

                for (int i = 0; i < parameters.size(); i++) {
                    preparedStatement.setObject(i + 1, parameters.get(i));
                }


                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated>0) {
                    System.out.println("Hotel updated");
                } else {
                    System.out.println("Hotel not updated");
                }

    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
        e.printStackTrace();

    }
}

    public List<Hotel> getAllHotels() {
        List<Hotel> hotels = new ArrayList<>();

        String getHotels = "SELECT * FROM \"Hotel\"";

        try (Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(getHotels)) {
            
            ResultSet rs = preparedStatement.executeQuery();
    
                while (rs.next()) {
                    hotels.add(new Hotel(
                        rs.getInt("HotelID"),
                        rs.getInt("ChainID"),
                        rs.getInt("Stars"),
                        rs.getInt("NumberOfRoom"),
                        rs.getString("Address"),
                        rs.getString("Email"),
                        rs.getString("PhoneNumber")
                    ));
                }


        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return hotels;
    }


    public List<Hotel> filterHotel(String name, Integer stars, String address) {

        List<Hotel> hotels = new ArrayList<>();

        String query = "SELECT * FROM \"Hotel\" WHERE 1=1";


        List<Object> parameters = new ArrayList<>();
        
        Integer chainID = null;

        if (name != null && !name.isEmpty()) {
            chainID = getChainIDByName(name);
            if (chainID == null) {
                System.out.println("No hotel chain found for name: " + name);
                return hotels;
            }
            query += " AND \"ChainID\" = ?";
            parameters.add(chainID);
        }

        if (stars != null) {
            query += " AND \"Stars\" = ?";
            parameters.add(stars);
        }

        if (address != null && !address.isEmpty()) {
            query += " AND \"Address\" LIKE ?";

            parameters.add("%" + address + "%");
        }

        try (Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                for (int i = 0; i < parameters.size(); i++) {
                    preparedStatement.setObject(i + 1, parameters.get(i));
                }
                

                ResultSet rs = preparedStatement.executeQuery();
    
                while (rs.next()) {
                    hotels.add(new Hotel(
                        rs.getInt("HotelID"),
                        rs.getInt("ChainID"),
                        rs.getInt("Stars"),
                        rs.getInt("NumberOfRoom"),
                        rs.getString("Address"),
                        rs.getString("Email"),
                        rs.getString("PhoneNumber")
                    ));
                }
    
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
    
            return hotels;
        }
        
            

    private Integer getChainIDByName(String hotelName) {
        String sql = "SELECT \"ChainID\" FROM \"HotelChain\" WHERE \"Name\" = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hotelName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("ChainID"); 
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        return null;

    }
    
    public List<String> getAllHotelChainNames() {
        List<String> chainNames = new ArrayList<>();
        String query = "SELECT \"Name\" FROM \"HotelChain\"";
    
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {
    
            while (rs.next()) {
                chainNames.add(rs.getString("Name"));
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return chainNames;
    }

    public List<Integer> getHotelIdsFromChainName(String chainName) {
        List<Integer> hotelIds = new ArrayList<>();
    
        String sql =
    "SELECT h.\"HotelID\" " +
    "FROM \"Hotel\" h " +
    "JOIN \"HotelChain\" c ON h.\"ChainID\" = c.\"ChainID\" " +
    "WHERE c.\"Name\" = ?";

    
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, chainName);
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                hotelIds.add(rs.getInt("HotelID"));
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return hotelIds;
    }
    
    
}




       

    
