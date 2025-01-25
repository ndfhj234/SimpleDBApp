/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.simpledbapp;

import javax.swing.SwingUtilities;

/**
 *
 * @author ddd60
 */
public class SimpleDBApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JDBCStartup dbConnection = new JDBCStartup("db");
            LoginFrame loginFrame = new LoginFrame(dbConnection);
            loginFrame.setVisible(true);
        });
    }
}
