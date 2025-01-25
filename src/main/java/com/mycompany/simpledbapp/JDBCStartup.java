/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.simpledbapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

/**
 *
 * @author fonze
 */
public class JDBCStartup {

    public class User {
        String username;
        String password;
        String accessRole;

        public User(String username, String password, String accessRole) {
            this.username = username;
            this.password = password;
            this.accessRole = accessRole;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAccessRole() {
            return accessRole;
        }

        public void setAccessRole(String accessRole) {
            this.accessRole = accessRole;
        }
    }

    private Connection conn;

    public JDBCStartup(String database) {
        String connStr = "jdbc:sqlite:" + database;
        try {
            // Register JDBC driver
            Class.forName("org.sqlite.JDBC");
            
            // Create connection
            conn = DriverManager.getConnection(connStr);
            if (conn != null) {
                System.out.println("Successfully connected to the database!");
                // Get SQLite version to verify connection
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT sqlite_version();");
                if (rs.next()) {
                    System.out.println("SQLite version: " + rs.getString(1));
                }
            }
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            System.err.println(e.toString());
        } catch (SQLException e) {
            System.err.println("Failed to create connection");
            System.err.println(e.toString());
        }
    }

    public ResultSet getAll() throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("select * from users");
    }

    public User getUser(String username, String password) throws SQLException {
        Statement stmt = conn.createStatement();
        // Fix SQL injection vulnerability by using prepared statement
        String sqlStr = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement pstmt = conn.prepareStatement(sqlStr);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
            return new User(rs.getString("username"), rs.getString("password"),
                rs.getString("user_role"));
        }
        return null;
    }

    public static void main(String[] args) {
        // Create database in the current directory
        JDBCStartup jdbc = new JDBCStartup("database.db");
        try {
            // Create a test table and insert some data
            Statement stmt = jdbc.conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS Users (" +
                        "username TEXT PRIMARY KEY," +
                        "password TEXT NOT NULL," +
                        "user_role TEXT)");
            
            System.out.println("Test table created successfully!");
            
            // Try to query the table
            ResultSet rs = jdbc.getAll();
            while(rs.next()) {
                System.out.println("User: " + rs.getString("username") + 
                                 ", Role: " + rs.getString("user_role"));
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
}