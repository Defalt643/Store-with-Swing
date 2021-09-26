package com.defalt.coffeeshop;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class TestDeleteProduct {
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
            String updateQuery = "DELETE FROM product WHERE id=?";
            PreparedStatement statement = c.prepareStatement(updateQuery);
            statement.setInt(1, 3);
            int row = statement.executeUpdate();
            System.out.println("Affect row " + row);
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
