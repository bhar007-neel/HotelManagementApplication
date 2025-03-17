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
             e.printStackTrace();
        }
    }


    public void deleteHotel(int hotelid) {
        String deletesql = "DELETE FROM \"Hotel\" WHERE \"RoomID\" = ?";

        try (Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(deletesql)) {

            preparedStatement.setInt(1,hotelid);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateHotel(int hotelid, Integer stars, int NumberOfRoom, String email, String phonenumber) {
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
        e.printStackTrace();

    }
}


    public List<Hotel> filterHotel(String name, Integer stars, String address) {

        List<Hotel> hotels = new ArrayList<>();

        String query = "SELECT * FROM \"Hotel\" WHERE 1=1";


        List<Object> parameters = new ArrayList<>();
        
        Integer chainID = getChainIDByName(name);

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
            e.printStackTrace();
        }

        return null;

    }
}




       

    