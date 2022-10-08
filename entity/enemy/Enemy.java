package entity.enemy;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class Enemy extends Entity {
    GamePanel gp;
    String name;
    String[] leftR = {"down", "right", "up"};
    String[] downR = {"left", "right", "up"};
    String[] rightR = {"down", "left", "up"};
    String[] upR = {"down", "right", "left"};
    String[] all = {"up", "down", "right", "left"};
    List<String> tmp = new ArrayList<>();
    int num;

    public Enemy(GamePanel gp) {
        this.gp = gp;
        setDefaultValues();
        getImage();
    }

    public void getImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/player2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        x = 1104;
        y = 48;
        speed = 2;
        tick = 0;
        maxFrame = 4;
        begin = 0;
        interval = 7;
        direction = "left";
        num = 48;
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
        Random a = new Random();
        gp.cChecker.checkTile(this);
        if (!collide && num > 0) {
            switch (direction) {
                case "left":
                    x -= speed;
                    num--;
                    break;
                case "right":
                    x += speed;
                    num--;
                    break;
                case "up":
                    y -= speed;
                    num--;
                    break;
                case "down":
                    y += speed;
                    num--;
                    break;
            }
        }
        if (collide) {

            if (direction.equals("left")) {
                direction = leftR[a.nextInt(3)];
            } else if (direction.equals("down")) {
                direction = downR[a.nextInt(3)];
            } else if (direction.equals("right")) {
                direction = rightR[a.nextInt(3)];
            } else if (direction.equals("up")) {
                direction = upR[a.nextInt(3)];
            }
        } else if (num == 0){
            direction = all[a.nextInt(4)];
            num = 48;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage frame = null;
        // vẽ nhân vật
        switch (direction) {
            case "up":
                // getSubimage để cắt 1 hình ảnh lớn thành các frame nhỏ
                frame = image.getSubimage(16, 16 * tick, 16, 16);
                break;
            case "down":
                frame = image.getSubimage(0, 16 * tick, 16, 16);
                break;
            case "left":
                frame = image.getSubimage(32, 16 * tick, 16, 16);
                break;
            case "right":
                frame = image.getSubimage(48, 16 * tick, 16, 16);
                break;
        }
        g2.drawImage(frame, x + 4, y + 4, gp.TILESIZE - 8, gp.TILESIZE - 8, null);
    }
}
