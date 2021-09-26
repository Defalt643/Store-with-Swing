package com.defalt.coffeeshop;

import database.Database;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Product;

public class TestInsertProduct {
    public static void main(String[] args) {
        Connection c = null;
        Database db = Database.getInstance();
        c=db.getConnection();
        try{
            String insertQuery = "INSERT INTO product(name,price) VALUES(?,?)";
            PreparedStatement statement = c.prepareStatement(insertQuery);
            Product product = new Product(-1,"Oh Leing",20);
            statement.setString(1, product.getName());
            statement.setDouble(2,product.getPrice());
            int row = statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            int id=-1;
            if(result.next()){
                id = result.getInt(1);
            }System.out.println("Affect row " + row + " id "+id);
        }catch(SQLException ex){
            Logger.getLogger(TestSelectProduct.class.getName()).log(Level.SEVERE,null,ex);
        }
        db.close();
    }
}
