/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author ming
 */
public class Receipt {

    private int id;
    private Date created;
    private User seller;
    private Customer customer;
    private ArrayList<ReceiptDetail> receiptDetail;
    private double total = 0;

    public Receipt(int id, Date created, User seller, Customer customer) {
        this.id = id;
        this.created = created;
        this.seller = seller;
        this.customer = customer;
        receiptDetail = new ArrayList<>();
    }

    public Receipt(User seller, Customer customer) {
        this(-1, null, seller, customer);
    }

    public void addReceiptDetail(int id, Product product, int amount, double price) {
        for (int row = 0; row < receiptDetail.size(); row++) {
            ReceiptDetail r = receiptDetail.get(row);
            if (r.getProduct().getId() == product.getId()) {
                r.addAmount(amount);
                return;
            }
        }
        receiptDetail.add(new ReceiptDetail(id, product, amount, price, this));
    }

    public void addReceiptDetail(Product product, int amount) {
        addReceiptDetail(id, product, amount, product.getPrice());
    }

    public void deleteReceiptDetail(int row) {
        receiptDetail.remove(row);
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal() {
        if (this.total == 0) {
            double total = 0;
            for (ReceiptDetail r : receiptDetail) {
                total = total + r.getTotal();
            }
            return total;
        } else {
            return this.total;
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ArrayList<ReceiptDetail> getReceiptDetail() {
        return receiptDetail;
    }

    public void setReceiptDetail(ArrayList<ReceiptDetail> receiptDetail) {
        this.receiptDetail = receiptDetail;
    }

    @Override
    public String toString() {
        String str = "Receipt{" + "id=" + id
                + ", created=" + created
                + ", seller=" + seller
                + ", customer=" + customer
                + ", total =" + this.getTotal()
                + "}\n";
        for (ReceiptDetail r : receiptDetail) {
            str = str + r.toString() + "\n";
        }
        return str;
    }

}
