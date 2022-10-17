package entity.enemy;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Zombie extends Enemy{
    public Zombie(int x, int y, GamePanel gp) {
        super(x, y, gp);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void setDefaultValues() {
        // x = 1104;
        // y = 48;
        speed = 2;
        tick = 0;
        maxFrame = 6;
        begin = 0;
        interval = 7;
        direction = "left";
    }

    @Override
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
        if (!collide) {
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
        } else {

            if (direction.equals("left")) {
                direction = leftR[a.nextInt(3)];
            } else if (direction.equals("down")) {
                direction = downR[a.nextInt(3)];
            } else if (direction.equals("right")) {
                direction = rightR[a.nextInt(3)];
            } else if (direction.equals("up")) {
                direction = upR[a.nextInt(3)];
            }
        }
    }

    @Override
    public void getImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/enemy/enemy3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage frame = null;
        // vẽ nhân vật
        switch (direction) {
            case "up":
                // getSubimage để cắt 1 hình ảnh lớn thành các frame nhỏ
                frame = image.getSubimage(16 * tick + 2, 48, 16, 16);
                break;
            case "down":
                frame = image.getSubimage(16 * tick + 2, 0, 16, 16);
                break;
            case "left":
                frame = image.getSubimage(16 * tick + 2, 16, 16, 16);
                break;
            case "right":
                frame = image.getSubimage(16 * tick + 2, 32, 16, 16);
                break;
        }
        g2.drawImage(frame, x + 4, y + 4, gp.TILESIZE - 8, gp.TILESIZE - 8, null);
    }
}