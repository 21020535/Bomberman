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
    public boolean up, down, left, right, bomb, pause, enterPressed = false;

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
            titleState(event);
        } else if (gp.gameState == gp.optionsState) {
            optionsState(event);
        } else if (gp.gameState == gp.gameOverState) {
            gameOverState(event);
        }


        if (event == KeyEvent.VK_P) {
            // pause = !pause;
            if (gp.gameState == gp.playState) {
                gp.gameState = gp.optionsState;
            } else {
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
        // if (event == KeyEvent.VK_R) {
        //     switch (gp.curMap) {
        //         case 0:
        //             gp.tileManager.loadMap("/res/map/map2.txt", 0);
        //             break;
        //     }
        // }

        if (event == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        
    }

    private void titleState(int event) {
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
                gp.stopMusic();
                gp.playMusic(0);
            }

            if (gp.ui.commandNumber == 1) {
            }

            if (gp.ui.commandNumber == 2) {
                System.exit(0);
            }
        }
    }

    private void optionsState(int event) {
        if (event == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        int maxCommandNum = 0;
        switch (gp.ui.subState) {
            case 0:
                maxCommandNum = 4;
                break;
            case 2: // why?
                maxCommandNum = 1;
                break;
        }

        if (event == KeyEvent.VK_W) {
            gp.ui.commandNumber--;
            gp.playSE(5);
            if (gp.ui.commandNumber < 0) {
                gp.ui.commandNumber = maxCommandNum;
            }
        }
        if (event == KeyEvent.VK_S) {
            gp.ui.commandNumber++;
            gp.playSE(5);
            if (gp.ui.commandNumber > maxCommandNum) {
                gp.ui.commandNumber = 0;
            }
        }

        if (event == KeyEvent.VK_A) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNumber == 0 && gp.sound.volumeScale > 0) {
                    gp.sound.volumeScale--;
                    gp.sound.checkVolume();
                    gp.playSE(5);
                }
                if (gp.ui.commandNumber == 1 && gp.se.volumeScale > 0) {
                    gp.se.volumeScale--;
                    gp.playSE(5);
                }
            }
        }
        if (event == KeyEvent.VK_D) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNumber == 0 && gp.sound.volumeScale < 5) {
                    gp.sound.volumeScale++;
                    gp.sound.checkVolume();
                    gp.playSE(5);
                }
                if (gp.ui.commandNumber == 1 && gp.se.volumeScale < 5) {
                    gp.se.volumeScale++;
                    gp.playSE(5);
                }
            }
        }
    }

    private void gameOverState(int event) {
        if (event == KeyEvent.VK_W) {
            gp.ui.commandNumber--;
            if (gp.ui.commandNumber < 0) {
                gp.ui.commandNumber = 1;
            }
            gp.playSE(5);
        }
        if (event == KeyEvent.VK_S) {
            gp.ui.commandNumber++;
            if (gp.ui.commandNumber > 1) {
                gp.ui.commandNumber = 0;
            }
            gp.playSE(5);
        }
        if (event == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNumber == 0) {
                gp.setupGame();
                gp.gameState = gp.playState;
                gp.playMusic(0);
            } else if (gp.ui.commandNumber == 1) {
                gp.gameState = gp.titleState;
            }
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
