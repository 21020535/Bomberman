package entity.bomb;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class FlameSides extends Entity {
    GamePanel gp;
    String axis;

    public FlameSides(int x, int y, GamePanel gp, String axis) {
        this.x = x;
        this.y = y;
        this.gp = gp;
        this.axis = axis;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/bomb/" + axis + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        tick = 0;
        maxFrame = 3;
        begin = 0;
        interval = 8;
    }

    public void update() {
        begin++;
        if (begin > interval) {
            tick++;
            if (tick >= maxFrame) {
                tick = 0;
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
