package com.defalt.coffeeshop;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestInsertProduct {
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
        
        try{
            String insertQuery = "INSERT INTO product(name,price) VALUES(?,?)";
            PreparedStatement statement = c.prepareStatement(insertQuery);
            statement.setString(1, "Oh Leing");
            statement.setDouble(2,20);
            int row = statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            int id=-1;
            if(result.next()){
                id = result.getInt(1);
            }System.out.println("Affect row " + row + " id "+id);
        }catch(SQLException ex){
            Logger.getLogger(TestSelectProduct.class.getName()).log(Level.SEVERE,null,ex);
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
