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
import model.User;
/**
 *
 * @author ming
 */
public class UserDAO implements DAOInterface<User>{
    @Override
    public int add(User object) {
        Connection conn = null;
        Database db = Database.getInstance();
        conn = db.getConnection();
        int id = -1;
        try {
            String sql = "INSERT INTO user (name, tel, password) VALUES (?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, object.getName());
            stmt.setString(2, object.getTel());
            stmt.setString(3, object.getPassword());
            int row = stmt.executeUpdate();
            ResultSet result = stmt.getGeneratedKeys();
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
    public ArrayList<User> getAll() {
        ArrayList list = new ArrayList();
        Connection conn = null;
        Database db = Database.getInstance();
        conn = db.getConnection();
        try {
            String sql = "SELECT id,name,tel,password FROM user";
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String tel = result.getString("tel");
                String password = result.getString("password");
                User user = new User(id, name, tel, password);
                System.out.println(id + " " + name + " " + tel + " " + password);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestSelectProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        return list;
    }

    @Override
    public User get(int id) {
        Connection conn = null;
        Database db = Database.getInstance();
        conn = db.getConnection();
        try {
            String sql = "SELECT id,name,tel,password FROM user WHERE id=" + id;
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            if (result.next()) {
                int pid = result.getInt("id");
                String name = result.getString("name");
                String tel = result.getString("tel");
                String password = result.getString("password");
                User user = new User(id, name, tel, password);
                return user;
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
            String sql = "DELETE FROM user WHERE id = ?";
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
    public int update(User object) {
        Connection conn = null;
        Database db = Database.getInstance();
        conn = db.getConnection();
        int row = 0;
        try {
            String sql = "UPDATE user SET name = ?, tel = ?, password = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, object.getName());
            stmt.setString(2, object.getTel());
            stmt.setString(3, object.getPassword());
            stmt.setInt(4, object.getId());
            row = stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TestSelectProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        return row;
    }

    public static void main(String[] args) {
        UserDAO user = new UserDAO();
        System.out.println(user.getAll());
        System.out.println(user.get(1));
        int id = user.add(new User(-1, "P'larm", "0869696969", "password"));
        System.out.println("id: " + id);
        User lastUser = user.get(id);
        System.out.println("Last User : " + lastUser);
        lastUser.setName("P'larmBigD");
        int row = user.update(lastUser);
        User updateUser = user.get(id);
        System.out.println("Update User : " + updateUser);
        user.delete(row);
        User deleteUser = user.get(row);
        System.out.println("Delete User : " + deleteUser);

    }
}
