package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

// xu ly tat ca giao dien ng dung
// menu,in chu,pause,option...
public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40;

    BufferedImage bombImage, menuImage;
    public int commandNumber = 0;
    int subState = 0;    //number of State

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        try {
            bombImage = ImageIO.read(getClass().getResourceAsStream("/res/bomb/fake.png"));
            menuImage = ImageIO.read(getClass().getResourceAsStream("/res/menu.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        // set g2 to use in anothermethods
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        // menu
        if (gp.gameState == gp.titleState) {
            drawTitleScreen(g2);
        }
        // pause
        if (gp.gameState == gp.playState) {
            // do
        }
        if (gp.gameState == gp.optionsState) {
            drawOptionsScreen();
        }
        if (gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }
    }


//    public void drawPauseScreen(Graphics2D g2) {
//        this.g2 = g2;
//        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
//        String text = "PAUSED";
//        int x = getForCenteredtext(text);
//
//        int y = gp.WINDOW_HEIGHT / 2;
//
//        g2.drawString(text, x, y);
//    }


    public void drawTitleScreen(Graphics2D g2) {
        // Ten Game
        // draw menu
        g2.drawImage(menuImage,0,0,gp.WINDOW_WIDTH,gp.WINDOW_HEIGHT,null);
        // draw game name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
        String text = "BOMBERMAN";
        int x = getForCenteredtext(text);
        int y = gp.TILESIZE * 3;

        // bong chu
        g2.setColor(Color.RED);
        g2.drawString(text, x + 8, y + 8);

        // mau chu
        g2.setColor(Color.white);
        g2.drawString(text, x,y);

        // menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));

        text = "NEW GAME";
        x = getForCenteredtext(text);
        y += gp.TILESIZE * 4;
        g2.drawString(text,x,y);
        if (commandNumber == 0) {
            g2.drawString(">",x - gp.TILESIZE, y);
        }

        text = "LOAD GAME";
        x = getForCenteredtext(text);
        y += gp.TILESIZE ;
        g2.drawString(text,x,y);
        if (commandNumber == 1) {
            g2.drawString(">",x - gp.TILESIZE, y);
        }

        text = "QUIT";
        x = getForCenteredtext(text);
        y += gp.TILESIZE;
        g2.drawString(text,x,y);
        if (commandNumber == 2) {
            g2.drawString(">",x - gp.TILESIZE, y);
        }
    }

    public void drawOptionsScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        // design
        int frameX = gp.TILESIZE * 8;
        int frameY = gp.TILESIZE;
        int frameWidth = gp.TILESIZE * 8;
        int frameHeight = gp.TILESIZE * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0: options_top(frameX, frameY);
                break;
//            case 1: /* full screen */
//                break;
            case 1:
                options_control(frameX, frameY);
                break;
            case 2:
                options_endGameConfirmation(frameX, frameY);
                break;
        }

        gp.input.enterPressed = false;
    }

    public void options_top(int frameX, int frameY) {
        int textX;
        int textY;

        // option:
        String text = "Options";
        textX = getForCenteredtext(text) - 30;
        textY = frameY + gp.TILESIZE;
        g2.drawString(text, textX, textY);

//        // full screen on/off
//        textX = frameX + gp.TILESIZE;
//        textY += gp.TILESIZE * 2;
//        g2.drawString("Full Screen", textX, textY);
//        if (commandNumber == 0) {
//            g2.drawString(">", textX - 25, textY);
//            if (gp.input.enterPressed == true) {
//                if (gp.fullScreenOn == false) {
//                    gp.fullScreenOn = true;
//                } else if (gp.fullScreenOn == true) {
//                    gp.fullScreenOn = false;
//                }
//            }
//        }

        // music
        textX = frameX + gp.TILESIZE;
        textY += gp.TILESIZE * 2;
//        textY += gp.TILESIZE;
        g2.drawString("Music", textX, textY);
        if (commandNumber == 0) {
            g2.drawString(">", textX - 25, textY);
        }

        // se
        textY += gp.TILESIZE;
        g2.drawString("SE", textX, textY);
        if (commandNumber == 1) {
            g2.drawString(">", textX - 25, textY);
        }

        // control
        textY += gp.TILESIZE;
        g2.drawString("Control", textX, textY);
        if (commandNumber == 2) {
            g2.drawString(">", textX - 25, textY);
            if (gp.input.enterPressed == true) {   // go to control
                subState = 1;
                commandNumber = 0;
            }
        }

        // end
        textY += gp.TILESIZE;
        g2.drawString("End Game", textX, textY);
        if (commandNumber == 3) {
            g2.drawString(">", textX - 25, textY);
            if (gp.input.enterPressed == true) {
                subState = 2;
                commandNumber = 0;
            }
        }

        // back
        textY += gp.TILESIZE * 2;
        g2.drawString("Back", textX, textY);
        if (commandNumber == 4) {
            g2.drawString(">", textX - 25, textY);
            if (gp.input.enterPressed == true) {
                gp.gameState = gp.playState;
                commandNumber = 0;
            }
        }

//        // full screen check
//        textX = frameX + gp.TILESIZE * 5 - 12;
//        textY = frameY + gp.TILESIZE * 2 + 24;
//        g2.setStroke(new BasicStroke(3));
//        g2.drawRect(textX, textY,24,24);
//        // handle fullscreen notyet?
//        if(gp.fullScreenOn == true) {
//            g2.fillRect(textX, textY, 24, 24);
//        }

        // volume +/-
        textX = frameX + gp.TILESIZE * 5 - 12;
        textY = frameY + gp.TILESIZE * 2 + 24;
//        textY += gp.TILESIZE;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY,120,24);     // (120/5=24)
        int volumeWidth = 24 * gp.sound.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        // se +/-
        textY += gp.TILESIZE;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY,120,24);
        int volWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volWidth, 24);
    }

    public void drawSubWindow(int x, int y, int width, int height) {

        Color c = new Color(0,0,0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y ,width, height, 35, 35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    // tutorial
    public void options_control(int frameX, int frameY) {
        int textX;
        int textY;

        // Title
        String text = "Control";
        textX = getForCenteredtext(text) - 30;
        textY = frameY + gp.TILESIZE;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.TILESIZE;
        textY += gp.TILESIZE * 2;
        g2.drawString("Move", textX, textY);

        textY += gp.TILESIZE;
        g2.drawString("Bomb", textX, textY);

        textY += gp.TILESIZE;
        g2.drawString("Options", textX, textY);

        // tutorial
        textX = frameX + gp.TILESIZE * 6 - gp.TILESIZE;
        textY = frameY + gp.TILESIZE * 3;
        g2.drawString("WASD", textX, textY);

        textY += gp.TILESIZE;
        g2.drawString("J", textX, textY);

        textY += gp.TILESIZE;
        g2.drawString("P", textX, textY);

        // back
        textX = frameX + gp.TILESIZE + 30;
        textY = frameY + gp.TILESIZE * 9;
        g2.drawString("Back", textX, textY);
        if (commandNumber == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.input.enterPressed == true) {
                subState = 0;
                commandNumber = 2;
            }
        }
    }

    // out?
    public void options_endGameConfirmation(int frameX, int frameY) {
        int textX = frameX + gp.TILESIZE;
        int textY = frameY + gp.TILESIZE * 2;

        String s = "Quit the game \nand return to the \ntitle screen?";

        for (String line: s.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // YES
        String textYes = "Yes";
        textX = getForCenteredtext(textYes) - gp.TILESIZE;
        textY += gp.TILESIZE * 3;
        g2.drawString(textYes, textX, textY);
        if (commandNumber == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.input.enterPressed == true) {
                subState = 0;
                gp.gameState = gp.titleState;
                gp.stopMusic();
                gp.playMusic(3);
                gp.setupGame();
            }
        }

        // NO
        String textNo = "No";
        textY += gp.TILESIZE;
        g2.drawString(textNo, textX, textY);
        if (commandNumber == 1) {
            g2.drawString(">", textX - 25, textY);
            if (gp.input.enterPressed == true) {
                subState = 0;
                commandNumber = 3;
            }
        }
    }

    public void drawGameOverScreen() {
        // make dark
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.WINDOW_WIDTH,gp.WINDOW_HEIGHT);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        // make shadow
        text = "Game Over";
        g2.setColor(Color.black);
        x = getForCenteredtext(text);
        y = gp.TILESIZE * 4;
        g2.drawString(text, x , y);

        // main
        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);

        // retry
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getForCenteredtext(text);
        y += gp.TILESIZE * 4;
        g2.drawString(text, x , y);
        if (commandNumber == 0) {
            g2.drawString(">", x - 40, y);
        }

        // back to menu
        text = "Quit";
        x = getForCenteredtext(text);
        y += 55;
        g2.drawString(text, x , y);
        if (commandNumber == 1) {
            g2.drawString(">", x - 40, y);
        }
    }


//    public void drawItem(Graphics2D g2) {
//        g2.drawImage(bombImage, 0, 0, gp.TILESIZE, gp.TILESIZE,null);
//    }
    // Get middle pos of the screen.
    public int getForCenteredtext(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.WINDOW_WIDTH/2 - length/2;
        return x;
    }
}
