package com.defalt.coffeeshop;
import database.Database;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class TestDeleteProduct {
    public static void main(String[] args) {
        Connection c = null;
        Database db = Database.getInstance();
        c=db.getConnection();
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
