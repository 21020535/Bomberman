package entity.bomb;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import entity.Entity;
import entity.Player;
import main.GamePanel;

public class Flame extends Entity {
    GamePanel gp;
    Player player;

    Bomb bomb;
    public boolean finish = false;
    private int totalSides = 0;

    private List<FlameSides> sides = new ArrayList<>();

    public int getTotalSides() {
        return totalSides;
    }

    public void setTotalSides(int totalSides) {
        this.totalSides = totalSides;
    }

    public Flame(int x, int y, GamePanel gp, Bomb bomb, Player player, int bombLength) {
        this.x = x;
        this.y = y;
        this.gp = gp;
        this.bomb = bomb;
        this.player = player;
        tick = 0;
        maxFrame = 4;
        begin = 0;
        interval = 7;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/bomb/fire.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean desUp = false, desDown = false, desLeft = false, desRight = false;

        for (int i = 1; i <= bombLength; i++) {
            if (desLeft == false) {
                if (gp.tileManager.mapTileNum[(bomb.getX() + 12 - i * gp.TILESIZE) / gp.TILESIZE][bomb.getY()
                        / gp.TILESIZE] != 2) {
                    if (i < bombLength) {
                        if (gp.tileManager.mapTileNum[(bomb.getX() - i * gp.TILESIZE) / gp.TILESIZE][(bomb.getY())
                                / gp.TILESIZE] == 1) {
                            sides.add(
                                    new FlameSides(bomb.getX() - i * gp.TILESIZE, bomb.getY(), gp,
                                            "horizontal_left"));
                        } else {
                            sides.add(
                                    new FlameSides(bomb.getX() - i * gp.TILESIZE, bomb.getY(), gp,
                                            "horizontal"));
                        }
                    } else {
                        sides.add(
                                new FlameSides(bomb.getX() - i * gp.TILESIZE, bomb.getY(), gp,
                                        "horizontal_left"));
                    }
                    if (gp.tileManager.mapTileNum[(bomb.getX() - i * gp.TILESIZE) / gp.TILESIZE][bomb.getY()
                            / gp.TILESIZE] == 1) {
                        desLeft = true;
                    }
                } else {
                    desLeft = true;
                }
            }
            if (desRight == false) {
                if (gp.tileManager.mapTileNum[(bomb.getX() + i * gp.TILESIZE) / gp.TILESIZE][bomb.getY()
                        / gp.TILESIZE] != 2) {
                    if (i < bombLength) {
                        if (gp.tileManager.mapTileNum[(bomb.getX() + i * gp.TILESIZE) / gp.TILESIZE][(bomb.getY())
                                / gp.TILESIZE] == 1) {
                            sides.add(
                                    new FlameSides(bomb.getX() + i * gp.TILESIZE, bomb.getY(), gp,
                                            "horizontal_right"));
                        } else {
                            sides.add(
                                    new FlameSides(bomb.getX() + i * gp.TILESIZE, bomb.getY(), gp,
                                            "horizontal"));
                        }
                    } else {
                        sides.add(
                                new FlameSides(bomb.getX() + i * gp.TILESIZE, bomb.getY(), gp,
                                        "horizontal_right"));
                    }
                    if (gp.tileManager.mapTileNum[(bomb.getX() + i * gp.TILESIZE) / gp.TILESIZE][bomb.getY()
                            / gp.TILESIZE] == 1) {
                        desRight = true;
                    }
                } else {
                    desRight = true;
                }
            }
            if (desDown == false) {
                if (gp.tileManager.mapTileNum[(bomb.getX()) / gp.TILESIZE][(bomb.getY() + i * gp.TILESIZE)
                        / gp.TILESIZE] != 2) {
                    if (i < bombLength) {
                        if (gp.tileManager.mapTileNum[(bomb.getX()) / gp.TILESIZE][(bomb.getY() + i * gp.TILESIZE)
                                / gp.TILESIZE] == 1) {
                            sides.add(
                                    new FlameSides(bomb.getX(), bomb.getY() + i * gp.TILESIZE, gp,
                                            "vertical_down"));
                        } else {
                            sides.add(
                                    new FlameSides(bomb.getX(), bomb.getY() + i * gp.TILESIZE, gp,
                                            "vertical"));
                        }

                    } else {
                        sides.add(
                                new FlameSides(bomb.getX(), bomb.getY() + i * gp.TILESIZE, gp,
                                        "vertical_down"));
                    }
                    if (gp.tileManager.mapTileNum[(bomb.getX()) / gp.TILESIZE][(bomb.getY() + i * gp.TILESIZE)
                            / gp.TILESIZE] == 1)
                        desDown = true;
                } else {
                    desDown = true;
                }
            }
            if (desUp == false) {
                if (gp.tileManager.mapTileNum[(bomb.getX()) / gp.TILESIZE][(bomb.getY() - i * gp.TILESIZE)
                        / gp.TILESIZE] != 2) {
                    if (i < bombLength) {
                        if (gp.tileManager.mapTileNum[(bomb.getX()) / gp.TILESIZE][(bomb.getY() - i * gp.TILESIZE)
                                / gp.TILESIZE] == 1) {
                            sides.add(
                                    new FlameSides(bomb.getX(), bomb.getY() - i * gp.TILESIZE, gp,
                                            "vertical_up"));
                        } else {
                            sides.add(
                                    new FlameSides(bomb.getX(), bomb.getY() - i * gp.TILESIZE, gp,
                                            "vertical"));
                        }
                    } else {
                        sides.add(
                                new FlameSides(bomb.getX(), bomb.getY() - i * gp.TILESIZE, gp,
                                        "vertical_up"));
                    }
                    if (gp.tileManager.mapTileNum[(bomb.getX()) / gp.TILESIZE][(bomb.getY() - i * gp.TILESIZE)
                            / gp.TILESIZE] == 1)
                        desUp = true;
                } else {
                    desUp = true;
                }
            }
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
        for (int i = 0; i < sides.size(); i++) {
            sides.get(i).update();
            if (player.getX() + 20 <= sides.get(i).getX() + gp.TILESIZE && player.getX() + gp.TILESIZE >= sides.get(i).getX() + 20
                    && player.getY() + 20 <= sides.get(i).getY() + gp.TILESIZE
                    && player.getY() + gp.TILESIZE >= sides.get(i).getY() + 20) {
                player.dead = true;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage frame = null;
        frame = image.getSubimage(16 * tick, 0, 16, 16);
        g2.drawImage(frame, x, y, gp.TILESIZE, gp.TILESIZE, null);
        for (int i = 0; i < sides.size(); i++) {
            sides.get(i).draw(g2);
        }
    }
}
