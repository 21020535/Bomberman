/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;

/**
 *
 * @author Lenovo
 */
public class Player extends Entity {

    GamePanel gp;
    KeyHandler input;

    public Player(GamePanel gp, KeyHandler input) {
        this.gp = gp;
        this.input = input;
        setDefaultValues();
        solidArea = new Rectangle(2, 2, gp.TILESIZE - 4, gp.TILESIZE - 4);
        getPlayerImage();
    }

    public void getPlayerImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/player2.png"));
            // up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_up_1.png"));
            // up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_up_2.png"));
            // down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_down_1.png"));
            // down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_down_2.png"));
            // left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_left_1.png"));
            // left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_left_2.png"));
            // right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_right_1.png"));
            // right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_right_2.png"));
            // down1 = tmp.getSubimage(0, 0, 32, 32);
            // down2 = tmp.getSubimage(32, 0, 32, 32);
            // left1 = tmp.getSubimage(64, 0, 32, 32);
            // left2 = tmp.getSubimage(32 * 3, 0, 32, 32);
            // right1 = tmp.getSubimage(32 * 4, 0, 32, 32);
            // right2 = tmp.getSubimage(32 * 5, 0, 32, 32);
            // up1 = tmp.getSubimage(32 * 6, 0, 32, 32);
            // up2 = tmp.getSubimage(32 * 7, 0, 32, 32);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        x = 500;
        y = 500;
        speed = 4;
        direction = "down";
        tick = 0;
        maxFrame = 4;
        begin = 0;
        interval = 7;
    }

    public void update() {
        if (input.up == true || input.down == true
                || input.left == true || input.right == true) {
            if (input.up == true) {
                direction = "up";
            }
            if (input.down == true) {
                direction = "down";
            }
            if (input.left == true) {
                direction = "left";
            }
            if (input.right == true) {
                direction = "right";
            }
            
            collide = false;
            gp.cChecker.checkTile(this);
            
            if (collide == false) {
                switch (direction) {
                    case "up":
                        y -= speed;
                        break;
                    case "down":
                        y += speed;
                        break;
                    case "left":
                        x -= speed;
                        break;
                    case "right":
                        x += speed;
                        break;
                }
            }
            // animating
            begin++;
            if (begin > interval) {
                tick++;
                if (tick >= maxFrame) {
                    tick = 0;
                }
                begin = 0;
            }
        } else {
            // if the player is standing still, change the frame so that it doesn't look weird
            tick = 0;
        }
    }

    public void draw(Graphics2D g2) {
//        g2.setColor(Color.white);
//        
//        g2.fillRect(x, y, 100, 100);
        BufferedImage frame = null;

        switch (direction) {
            case "up":
                // if (tick == 1) {
                //     image = up1;
                // } else {
                //     image = up2;
                // }
                frame = image.getSubimage(16, 16 * tick, 16, 16);
                break;
            case "down":
                // if (tick == 1) {
                //     image = down1;
                // } else {
                //     image = down2;
                // }
                frame = image.getSubimage(0, 16 * tick, 16, 16);
                break;
            case "left":
                // if (tick == 1) {
                //     image = left1;
                // } else {
                //     image = left2;
                // }
                frame = image.getSubimage(32, 16 * tick, 16, 16);
                break;
            case "right":
                // if (tick == 1) {
                //     image = right1;
                // } else {
                //     image = right2;
                // }
                frame = image.getSubimage(48, 16 * tick, 16, 16);
                break;
        }

        g2.drawImage(frame, x, y, gp.TILESIZE, gp.TILESIZE, null);
    }
}
