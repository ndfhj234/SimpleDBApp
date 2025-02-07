package com.mycompany.simpledbapp;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Login {
    private static int loginAttempts = 0;
    private static JFrame frame;
    private static JDBCStartup db;
    
    public static void main(String[] args) {
        db = new JDBCStartup("AccountsDB.db");
        showLoginFrame();
    }
    
    public static void showLoginFrame() {
        frame = new JFrame("Login");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel(new GridLayout(3, 2));
        
        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        
        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(new JLabel());
        panel.add(loginButton);
        
        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            
            try {
                JDBCStartup.User user = db.getUser(username, password);
                
                if (user != null) {
                    loginAttempts = 0;
                    frame.dispose();
                    if (user.getUserRole().equals("admin")) {
                        new Admin(user);
                    } else {
                        new Guest(user);
                    }
                } else {
                    loginAttempts++;
                    if (loginAttempts >= 3) {
                        JOptionPane.showMessageDialog(frame, "Failed attempts exceeded 3. Application will now close.");
                        System.exit(0);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid username or password.");
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame,
                    "Database error occurred: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        frame.add(panel);
        frame.setVisible(true);
    }
}
