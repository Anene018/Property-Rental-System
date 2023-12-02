package com.flexisaf.doa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flexisaf.config.*;
import com.flexisaf.models.user;
import com.flexisaf.models.user.*;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class userDoa {

    private static final Logger logger = Logger.getLogger(userDoa.class.getName());

    //Create a new user
    public boolean createUser(user newUser) {
        try (Connection connection = databaseConfig.getConnection()) {
            String sql = "INSERT INTO Users (fullname, email, DOB, userType, hashedPassword) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, newUser.getFullname());
                preparedStatement.setString(2, newUser.getEmail());
                preparedStatement.setDate(3, newUser.getDOB());
                preparedStatement.setObject(4, newUser.getUserType().name());
                preparedStatement.setString(5, newUser.getHashedPassword());

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            newUser.setUserId(generatedKeys.getInt(1));
                            return true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error creating user. SQL State: " + e.getSQLState() + " Error: " + e.getMessage(), e);

        }
        return false;
    }

    // Get user by ID
    public user getUserById (int Id){
        user User = null;
        try (Connection connection = databaseConfig.getConnection()) {
            String sql = "SELECT * FROM Users WHERE userId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1, Id);

                try( ResultSet resultSet = preparedStatement.executeQuery()){
                    if (resultSet.next()) {
                        User = mapResultSetToUser(resultSet);
                    }
                }
            }

        } catch (SQLException e) {
            // TODO: handle exception
            logger.log(Level.SEVERE, "Error getting user SQL State: " + e.getSQLState() + " Error: " + e.getMessage(), e);
        }
        return User;
    }

    // Get all Users
    public List<user> getAllUsers () {
        List<user> Users = new ArrayList<>();
        try (Connection connection = databaseConfig.getConnection() ){
            String sql = "SELECT * FROM Users";
            try ( PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                try (ResultSet resultSet = preparedStatement.executeQuery()){
                    while (resultSet.next()) {
                        user User = mapResultSetToUser(resultSet);
                        Users.add(User);
                    }
                }
            }
        } catch (SQLException e) {
            // TODO: handle exception
            logger.log(Level.SEVERE, "Error getting user SQL State: " + e.getSQLState() + " Error: " + e.getMessage(), e);
        }
        return Users;
    }
    // Get User by email
    public user getUserByEmail(String email)  {
        user User = null;
        try (Connection connection = databaseConfig.getConnection()){
            String sql = "SELECT * FROM Users WHERE email=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setString(1,email);

                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    if (resultSet.next()){
                        User = mapResultSetToUser(resultSet);
                    }
                }
            }
        }catch (SQLException e){
            logger.log(Level.SEVERE, "Error getting user SQL State: " + e.getSQLState() + " Error: " + e.getMessage(), e);
        }
        return  User;
    }

    // Update User
    public boolean updateUser(user User) {
        try (Connection connection = databaseConfig.getConnection()) {
            String sql = "UPDATE Users SET fullname=?, email=?, DOB=?, userType=?, hashedPassword=? WHERE userId=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, User.getFullname());
                preparedStatement.setString(2, User.getEmail());
                preparedStatement.setDate(3, User.getDOB());
                preparedStatement.setString(4, User.getUserType().name());
                preparedStatement.setString(5, User.getHashedPassword());
                preparedStatement.setInt(6, User.getUserId());

                int affectedRows = preparedStatement.executeUpdate();

                return affectedRows > 0;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating user. SQL State: " + e.getSQLState() + " Error: " + e.getMessage(), e);
            return false;
        }
    }


    // Delete User
    public boolean deleteUser (int userId){
        try (Connection connection = databaseConfig.getConnection()){
            String sql ="DELETE FROM Users WHERE userId=? ";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1, userId);
                int affectedRow = preparedStatement.executeUpdate();

                if (affectedRow > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            // TODO: handle exception
            logger.log(Level.SEVERE, "Error deleting user. SQL State: " + e.getSQLState() + " Error: " + e.getMessage(), e);
        }
        return false;
    }

    private user mapResultSetToUser(ResultSet resultSet) throws SQLException {
        int userId = resultSet.getInt("userId");
        String fullname = resultSet.getString("fullname");
        String email = resultSet.getString("email");
        Date DOB = resultSet.getDate("DOB");
        String hashedPassword = resultSet.getString("hashedPassword");
        UserType userType = UserType.valueOf(resultSet.getString("userType"));

        return new user(userId,fullname,email,DOB,userType,hashedPassword);
    }
    public String mapUserListToJson (List<user> userList){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            userList.forEach(user -> {
                if (user.getHashedPassword() == null) {
                    user.setHashedPassword("");
                }
            });

            return objectMapper.writeValueAsString(userList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
