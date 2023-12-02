package com.flexisaf;
import com.flexisaf.controllers.HouseController;
import com.flexisaf.controllers.UserController;
import com.flexisaf.models.*;
import com.flexisaf.models.user.UserType;
import  com.flexisaf.config.databaseConfig;
import com.flexisaf.doa.*;
import java.sql.SQLException;
import java.sql.Connection;


public class Main {
    public static void main(String[] args) {

        Connection connection = null;
        try {
            connection = databaseConfig.getConnection();
            System.out.println("Connected to database ");
            UserController userController = new UserController();

        } catch (SQLException e) {
            // TODO: handle exception
            System.err.println("Failed to connect to database");
            e.printStackTrace();
        }finally{
            databaseConfig.closeConnection(connection);
        }
    }
}
