package com.defalt.coffeeshop;
import database.Database;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class TestUpdateProduct {
    public static void main(String[] args) {
        Connection c = null;
        Database db = Database.getInstance();
        c=db.getConnection();
        try{
            String updateQuery = "UPDATE product SET name =?,price =? WHERE id =?";
            PreparedStatement statement = c.prepareStatement(updateQuery);
            statement.setString(1, "Oh Leing 1");
            statement.setDouble(2,30);
            statement.setInt(3, 3);
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
