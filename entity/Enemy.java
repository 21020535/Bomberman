package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Entity {
    GamePanel gp;
    String name;
    public Enemy(GamePanel gp) {
        this.gp = gp;
        setDefaultValues();
        getPlayerImage();
    }

    public void getPlayerImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/balloom_left1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setDefaultValues() {
        x = 1104;
        y = 48;
        speed = 3;
        tick = 0;
        maxFrame = 4;
        begin = 0;
        interval = 7;
        direction = "left";
    }

    public void update() {

        begin++;
        // begin > interval khoảng tg load mỗi frame
        if (begin > interval) {
            tick++;
            // nếu frame > frame max cho về ban đầu
            if (tick >= maxFrame) {
                tick = 0;
            }
            begin = 0;
        }
        collide = false;
        gp.cChecker.checkTile(this);
        if (!collide) {
            switch (direction) {
                case "left":
                    x -= speed;
                    break;
                case "right":
                    x += speed;
                    break;
                case "up":
                    y -= speed;
                    break;
                case "down":
                    y += speed;
                    break;
            }
        } else {
            Random a = new Random();
            if (direction.equals("left")) {
                String [] direstions = {"down","right","up"};
                direction = direstions[a.nextInt(3)];
            } else if (direction.equals("down")){
                String [] direstions = {"left","right","up"};
                direction = direstions[a.nextInt(3)];
            } else if (direction.equals("right")) {
                String [] direstions = {"down","left","up"};
                direction = direstions[a.nextInt(3)];
            } else if (direction.equals("up")) {
                String [] direstions = {"down","right","left"};
                direction = direstions[a.nextInt(3)];
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage frame = null;
        // vẽ nhân vật
        frame = image.getSubimage(0,0,16,16);
        g2.drawImage(frame, x + 4, y + 4, gp.TILESIZE - 8, gp.TILESIZE - 8, null);
    }
}
