package com.defalt.coffeeshop;
import database.Database;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Product;
public class TestDeleteProduct {
    public static void main(String[] args) {
        Connection c = null;
        Database db = Database.getInstance();
        c=db.getConnection();
        try{
            String updateQuery = "DELETE FROM product WHERE id=?";
            Product product = new Product(3,"Oh Lieng",30);
            PreparedStatement statement = c.prepareStatement(updateQuery);
            statement.setInt(1, product.getId());
            int row = statement.executeUpdate();
            System.out.println("Affect row " + row);
        }catch(SQLException ex){
            Logger.getLogger(TestSelectProduct.class.getName()).log(Level.SEVERE,null,ex);
        }
        db.close();
    }
}
