package com.github.metakol.DBHandler;

import java.sql.*;

public class DBHandler {
    private static String nameConnection="org.sqlite.JDBC";
    public void connection (){
        try {
            Class.forName(nameConnection);
            Connection connection=DriverManager.getConnection("jdbc:sqlite:src/main/resources/com/github/metakol/database/PetDB.db");
            System.out.println("Connected");
            connection.close();
            System.out.println("Closed");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
