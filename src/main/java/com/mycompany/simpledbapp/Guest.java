package com.mycompany.simpledbapp;

import javax.swing.*;
import java.awt.*;

public class Guest {
    private JFrame frame;
    private JDBCStartup.User user;
    
    public Guest(JDBCStartup.User user) {
        this.user = user;
        createGuestWindow();
    }
    
    public void createGuestWindow() {
        frame = new JFrame("Guest");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel(new BorderLayout());
        
        JLabel welcomeLabel = new JLabel("Welcome, " + user.getUsername());
        JButton logoutButton = new JButton("Logout");
        
        logoutButton.addActionListener(e -> {
            frame.dispose();
            Login.showLoginFrame();
        });
        
        panel.add(welcomeLabel, BorderLayout.CENTER);
        panel.add(logoutButton, BorderLayout.SOUTH);
        
        frame.add(panel);
        frame.setVisible(true);
    }
}
