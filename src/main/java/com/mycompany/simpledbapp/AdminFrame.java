package com.mycompany.simpledbapp;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminFrame extends JFrame {
    private final JDBCStartup dbConnection;
    private final LoginFrame loginFrame;
    private JTextArea userListArea;
    
    public AdminFrame(JDBCStartup dbConnection, LoginFrame loginFrame) {
        this.dbConnection = dbConnection;
        this.loginFrame = loginFrame;
        setupUI();
        loadUsers();
    }
    
    private void setupUI() {
        setTitle("Admin Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new BorderLayout());
        
        userListArea = new JTextArea();
        userListArea.setEditable(false);
        panel.add(new JScrollPane(userListArea), BorderLayout.CENTER);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        panel.add(logoutButton, BorderLayout.SOUTH);
        
        add(panel);
    }
    
    private void loadUsers() {
        try {
            ResultSet rs = dbConnection.getAll();
            StringBuilder users = new StringBuilder();
            users.append("User List:\n\n");
            while (rs.next()) {
                users.append("Username: ").append(rs.getString("username"))
                     .append(", Role: ").append(rs.getString("user_role"))
                     .append("\n");
            }
            userListArea.setText(users.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading users: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void logout() {
        this.dispose();
        loginFrame.setVisible(true);
    }
} 