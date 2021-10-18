/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ming
 */
public class ReceiptDetail {

    private int id;
    private Product product;
    private int amount;
    private double price;
    private Receipt receipt;

    public ReceiptDetail(int id, Product product, int amount, double price, Receipt receipt) {
        this.id = id;
        this.product = product;
        this.amount = amount;
        this.price = price;
        this.receipt = receipt;
    }

    public ReceiptDetail(Product product, int amount, double price, Receipt receipt) {
        this(-1, product, amount, price, receipt);

    }

    public double getTotal() {
        return amount * price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

}
