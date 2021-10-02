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
import model.Customer;

/**
 *
 * @author ming
 */
public class CustomerDAO implements DAOInterface<Customer> {

    @Override
    public int add(Customer object) {
        Connection c = null;
        Database db = Database.getInstance();
        c = db.getConnection();
        int id = -1;
        try {
            String sql = "INSERT INTO customer (name, tel) VALUES (?,?)";
            PreparedStatement statement = c.prepareStatement(sql);
            statement.setString(1, object.getName());
            statement.setString(2, object.getTel());
            int row = statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                id = result.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestSelectProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        return id;
    }

    @Override
    public ArrayList<Customer> getAll() {
        ArrayList list = new ArrayList();
        Connection conn = null;
        Database db = Database.getInstance();
        conn = db.getConnection();
        try {
            String sql = "SELECT id,name,tel FROM customer";
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String tel = result.getString("tel");
                Customer customer = new Customer(id, name, tel);
                System.out.println(id + " " + name + " " + tel);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestSelectProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        return list;
    }

    @Override
    public Customer get(int id) {
        Connection conn = null;
        Database db = Database.getInstance();
        conn = db.getConnection();
        try {
            String sql = "SELECT id,name,tel FROM customer WHERE id=" + id;
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            if (result.next()) {
                int pid = result.getInt("id");
                String name = result.getString("name");
                String tel = result.getString("tel");
                Customer cus = new Customer(id, name, tel);
                return cus;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestSelectProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int delete(int id) {
        Connection conn = null;
        Database db = Database.getInstance();
        conn = db.getConnection();
        int row = 0;
        try {
            String sql = "DELETE FROM customer WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            row = stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TestSelectProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        return row;
    }

    @Override
    public int update(Customer object) {
        Connection conn = null;
        Database db = Database.getInstance();
        conn = db.getConnection();
        int row = 0;
        try {
            String sql = "UPDATE customer SET name = ?, tel = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, object.getName());
            stmt.setString(2, object.getTel());
            stmt.setInt(3, object.getId());
            row = stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TestSelectProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        return row;
    }

    public static void main(String[] args) {
        CustomerDAO cus = new CustomerDAO();
        System.out.println(cus.getAll());
        System.out.println(cus.get(1));
        int id = cus.add(new Customer(-1, "P'Larm", "0869696969"));
        System.out.println("id: " + id);
        Customer lastUser = cus.get(id);
        System.out.println("Last User : " + lastUser);
        lastUser.setName("P'LarmBig");
        int row = cus.update(lastUser);
        Customer updateUser = cus.get(id);
        System.out.println("Update User : " + updateUser);
        cus.delete(row);
        Customer deleteUser = cus.get(row);
        System.out.println("Delete User : " + deleteUser);
    }
}
