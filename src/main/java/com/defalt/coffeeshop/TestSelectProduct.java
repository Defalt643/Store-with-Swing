package com.defalt.coffeeshop;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class TestSelectProduct {
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
            String query = "SELECT id,name,price FROM Product";
            Statement stmt = c.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while(result.next()){
                int id = result.getInt("id");
                String name = result.getString("name");
                double price = result.getDouble("price");
                System.out.println(+id+" "+name+" "+price);
            }
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
