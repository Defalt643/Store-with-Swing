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
import model.ReceiptDetail;
import model.User;

/**
 *
 * @author ming
 */
public class ReceiptDAO implements DAOInterface<Receipt>{

    @Override
    public int add(Receipt object) {
        Connection conn = null;
        Database db = Database.getInstance();
        conn = db.getConnection();
        int id = -1;
        try {
            String sql = "INSERT INTO receipt (customer_id,user_id,total) VALUES (?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, object.getCustomer().getId());
            stmt.setInt(2, object.getSeller().getId());
            stmt.setDouble(3, object.getTotal());
            int row = stmt.executeUpdate();
            ResultSet result = stmt.getGeneratedKeys();
            if (result.next()) {
                id = result.getInt(1);
                object.setId(id);
            }
            for(ReceiptDetail r : object.getReceiptDetail()) {
                String sqlDetail = "INSERT INTO receipt_detail (receipt_id,product_id,price, amount) VALUES (?,?,?,?);";
                PreparedStatement stmtDetail = conn.prepareStatement(sqlDetail);
                stmtDetail.setInt(1, r.getReceipt().getId());
                stmtDetail.setInt(2, r.getProduct().getId());
                stmtDetail.setDouble(3, r.getPrice());
                stmtDetail.setInt(4, r.getAmount());
                int rowDetail = stmt.executeUpdate();
                ResultSet resultDetail = stmt.getGeneratedKeys();
                if (resultDetail.next()) {
                    id = resultDetail.getInt(1);
                    r.setId(id);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error: to create receipt !!! " + ex.getMessage());
        }

        db.close();
        return id;
    }

    @Override
    public ArrayList<Receipt> getAll() {
        ArrayList list = new ArrayList();
        java.sql.Connection conn = null;
        Database db = Database.getInstance();
        conn = db.getConnection();
        try {
            db.getConnection();
            conn.setAutoCommit(false);

            String sql = "SELECT r.id as id,\n"
                    + "       r.created as created,\n"
                    + "       customer_id,\n"
                    + "       c.name as customer_name,\n"
                    + "       c.tel as customer_tel,\n"
                    + "       user_id,\n"
                    + "       u.name as user_name,\n"
                    + "       u.tel as user_tel,\n"
                    + "       total\n"
                    + "  FROM receipt r,customer c, user u\n"
                    + "  WHERE r.customer_id = c.id AND r.user_id = u.id;";
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(sql);

            while (result.next()) {
                int id = result.getInt("id");
                Date created = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(result.getString("created"));
                int customerId = result.getInt("customer_id");
                String customerName = result.getString("customer_name");
                String customerTel = result.getString("customer_tel");
                int userId = result.getInt("user_id");
                String userName = result.getString("user_name");
                String userTel = result.getString("user_tel");
                double total = result.getDouble("total");
                Receipt receipt = new Receipt(id, created, new User(userId, userName, userTel),
                                          new Customer(customerId, customerName, customerTel));
                receipt.setTotal(total);
                list.add(receipt);
            }

            result.close();
            stmt.close();
            db.close();
        } catch (SQLException ex) {
            System.out.println("Error: Unable to select All receipt " + ex.getMessage());
        } catch (ParseException ex) {
            System.out.println("Error: Date passing All receipt " + ex.getMessage());
        }
        return list;
    }

    @Override
    public Receipt get(int id) {
        Connection conn = null;
        Database db = Database.getInstance();
        conn = db.getConnection();
        try {
            String sql = "SELECT r.id as id,\n"
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
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                int rid = result.getInt("id");
                Date created = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(result.getString("created"));
                int customerId = result.getInt("customer_id");
                String customerName = result.getString("customer_name");
                String customerTel = result.getString("customer_tel");
                int userId = result.getInt("user_id");
                String userName = result.getString("user_name");
                String userTel = result.getString("user_tel");
                double total = result.getDouble("total");
                Receipt receipt = new Receipt(rid, created, new User(userId, userName, userTel),
                                          new Customer(customerId, customerName, customerTel));
                 
                 String sqlDetail = "SELECT rd.id as id,\n"
                        + "       receipt_id,\n"
                        + "       product_id,\n"
                        + "       p.name as product_name,\n"
                        + "       p.price as product_price, \n"
                        + "       rd.price as price,\n"
                        + "       amount\n"
                        + "  FROM receipt_detail rd, product p\n"
                        + "  WHERE receipt_id = ? AND rd.product_id = p.id;"
                        + "  ORDER BY created DESC;";
                PreparedStatement stmtDetail = conn.prepareStatement(sqlDetail);
                stmtDetail.setInt(1, id);
                ResultSet resultDetail = stmtDetail.executeQuery();
                while(resultDetail.next()) {
                    int receiveId = resultDetail.getInt("id");
                    int productId = resultDetail.getInt("product_id");
                    String productName = resultDetail.getString("product_name");
                    double productPrice = resultDetail.getDouble("product_price");
                    double price = resultDetail.getDouble("price");
                    int amount = resultDetail.getInt("amount");
                    Product product = new Product(productId, productName, productPrice);
                    receipt.addReceiptDetail(receiveId,product, amount, price);
                }
                receipt.setTotal(total);
                return receipt;
            }
            
            result.close();
            stmt.close();
            db.close();
            
        } catch (SQLException ex) {
            System.out.println("Error: Unable to select receipt id " + id + " !!! " + ex.getMessage());
        } catch (ParseException ex) {
            System.out.println("Error: Date passing receipt id" + id + " !!! "+ ex.getMessage());
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
            String sql = "DELETE FROM receipt WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            row = stmt.executeUpdate();
            System.out.println("Affect row " + row);
        } catch (SQLException ex) {
            System.out.println("Error: Unable delete receipt id " + id + " !!!");
        }

        db.close();
        return row;
    }

    @Override
    public int update(Receipt object) {
        //Connection conn = null;
        //Database db = Database.getInstance();
        //conn = db.getConnection();
        //int row = 0;
        //try {
        //String sql  = "UPDATE product SET name = ?, price = ? WHERE id = ?";
        //PreparedStatement stmt = conn.prepareStatement(sql);
        //stmt.setString(1, object.getName());
        //stmt.setDouble(2, object.getPrice());
        //stmt.setInt(3, object.getId());
        //row = stmt.executeUpdate();
        //System.out.println("Affect row " + row);
        //} catch (SQLException ex) {
        //Logger.getLogger(TestSelectionProduct.class.getName()).log(Level.SEVERE, null, ex);
        //}

        //db.close();
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
        System.out.println("id = " + dao.add(receipt));   
        System.out.println("Get id = " + dao.get(1));
        System.out.println("Receipt after add : " + receipt);
        System.out.println("Get all " +dao.getAll());
        Receipt newReceipt = dao.get(receipt.getId());
        System.out.println(receipt.getId());
        System.out.println("New Receipt : " + newReceipt);

    }

}
