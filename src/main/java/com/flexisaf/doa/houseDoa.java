package com.flexisaf.doa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flexisaf.config.*;
import com.flexisaf.models.house;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class houseDoa {

    private static final Logger logger = Logger.getLogger(houseDoa.class.getName());

    // create house
    public boolean createHouse(house newHouse){
        try (Connection connection = databaseConfig.getConnection()){
            String sql = "INSERT INTO House (userId , rent , numberOfRooms, locality ) VALUES (?, ?, ?, ?) ";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql , PreparedStatement.RETURN_GENERATED_KEYS)){
                preparedStatement.setInt(1, newHouse.getUserId());
                preparedStatement.setInt(2,newHouse.getRent());
                preparedStatement.setInt(3, newHouse.getNumberOfRooms());
                preparedStatement.setString(4, newHouse.getLocality());

                int affectedRow = preparedStatement.executeUpdate();

                if (affectedRow > 0){
                    try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){
                        if (generatedKeys.next()){
                            newHouse.setHouseId(generatedKeys.getInt(1));
                            return true;
                        }
                    }
                }


            }
        }catch (SQLException e){
            logger.log(Level.SEVERE ,"Error creating House " + e.getSQLState() + " Error: " + e.getMessage(), e);
        }
        return false;
    }

    public boolean updateHouse ( house updateHouse ){
        try(Connection connection = databaseConfig.getConnection()) {
            String sql = "UPDATE House SET rent=?, numberOfRooms=?, locality=? WHERE houseId=? ";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1,updateHouse.getRent());
                preparedStatement.setInt(2, updateHouse.getNumberOfRooms());
                preparedStatement.setString(3, updateHouse.getLocality());
                preparedStatement.setInt(4,updateHouse.getHouseId());

                int affected = preparedStatement.executeUpdate();
                return affected > 0;
            }

        } catch (SQLException e){
            logger.log(Level.SEVERE , "Error Updating house "  + e.getSQLState() + " Error: " + e.getMessage(), e);
        }
        return  false;
    }

    public List<house> getAllHouse(){
        List<house> Houses = new ArrayList<>();
        try( Connection connection = databaseConfig.getConnection()){
            String sql = "SELECT * FROM House ORDER BY rent ";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    while (resultSet.next()){
                        house House = mapResultsetToHouse(resultSet);
                        Houses.add(House);
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching house " + e.getSQLState() + " Error: " + e.getMessage(), e);
        }
        return Houses;
    }

    public house getHouseByHouseId (int houseId){
        house House = null;
        try (Connection connection = databaseConfig.getConnection()){
            String sql = "SELECT * FROM House WHERE houseId=?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1, houseId);

                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    if (resultSet.next()){
                        House = mapResultsetToHouse(resultSet);
                    }
                }
            }
        }catch (SQLException e){
            logger.log(Level.SEVERE , "Failed to get house " + e.getSQLState() + " Error: " + e.getMessage(), e);
        }
        return House;
    }

    public List<house> getHouseByUserId (int userId){
        List<house> House = new ArrayList<>();
        try(Connection connection = databaseConfig.getConnection()){
            String sql = "SELECT * FROM House WHERE userId=?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1,userId);

                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    while (resultSet.next()){
                        house Houses = mapResultsetToHouse(resultSet);
                        assert false;
                        House.add(Houses);
                    }
                }
            }
        } catch (SQLException e){
            logger.log(Level.SEVERE , "Failed to get house " + e.getSQLState() + " Error: " + e.getMessage(), e);
        }
        return  House;
    }
    public List<house>  getHouseByNumberOfRoom(int numberOfRooms){
        List<house> House = new ArrayList<>();
        try (Connection connection = databaseConfig.getConnection()){
            String sql = "SELECT * FROM House WHERE numberOfRooms=? ";
            try ( PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1,numberOfRooms);
                try (ResultSet resultSet = preparedStatement.executeQuery()){
                    while (resultSet.next()){
                        house Houses = mapResultsetToHouse(resultSet);
                        House.add(Houses);
                    }
                }
            }
        } catch (SQLException e){
            logger.log(Level.SEVERE , "Failed to get house " + e.getSQLState() + " Error: " + e.getMessage(), e);
        }
        return House;
    }
    public List<house> getHouseByRent (int rent){
        List<house> House = new ArrayList<>();
        try (Connection connection = databaseConfig.getConnection()){
            String sql = "SELECT * FROM House WHERE rent=?";
            try( PreparedStatement preparedStatement  = connection.prepareStatement(sql)){
                preparedStatement.setInt(1,rent);
                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    while (resultSet.next()){
                        house Houses = mapResultsetToHouse(resultSet);
                        House.add(Houses);
                    }
                }
            }
        } catch (SQLException e){
            logger.log(Level.SEVERE , "Failed to get house " + e.getSQLState() + " Error: " + e.getMessage(), e);
        }
        return House;
    }

    public  List<house> getHouseByLocality( String locality){
        List<house> House = new ArrayList<>();
        try(Connection connection = databaseConfig.getConnection()){
            String sql = "SELECT * FROM House WHERE locality=?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setString(1,locality);
                try (ResultSet resultSet = preparedStatement.executeQuery()){
                    while (resultSet.next()){
                        house Houses = mapResultsetToHouse(resultSet);
                        House.add(Houses);
                    }
                }
            }
        } catch (SQLException e){
            logger.log(Level.SEVERE , "Failed to get house " + e.getSQLState() + " Error: " + e.getMessage(), e);
        }
        return House;
    }

    public boolean deleteHouse(int houseId ){
        try(Connection connection = databaseConfig.getConnection()){
            String sql = "DELETE FROM House WHERE houseId=?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1, houseId);
                int affectedRows = preparedStatement.executeUpdate();

                return affectedRows > 0;
            }
        }catch (SQLException e){
            logger.log(Level.SEVERE , "Failed to delete house SQL state: " + e.getSQLState() + " Error: " + e.getMessage(), e);
        }
        return false;
    }
    private house mapResultsetToHouse (ResultSet resultSet) throws SQLException {
        house House = new house();
        House.setHouseId(resultSet.getInt("houseId"));
        House.setUserId(resultSet.getInt("userId"));
        House.setRent(resultSet.getInt("rent"));
        House.setNumberOfRooms(resultSet.getInt("numberOfRooms"));
        House.setLocality(resultSet.getString("locality"));
        return House;
    }
    public String mapHouseToJson (List<house> houses) {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.writeValueAsString(houses);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
