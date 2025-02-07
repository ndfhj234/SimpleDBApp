package com.mycompany.simpledbapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCStartup {

    public class User {
        String username;
        String password;
        String user_role;

        public User(String username, String password, String user_role) {
            this.username = username;
            this.password = password;
            this.user_role = user_role;
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

        public String getUserRole() {
            return user_role;
        }

        public void setUserRole(String user_role) {
            this.user_role = user_role;
        }
    }

    private Connection conn;

     public JDBCStartup(String database) {
        String connStr = "jdbc:sqlite:" + database;
        try {
            conn = DriverManager.getConnection(connStr);
            System.out.println("Opened database successfully");
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
        ResultSet rs = stmt.executeQuery("SELECT * FROM Users where username = '" + username + "' and password = '" + password + "'");
        if(rs.next())
            return new User(rs.getString("username"), rs.getString("password"),
                rs.getString("user_role"));
        else return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                users.add(new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("user_role")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error getting users: " + e.getMessage());
        }
        return users;
    }

    public static void main(String[] args) {
        try {
            JDBCStartup db = new JDBCStartup("AccountsDB.db");
            db.getAll();
        } catch (SQLException e) {
            System.err.println(e.toString());
        }
    }
}