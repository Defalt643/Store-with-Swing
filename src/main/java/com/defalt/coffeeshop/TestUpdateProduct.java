package com.defalt.coffeeshop;
import database.Database;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Product;
public class TestUpdateProduct {
    public static void main(String[] args) {
        Connection c = null;
        Database db = Database.getInstance();
        c=db.getConnection();
        try{
            String updateQuery = "UPDATE product SET name =?,price =? WHERE id =?";
            PreparedStatement statement = c.prepareStatement(updateQuery);
            Product product = new Product(4,"Oh Leing",30);
            statement.setString(1, product.getName());
            statement.setDouble(2,product.getPrice());
            statement.setInt(3, product.getId());
            int row = statement.executeUpdate();
            System.out.println("Affect row " + row);
        }catch(SQLException ex){
            Logger.getLogger(TestSelectProduct.class.getName()).log(Level.SEVERE,null,ex);
        }
        db.close();
    }
}
