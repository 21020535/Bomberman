package entity.enemy;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Kiki extends Enemy {
    private boolean blockedL = false, blockedR = false, blockedU = false, blockedD = false;
    private int movementBuffer = 0;

    public Kiki(int x, int y, GamePanel gp) {
        super(x, y, gp);
    }

    @Override
    public void getImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/enemy/kiki.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setDefaultValues() {
        speed = 2;
        tick = 0;
        maxFrame = 3;
        begin = 0;
        interval = 10;
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

        if (movementBuffer == 0) {
            boolean chose = false;
            if (gp.player.getX() < x) {
                if (blockedL == false) {
                    direction = "left";
                    movementBuffer += gp.TILESIZE;
                    chose = true;
                }
            }
            if (gp.player.getX() > x) {
                if (chose == false && blockedR == false) {
                    direction = "right";
                    movementBuffer += gp.TILESIZE;
                    chose = true;
                }
            }
            if (gp.player.getY() < y) {
                if (chose == false && blockedU == false) {
                    direction = "up";
                    movementBuffer += gp.TILESIZE;
                    chose = true;
                }
            }
            if (gp.player.getY() > y) {
                if (chose == false && blockedD == false) {
                    direction = "right";
                    movementBuffer += gp.TILESIZE;
                    chose = true;
                }
            }
        }
        moved = false;
        collide = false;
        // Random a = new Random();
        gp.cChecker.checkTile(this);
        if (collide == false) {
            moved = true;
            if (movementBuffer > 0) {
                switch (direction) {
                    case "up":
                        y -= Math.min(speed, movementBuffer);
                        movementBuffer -= Math.min(speed, movementBuffer);
                        break;
                    case "down":
                        y += Math.min(speed, movementBuffer);
                        movementBuffer -= Math.min(speed, movementBuffer);
                        break;
                    case "left":
                        x -= Math.min(speed, movementBuffer);
                        movementBuffer -= Math.min(speed, movementBuffer);
                        break;
                    case "right":
                        x += Math.min(speed, movementBuffer);
                        movementBuffer -= Math.min(speed, movementBuffer);
                        break;
                }
            }
        } else {
            switch (direction) {
                case "up":
                    blockedU = true;
                    if (moved) {
                        blockedL = false;
                        blockedR = false;
                    }
                    break;
                case "down":
                    blockedD = true;
                    if (moved) {
                        blockedL = false;
                        blockedR = false;
                    }
                    break;
                case "left":
                    blockedL = true;
                    if (moved) {
                        blockedU = false;
                        blockedD = false;
                    }
                    break;
                case "right":
                    blockedR = true;
                    if (moved) {
                        blockedU = false;
                        blockedD = false;
                    }
                    break;
            }
            movementBuffer = 0;
        }
        System.out.println(blockedD + " blockedD");
        System.out.println(blockedR + " blockedR");
        System.out.println(blockedU + " blockedU");
        System.out.println(blockedL + " blockedL");
        System.out.println();
        }
        collide = false;
        gp.cChecker.checkTile(this);
        if (collide == false) {
            if (movementBuffer > 0) {
                switch (direction) {
                    case "up":
                        y -= Math.min(speed, movementBuffer);
                        movementBuffer -= Math.min(speed, movementBuffer);
                        break;
                    case "down":
                        y += Math.min(speed, movementBuffer);
                        movementBuffer -= Math.min(speed, movementBuffer);
                        break;
                    case "left":
                        x -= Math.min(speed, movementBuffer);
                        movementBuffer -= Math.min(speed, movementBuffer);
                        break;
                    case "right":
                        x += Math.min(speed, movementBuffer);
                        movementBuffer -= Math.min(speed, movementBuffer);
                        break;
                }
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
            }
        } else {
            switch (direction) {
                case "left":
                    blockedL = true;
                    break;
                case "right":
                    blockedR = true;
                    break;
                case "up":
                    blockedU = true;
                    break;
                case "down":
                    blockedD = true;
                    break;
            }
            movementBuffer = 0;
            tick = 0;
        }

    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage frame = null;
        switch (direction) {
            case "left":
                frame = image.getSubimage(48 * tick, 0, 48, 48);
                break;
            case "right":
                frame = image.getSubimage(48 * tick, 48, 48, 48);
                break;
            default:
                break;
        }
        g2.drawImage(frame, x + 4, y + 4, gp.TILESIZE - 8, gp.TILESIZE - 8, null);
    }
}
