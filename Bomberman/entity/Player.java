/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import main.GamePanel;
import main.KeyHandler;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

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
        getPlayerImage();
    }

    public void getPlayerImage() {
        try {
            BufferedImage tmp = ImageIO.read(getClass().getResourceAsStream("/res/player/player.png"));
            // up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_up_1.png"));
            // up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_up_2.png"));
            // down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_down_1.png"));
            // down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_down_2.png"));
            // left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_left_1.png"));
            // left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_left_2.png"));
            // right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_right_1.png"));
            // right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_right_2.png"));
            down1 = tmp.getSubimage(0, 0, 32, 32);
            down2 = tmp.getSubimage(32, 0, 32, 32);
            left1 = tmp.getSubimage(64, 0, 32, 32);
            left2 = tmp.getSubimage(32 * 3, 0, 32, 32);
            right1 = tmp.getSubimage(32 * 4, 0, 32, 32);
            right2 = tmp.getSubimage(32 * 5, 0, 32, 32);
            up1 = tmp.getSubimage(32 * 6, 0, 32, 32);
            up2 = tmp.getSubimage(32 * 7, 0, 32, 32);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void update() {
        if (input.up == true || input.down == true
                || input.left == true || input.right == true) {
            if (input.up == true) {
                direction = "up";
                y -= speed;
            }
            if (input.down == true) {
                direction = "down";
                y += speed;
            }
            if (input.left == true) {
                direction = "left";
                x -= speed;
            }
            if (input.right == true) {
                direction = "right";
                x += speed;
            }

            begin++;
            if (begin > interval) {
                frame++;
                if (frame > maxFrame) {
                    frame = 1;
                }
                begin = 0;
            }
        } else {
            if (direction == "left" || direction == "right") {
                frame = 2;
            }
        }
    }

    public void draw(Graphics g2) {
//        g2.setColor(Color.white);
//        
//        g2.fillRect(x, y, 100, 100);
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (frame == 1) {
                    image = up1;
                } else {
                    image = up2;
                }
                break;
            case "down":
                if (frame == 1) {
                    image = down1;
                } else {
                    image = down2;
                }
                break;
            case "left":
                if (frame == 1) {
                    image = left1;
                } else {
                    image = left2;
                }
                break;
            case "right":
                if (frame == 1) {
                    image = right1;
                } else {
                    image = right2;
                }
                break;
        }

        g2.drawImage(image, x, y, 120, 120, null);
    }
}
