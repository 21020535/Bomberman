package entity.enemy;

import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Dumb extends Enemy {

    public Dumb(int x, int y, GamePanel gp) {
        super(x, y, gp);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void setDefaultValues() {
        // x = 1104;
        // y = 48;
        speed = 2;
        tick = 0;
        maxFrame = 4;
        begin = 0;
        interval = 7;
        direction = "left";
        num = 48;
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
        } else if (num == 0) {
            direction = all[a.nextInt(4)];
            num = 48;
        }
    }

    @Override
    public void getImage() {
        // TODO Auto-generated method stub
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/player2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
