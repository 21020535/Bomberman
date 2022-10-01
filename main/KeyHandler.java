/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Lenovo
 */
// hàm để nhận đầu vào là 1 sụ kiện chuột
public class KeyHandler implements KeyListener{
    public boolean up, down, left, right, bomb;

    // type là event
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
     // khi ấn
    @Override
    public void keyPressed(KeyEvent e) {
        int event = e.getKeyCode();
        
        if (event == KeyEvent.VK_W) {
            up = true;
        }
        if (event == KeyEvent.VK_A) {
            left = true;
        }
        if (event == KeyEvent.VK_S) {
            down = true;
        }
        if (event == KeyEvent.VK_D) {
            right = true;
        }
        if (event == KeyEvent.VK_J) {
            bomb = true;
        }
    }

    // khi thả
    @Override
    public void keyReleased(KeyEvent e) {
        int event = e.getKeyCode();
        
        if (event == KeyEvent.VK_W) {
            up = false;
        }
        if (event == KeyEvent.VK_A) {
            left = false;
        }
        if (event == KeyEvent.VK_S) {
            down = false;
        }
        if (event == KeyEvent.VK_D) {
            right = false;
        }
        if (event == KeyEvent.VK_J) {
            bomb = false;

        }
    }
    
}
