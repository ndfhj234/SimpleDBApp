package com.mycompany.simpledbapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageLabel;
    private final JDBCStartup dbConnection;
    private int loginAttempts = 0;
    
    public LoginFrame(JDBCStartup dbConnection) {
        this.dbConnection = dbConnection;
        setupUI();
    }
    
    private void setupUI() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Username field
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);
        
        // Password field
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);
        
        // Login button
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        loginButton = new JButton("Login");
        panel.add(loginButton, gbc);
        
        // Message label
        gbc.gridy = 3;
        messageLabel = new JLabel(" ");
        messageLabel.setForeground(Color.RED);
        panel.add(messageLabel, gbc);
        
        loginButton.addActionListener(e -> handleLogin());
        
        add(panel);
    }
    
    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        try {
            JDBCStartup.User user = dbConnection.getUser(username, password);
            if (user != null) {
                loginAttempts = 0;
                openAppropriateWindow(user);
            } else {
                loginAttempts++;
                if (loginAttempts >= 3) {
                    JOptionPane.showMessageDialog(this, 
                        "Too many failed attempts. Application will close.", 
                        "Login Failed", 
                        JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
                messageLabel.setText("Invalid username or password. Attempts left: " + (3 - loginAttempts));
            }
        } catch (SQLException ex) {
            messageLabel.setText("Database error: " + ex.getMessage());
        }
    }
    
    private void openAppropriateWindow(JDBCStartup.User user) {
        this.setVisible(false);
        if ("admin".equals(user.getAccessRole())) {
            new AdminFrame(dbConnection, this).setVisible(true);
        } else {
            new GuestFrame(user, this).setVisible(true);
        }
    }
} 