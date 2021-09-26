package com.defalt.coffeeshop;
import database.Database;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class TestSelectProduct {
    public static void main(String[] args) {
        Connection c = null;
        Database db = Database.getInstance();
        c=db.getConnection();
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
