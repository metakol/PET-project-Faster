package com.github.metakol.DBHandler;

import java.sql.*;

public class DBHandler {
    private static String nameConnection = "org.sqlite.JDBC";
    private Connection connection;

    public void DBHandler() {

    }

    public void open() {
        try {
            Class.forName(nameConnection);
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/com/github/metakol/database/PetDB.db");
            System.out.println("Connected");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Closed");
    }

    public Statement getStatement() throws SQLException {
        return connection.createStatement();
    }

}
