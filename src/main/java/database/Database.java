package database;
//singleton pattern

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    private static Database instance = new Database();
    private Connection c;
    private Database() {
    }

    public static Database getInstance() {
        String dbPath = "./db/CoffeeStore.db";
        try {
            if (instance.c == null || instance.c.isClosed()) {
                Class.forName("org.sqlite.JDBC");
                instance.c = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
                System.out.println("Database connection");
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Error : JDCB is not exist");
        } catch (SQLException ex) {
            System.out.println("Error : Database cannot connection");
        }
        return instance;
    }

    public static void close() {
        try {
            if (instance.c != null || !instance.c.isClosed()) {
                instance.c.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        instance.c = null;
    }public Connection getConnection(){
        return instance.c;
    }
}
