/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.simpledbapp;
/**
 *
 * @author ddd60
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class SimpleDBApp {
    public static void main(String[] args) {
        // Create database connection
        JDBCStartup jdbc = new JDBCStartup("C:\\Users\\ddd60\\OneDrive\\Desktop\\sqlite-db\\db");
        
        try {
            // Get all rows from accounts table
            ResultSet rs = jdbc.getAll();
            
            // Print table header
            System.out.println("\nUsers Table Contents:");
            System.out.println("------------------------");
            System.out.println("Username | Password | User Role");
            System.out.println("------------------------");
            
            // Print each row
            while(rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String role = rs.getString("user_role");
                System.out.printf("%s | %s | %s%n", username, password, role);
            }
            
        } catch (SQLException e) {
            System.err.println("Error reading from database: " + e.getMessage());
        }
    }
}
