package com.defalt.coffeeshop;
import java.sql.*;
public class TestConnection {
    public static void main(String[] args) {
        Connection c = null;
        String dbPath = "./db/CoffeeStore.db";
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            System.out.println("Database connection");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error : JDCB is not exist");
        } catch (SQLException ex) {
            System.out.println("Error : Database cannot connection");
        }

        try {
            if (c != null) {
                c.close();
            }

        } catch (SQLException ex) {
            System.out.println("Error : Cannot close database");
        }
    }
}
