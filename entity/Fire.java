package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Fire extends Entity {

    GamePanel gp;
    public boolean finish = false;
    public Fire(int x,int y, GamePanel gp) {
        this.x = x;
        this.y = y;
        this.gp = gp;
        tick = 0;
        maxFrame = 3;
        begin = 0;
        interval = 8;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/bomb/flame_center.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        // chay animation
        // if là thời gian chạy giữa các frame
        begin++;
        if (begin > interval) {
            tick++;
            if (tick >= maxFrame) {
                // chạy xong 1 lần thì fire sẽ finish và bị bỏ ra khỏi list
                finish = true;
            }
            begin = 0;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage frame = null;
        frame = image.getSubimage(16 * tick, 0, 16, 16);
        g2.drawImage(frame, x, y, gp.TILESIZE, gp.TILESIZE, null);
    }
}
