package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import main.GamePanel;
public class Fire extends Entity {
    long timeToExplode = 2000;
    long put, clock;
    boolean exploded = false;
    GamePanel gp;
    public Fire(int x,int y, GamePanel gp) {
        this.x = x;
        this.y = y;
        this.gp = gp;
        tick = 0;
        maxFrame = 3;
        begin = 0;
        interval = 8;
        put = System.currentTimeMillis();
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/bomb/bomb_eplo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        // chay animation
        clock = System.currentTimeMillis() ;
        if (clock - put < timeToExplode) {
            begin++;
            if (begin > interval) {
                tick++;
                if (tick >= maxFrame) {
                    tick = 0;
                }
                begin = 0;
            }
        } else {
            exploded = true;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage frame = null;
        frame = image.getSubimage(16 * tick, 0, 16, 16);
        g2.drawImage(frame, x, y, gp.TILESIZE, gp.TILESIZE, null);
    }
}
