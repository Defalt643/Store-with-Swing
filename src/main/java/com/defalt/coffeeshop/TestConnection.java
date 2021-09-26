package com.defalt.coffeeshop;
import database.Database;
import java.sql.*;
public class TestConnection {
    public static void main(String[] args) {
        Connection c = null;
        Database db = Database.getInstance();
        db.close();
    }
}
