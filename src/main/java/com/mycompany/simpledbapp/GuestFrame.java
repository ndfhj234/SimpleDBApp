package com.mycompany.simpledbapp;

import javax.swing.*;
import java.awt.*;

public class GuestFrame extends JFrame {
    private final JDBCStartup.User user;
    private final LoginFrame loginFrame;
    
    public GuestFrame(JDBCStartup.User user, LoginFrame loginFrame) {
        this.user = user;
        this.loginFrame = loginFrame;
        setupUI();
    }
    
    private void setupUI() {
        setTitle("Welcome Guest");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new BorderLayout());
        
        JLabel welcomeLabel = new JLabel("Welcome, " + user.getUsername() + "!");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(welcomeLabel, BorderLayout.CENTER);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        panel.add(logoutButton, BorderLayout.SOUTH);
        
        add(panel);
    }
    
    private void logout() {
        this.dispose();
        loginFrame.setVisible(true);
    }
} 