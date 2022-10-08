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
public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean up, down, left, right, bomb;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    // type là event
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
     // khi ấn
    @Override
    public void keyPressed(KeyEvent e) {
        int event = e.getKeyCode();
        // Menu
        if (gp.gameState == gp.titleState) {
            if (event == KeyEvent.VK_W) {
                gp.ui.commandNumber--;
                if (gp.ui.commandNumber < 0) {
                    gp.ui.commandNumber = 2;
                }
            }

            if (event == KeyEvent.VK_S) {
                gp.ui.commandNumber++;
                if (gp.ui.commandNumber > 2) {
                    gp.ui.commandNumber = 0;
                }
            }

            if (event == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNumber == 0) {
                    gp.gameState = gp.playState;
//                    gp.stopMusic();
//                    gp.playMusic(0);
                }

                if (gp.ui.commandNumber == 1) {
                }

                if (gp.ui.commandNumber == 2) {
                    System.exit(0);
                }
            }
        }
        if (event == KeyEvent.VK_P) {
            if (gp.gameState == gp.playState) {
                gp.gameState = gp.pauseState;
            } else if (gp.gameState == gp.pauseState) {
                gp.gameState = gp.playState;
            }
        }

        // di chuyen
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
