package com.defalt.coffeeshop;
import database.Database;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Product;
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
                Product product = new Product(id,name,price);
                System.out.println(product);
            }
        }catch(SQLException ex){
            Logger.getLogger(TestSelectProduct.class.getName()).log(Level.SEVERE,null,ex);
        }
        db.close();
    }
}
