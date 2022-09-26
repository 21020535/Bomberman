/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXSwingMain.java to edit this template
 */
package main;

import java.awt.Color;

import javax.swing.JFrame;

/**
 *
 * @author Lenovo
 */
public class Game {
    public static void main(String[] args) {
        // basically swing seperates window frame and the content inside the frame
        // (which is panel)
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Bomberman ver IDK what it is");
        
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        
        // make the window's size to fit the size of the panel
        window.pack();

        // null -> make the window appear in the middle of the screen
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}
