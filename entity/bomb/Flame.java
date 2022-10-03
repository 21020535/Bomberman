package entity.bomb;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class Flame extends Entity {
    GamePanel gp;
    public boolean finish = false;
    private int totalSides = 0;

    public int getTotalSides() {
        return totalSides;
    }

    public void setTotalSides(int totalSides) {
        this.totalSides = totalSides;
    }

    public Flame(int x, int y, GamePanel gp) {
        this.x = x;
        this.y = y;
        this.gp = gp;
        tick = 0;
        maxFrame = 3;
        begin = 0;
        interval = 8;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/bomb/bomb_center.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        // chay animation
        begin++;
        if (begin > interval) {
            tick++;
            if (tick >= maxFrame) {
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
