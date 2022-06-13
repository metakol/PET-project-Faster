package com.github.metakol.DBHandler;

import java.sql.*;

public class DBHandler implements AutoCloseable {
    private static String nameConnection = "org.sqlite.JDBC";
    private Connection connection;

    public DBHandler() {
        try {
            Class.forName(nameConnection);
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/com/github/metakol/database/PetDB.db");
            System.out.println("Connected");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Closed");
    }
    public ResultSet executeQueryStatement(String sqlQuery){
        ResultSet resultSet;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }
    public void executeUpdateStatement(String sqlQuery){
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
