/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.defalt.coffeeshop;

import model.Customer;
import model.Product;
import model.Receipt;
import model.User;

/**
 *
 * @author ming
 */
public class TestReceipt {

    public static void main(String[] args) {
        Product p1 = new Product(1, "Chayen", 30);
        Product p2 = new Product(2, "Americano", 40);
        User seller = new User("Naded", "88888888", "password");
        Customer customer = new Customer("Pawit", "9999999");
        Receipt receipt = new Receipt(seller, customer);
        receipt.addReceiptDetail(p1, 1);
        receipt.addReceiptDetail(p2, 3);
        System.out.println(receipt);
        receipt.deleteReceiptDetail(0);
        System.out.println(receipt);
        receipt.addReceiptDetail(p1, 2);
        System.out.println(receipt);
        receipt.addReceiptDetail(p1, 2);
        System.out.println(receipt);
    }
}