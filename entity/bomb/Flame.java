package entity.bomb;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import entity.Entity;
import entity.Player;
import entity.enemy.Enemy;
import main.GamePanel;

public class Flame extends Entity {
    GamePanel gp;
    Player player;

    List<Enemy> enemies;

    Bomb bomb;
    public boolean finish = false;
    private int totalSides = 0;

    private List<FlameSides> sides = new ArrayList<>();

    public boolean flagLeft = false, flagRight = false, flagUp = false, flagDown = false;

    public int x1, x2, x3, x4, y1, y2, y3, y4;

    public int getTotalSides() {
        return totalSides;
    }

    public void setTotalSides(int totalSides) {
        this.totalSides = totalSides;
    }

    public Flame(int x, int y, GamePanel gp, Bomb bomb, Player player, List<Enemy> enemies, int bombLength) {
        this.x = x;
        this.y = y;
        this.gp = gp;
        this.bomb = bomb;
        this.player = player;
        this.enemies = enemies;
        tick = 0;
        maxFrame = 4;
        begin = 0;
        interval = 7;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/bomb/fire.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        generateFlameSides(gp, bomb, bombLength);
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
            if (!player.flameResist) {
                if (player.getX() + 20 <= sides.get(i).getX() + gp.TILESIZE
                        && player.getX() + gp.TILESIZE >= sides.get(i).getX() + 20
                        && player.getY() + 20 <= sides.get(i).getY() + gp.TILESIZE
                        && player.getY() + gp.TILESIZE >= sides.get(i).getY() + 20) {
                    player.dead = true;
                }
            }
            for (int j = 0; j < enemies.size(); j++) {
                if (enemies.get(j).dead == false) {
                    if (enemies.get(j).getX() + 20 <= sides.get(i).getX() + gp.TILESIZE
                            && enemies.get(j).getX() + gp.TILESIZE >= sides.get(i).getX() + 20
                            && enemies.get(j).getY() + 20 <= sides.get(i).getY() + gp.TILESIZE
                            && enemies.get(j).getY() + gp.TILESIZE >= sides.get(i).getY() + 20) {
                        enemies.get(j).dead = true;
                        enemies.get(j).tick = 0;
                        enemies.get(j).begin = 0;
                        enemies.get(j).interval = 20;
                    }
                }
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

    private void generateFlameSides(GamePanel gp, Bomb bomb, int bombLength) {
        boolean desUp = false, desDown = false, desLeft = false, desRight = false;

        for (int i = 1; i <= bombLength; i++) {
            if (desLeft == false) {
                if (!GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bomb.getX() + 12 - i * gp.TILESIZE)
                        / gp.TILESIZE][bomb.getY()
                                / gp.TILESIZE]].stiff) {
                    if (i < bombLength) {
                        if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bomb.getX() - i * gp.TILESIZE)
                                / gp.TILESIZE][(bomb.getY())
                                        / gp.TILESIZE]].breakable) {
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
                    if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bomb.getX() - i * gp.TILESIZE)
                            / gp.TILESIZE][bomb.getY()
                                    / gp.TILESIZE]].breakable) {
                        desLeft = true;
                    }
                } else {
                    desLeft = true;
                }
            }
            if (desRight == false) {
                if (!GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bomb.getX() + i * gp.TILESIZE)
                        / gp.TILESIZE][bomb.getY()
                                / gp.TILESIZE]].stiff) {
                    if (i < bombLength) {
                        if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bomb.getX() + i * gp.TILESIZE)
                                / gp.TILESIZE][(bomb.getY())
                                        / gp.TILESIZE]].breakable) {
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
                    if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bomb.getX() + i * gp.TILESIZE)
                            / gp.TILESIZE][bomb.getY()
                                    / gp.TILESIZE]].breakable) {
                        desRight = true;
                    }
                } else {
                    desRight = true;
                }
            }
            if (desDown == false) {
                if (!GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bomb.getX())
                        / gp.TILESIZE][(bomb.getY() + i * gp.TILESIZE)
                                / gp.TILESIZE]].stiff) {
                    if (i < bombLength) {
                        if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bomb.getX())
                                / gp.TILESIZE][(bomb.getY() + i * gp.TILESIZE)
                                        / gp.TILESIZE]].breakable) {
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
                    if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bomb.getX())
                            / gp.TILESIZE][(bomb.getY() + i * gp.TILESIZE)
                                    / gp.TILESIZE]].breakable)
                        desDown = true;
                } else {
                    desDown = true;
                }
            }
            if (desUp == false) {
                if (!GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bomb.getX())
                        / gp.TILESIZE][(bomb.getY() - i * gp.TILESIZE)
                                / gp.TILESIZE]].stiff) {
                    if (i < bombLength) {
                        if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bomb.getX())
                                / gp.TILESIZE][(bomb.getY() - i * gp.TILESIZE)
                                        / gp.TILESIZE]].breakable) {
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
                    if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bomb.getX())
                            / gp.TILESIZE][(bomb.getY() - i * gp.TILESIZE)
                                    / gp.TILESIZE]].breakable)
                        desUp = true;
                } else {
                    desUp = true;
                }
            }
        }
    }
}
