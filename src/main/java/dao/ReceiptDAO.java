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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Customer;
import model.Product;
import model.Receipt;
import model.User;

/**
 *
 * @author ming
 */
public class ReceiptDAO implements DAOInterface<Receipt>{

    @Override
    public int add(Receipt object) {
        Connection c = null;
        Database db = Database.getInstance();
        c = db.getConnection();
        int id = -1;
        try {
            String insertQuery = "INSERT INTO receipt(customer_id, user_id, total) VALUES (?,?,?)";
            PreparedStatement statement = c.prepareStatement(insertQuery);
            statement.setInt(1, object.getCustomer().getId());
            statement.setInt(2, object.getSeller().getId());
            statement.setDouble(3, object.getTotal());
            int row = statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                id = result.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error : to create receipt");
        }
        db.close();
        return id;
    }

    @Override
    public ArrayList<Receipt> getAll() {
        ArrayList list = new ArrayList();
        Connection c = null;
        Database db = Database.getInstance();
        c = db.getConnection();
        try {
            String query = "SELECT r.id as id,\n"
                    + "       created,\n"
                    + "       customer_id,\n"
                    + "       c.name as customer_name,\n"
                    + "       c.tel as customer_tel,\n"
                    + "       user_id,      \n"
                    + "       u.name as user_name,\n"
                    + "       u.tel as user_tel,\n"
                    + "       total     \n"
                    + "  FROM Receipt r, Customer c, User u\n"
                    + "  WHERE r.customer_id = c.id AND r.user_id = u.id;";
            Statement stmt = c.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                int id = result.getInt("id");
                Date created = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(result.getString("created"));
                int customerId = result.getInt("customer_id");
                String customerName = result.getString("customer_name");
                String customerTel = result.getString("customer_Tel");
                int userId = result.getInt("user_id");
                String userName = result.getString("user_name");
                String userTel = result.getString("user_Tel");
                double total = result.getDouble("total");
                Receipt receipt = new Receipt(id, created,
                        new User(userId, userName, userTel),
                        new Customer(customerId, customerName, customerTel)
                );
                list.add(receipt);
            }
        } catch (SQLException ex) {
            System.out.println("Error : Unable to select all receipt!!!" + ex.getMessage());
        } catch (ParseException ex) {
            System.out.println("Error : Date parsing all receipt!!!" + ex.getMessage());
        }
        db.close();
        return list;
    }

    @Override
    public Receipt get(int id) {
        Connection c = null;
        Database db = Database.getInstance();
        c = db.getConnection();
        try {
            String query = "SELECT r.id as id,\n"
                    + "       r.created as created,\n"
                    + "       customer_id,\n"
                    + "       c.name as customer_name,\n"
                    + "       c.tel as customer_tel,\n"
                    + "       user_id,\n"
                    + "       u.name as user_name,\n"
                    + "       u.tel as user_tel,\n"
                    + "       total\n"
                    + "  FROM receipt r,customer c, user u\n"
                    + "  WHERE r.id = ? AND r.customer_id = c.id AND r.user_id = u.id"
                    + "  ORDER BY created DESC;";

            PreparedStatement statement = c.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery(query);
            if (result.next()) {
                int rid = result.getInt("id");
                Date created = result.getDate("created");
                int customerId = result.getInt("customer_id");
                String customerName = result.getString("customer_name");
                String customerTel = result.getString("customer_Tel");
                int userId = result.getInt("user_id");
                String userName = result.getString("user_name");
                String userTel = result.getString("user_Tel");
                double total = result.getDouble("total");
                Receipt receipt = new Receipt(rid, created,
                        new User(userId, userName, userTel),
                        new Customer(customerId, customerName, customerTel)
                );
                return receipt;
            }
        } catch (SQLException ex) {
            System.out.println("Error : Unable to select receipt id " + id + " !!!");

        }
        db.close();
        return null;
    }

    @Override
    public int delete(int id) {
        Connection c = null;
        Database db = Database.getInstance();
        c = db.getConnection();
        int row = 0;
        try {
            String updateQuery = "DELETE FROM receipt WHERE id=?";
            PreparedStatement statement = c.prepareStatement(updateQuery);
            statement.setInt(1, id);
            row = statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error : Unable to delete receipt id " + id + " !!!");
        }
        db.close();
        return row;
    }

    @Override
    public int update(Receipt object) {
//        Connection c = null;
//        Database db = Database.getInstance();
//        c = db.getConnection();
//        int row = 0;
//        try {
//            String updateQuery = "UPDATE product SET name =?,price =? WHERE id =?";
//            PreparedStatement statement = c.prepareStatement(updateQuery);
//            Product product = new Product(object.getId(), object.getName(), object.getPrice());
//            statement.setString(1, product.getName());
//            statement.setDouble(2, product.getPrice());
//            statement.setInt(3, product.getId());
//            row = statement.executeUpdate();
//            System.out.println("Affect row " + row);
//        } catch (SQLException ex) {
//            Logger.getLogger(TestSelectProduct.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        db.close();
        return 0;
    }

    public static void main(String[] args) {
        Product p1 = new Product(2, "Americano", 30);
        Product p2 = new Product(1, "Espresso", 30);
        User seller = new User(2, "Somchai", "0969696969");
        Customer customer = new Customer(4, "P'LarmBig", "0890453210");
        Receipt receipt = new Receipt(seller, customer);
        receipt.addReceiptDetail(p1, 1);
        receipt.addReceiptDetail(p2, 3);
        System.out.println(receipt);
        ReceiptDAO dao = new ReceiptDAO();
        System.out.println("id =" + dao.add(receipt));
        System.out.println(dao.getAll());
    }

}
