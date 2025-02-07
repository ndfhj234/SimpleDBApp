package com.mycompany.simpledbapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Admin {
    private JFrame frame;
    private JDBCStartup.User user;
    private JDBCStartup db;
    
    public Admin(JDBCStartup.User user) {
        this.user = user;
        this.db = new JDBCStartup("AccountsDB.db");
        createAdminWindow();
    }
    
    public void createAdminWindow() {
        frame = new JFrame("Admin");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"Username", "Role"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable userTable = new JTable(model);
        
        try {
            ResultSet users = db.getAll();
            while (users.next()) {
                model.addRow(new Object[]{
                    users.getString("username"),
                    users.getString("user_role")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error loading users: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            frame.dispose();
            Login.showLoginFrame();
        });
        
        panel.add(new JScrollPane(userTable), BorderLayout.CENTER);
        panel.add(logoutButton, BorderLayout.SOUTH);
        
        frame.add(panel);
        frame.setVisible(true);
    }
}
