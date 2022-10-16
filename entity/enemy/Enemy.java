package entity.enemy;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import entity.Entity;
import main.GamePanel;

public abstract class Enemy extends Entity {
    GamePanel gp;
    String name;
    String[] leftR = {"down", "right", "up"};
    String[] downR = {"left", "right", "up"};
    String[] rightR = {"down", "left", "up"};
    String[] upR = {"down", "right", "left"};
    String[] all = {"up", "down", "right", "left"};
    List<String> tmp = new ArrayList<>();
    int num;

    public Enemy(int x, int y, GamePanel gp) {
        this.x = x;
        this.y = y;
        this.gp = gp;
        setDefaultValues();
        getImage();
    }

    public abstract void getImage();

    public abstract void setDefaultValues();

    public abstract void update();

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
