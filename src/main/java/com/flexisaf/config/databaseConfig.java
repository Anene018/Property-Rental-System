package com.flexisaf.config;


import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class databaseConfig {
    private static final Properties property = new Properties();

    static{
        try {
            String filepath = "c:/Users/anene/IdeaProjects/property-rental/src/main/resources/db.properties";
            FileInputStream input = new FileInputStream(filepath);
            property.load(input);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException("Failed to load PostgreSQL JDBC driver");
        }
    }
    public static String getUrl(){
        return property.getProperty("db_url");
    }
    public static String getUser (){
        return property.getProperty("db_user");
    }
    public static String getPassword(){
        return property.getProperty("db_password");
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getUrl(), getUser(), getPassword());
    }

    public static void closeConnection(Connection connection) {
        if(connection != null){
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

