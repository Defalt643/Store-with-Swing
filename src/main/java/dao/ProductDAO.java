/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.defalt.coffeeshop.TestSelectProduct;
import database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Product;

/**
 *
 * @author ming
 */
public class ProductDAO implements DAOInterface<Product>{

    @Override
    public int add(Product object) {
        Connection c = null;
        Database db = Database.getInstance();
        c=db.getConnection();
        int id=-1;
        try{
            String insertQuery = "INSERT INTO product(name,price) VALUES(?,?)";
            PreparedStatement statement = c.prepareStatement(insertQuery);
            statement.setString(1, object.getName());
            statement.setDouble(2,object.getPrice());
            int row = statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if(result.next()){
                id = result.getInt(1);
            }
        }catch(SQLException ex){
            Logger.getLogger(TestSelectProduct.class.getName()).log(Level.SEVERE,null,ex);
        }
        db.close();
        return id;
    }

    @Override
    public ArrayList<Product> getAll() {
        ArrayList list = new ArrayList();
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
                list.add(product);  
            }
        }catch(SQLException ex){
            Logger.getLogger(TestSelectProduct.class.getName()).log(Level.SEVERE,null,ex);
        }
        db.close();
        return list;
    }

    @Override
    public Product get(int id) {
        Connection c = null;
        Database db = Database.getInstance();
        c=db.getConnection();
        try{
            String query = "SELECT id,name,price FROM Product WHERE id="+id;
            Statement stmt = c.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while(result.next()){
                int pid = result.getInt("id");
                String name = result.getString("name");
                double price = result.getDouble("price");
                Product product = new Product(pid,name,price);
                return product;
            }
        }catch(SQLException ex){
            Logger.getLogger(TestSelectProduct.class.getName()).log(Level.SEVERE,null,ex);
        }
        db.close();
        return null;
    }

    @Override
    public int delete(int id) {
        Connection c = null;
        Database db = Database.getInstance();
        c=db.getConnection();
        int row=0;
        try{
            String updateQuery = "DELETE FROM product WHERE id=?";
            Product product = new Product(id,"Oh Lieng",30);
            PreparedStatement statement = c.prepareStatement(updateQuery);
            statement.setInt(1, product.getId());
            row = statement.executeUpdate();
        }catch(SQLException ex){
            Logger.getLogger(TestSelectProduct.class.getName()).log(Level.SEVERE,null,ex);
        }
        db.close();
        return row;
    }

    @Override
    public int update(Product object) {
        Connection c = null;
        Database db = Database.getInstance();
        c=db.getConnection();
        int row = 0;
        try{
            String updateQuery = "UPDATE product SET name =?,price =? WHERE id =?";
            PreparedStatement statement = c.prepareStatement(updateQuery);
            Product product = new Product(object.getId(),object.getName(),object.getPrice());
            statement.setString(1, product.getName());
            statement.setDouble(2,product.getPrice());
            statement.setInt(3, product.getId());
            row = statement.executeUpdate();
            System.out.println("Affect row " + row);
        }catch(SQLException ex){
            Logger.getLogger(TestSelectProduct.class.getName()).log(Level.SEVERE,null,ex);
        }
        db.close();
        return row;
    }
    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();
        System.out.println(dao.getAll());
        System.out.println(dao.get(1));
        int id =  dao.add(new Product(-1,"KrafaeYen",50.0));
        System.out.println("id " + id);
        Product lastProduct = dao.get(id);
        System.out.println("last Product:"+lastProduct);
        lastProduct.setPrice(100);
        int row  = dao.update(lastProduct);
        Product updateProduct = dao.get(id);
        System.out.println("update product:"+updateProduct);
        dao.delete(id);
        Product deleteProduct = dao.get(id);
        System.out.println("delete product:"+deleteProduct);
    }
}
