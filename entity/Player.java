/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import entity.bomb.Bomb;
import entity.bomb.Flame;
import main.GamePanel;
import main.KeyHandler;

/**
 * @author Lenovo
 */
public class Player extends Entity {

    private GamePanel gp;
    private KeyHandler input;
    private List<Bomb> bombs = new ArrayList<>();
    private List<Flame> flames = new ArrayList<>();
    private List<Integer> mapItem = new ArrayList<>();
    private int bombLength, maxBomb;
    private int movementBuffer = 0;

    public boolean flameResist = true;

    public boolean dead = false;
    public boolean finish = false;

    public Player(GamePanel gp, KeyHandler input) {
        this.gp = gp;
        this.input = input;
        setDefaultValues();
        getPlayerImage();
    }

    // đọc file ảnh của nhân vật
    public void getPlayerImage() {
        try {
            if (!dead) {
                image = ImageIO.read(getClass().getResourceAsStream("/res/player/player2.png"));
                image2 = ImageIO.read(getClass().getResourceAsStream("/res/player/deadplayer.png"));
            } else {
                gp.playSE(6);
                interval = 20;
                tick = 0;
                begin = 0;
                // image =
                // ImageIO.read(getClass().getResourceAsStream("/res/player/deadplayer.png"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // set các thông số mặc định
    public void setDefaultValues() {
        x = 48;
        y = 48;
        speed = 4;
        direction = "down";
        tick = 0;
        maxFrame = 4;
        begin = 0;
        interval = 10;
        bombLength = 3;
        maxBomb = 10;
        powerupsSetup();
    }

    public void update() {
        handleMovementsAndInputs();
        handleBombs();
        powerUps();
        dead = dead | deadYet();
    }

    public void draw(Graphics2D g2) {
        if (!dead) {
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

            // vẽ bomb
            for (int i = 0; i < bombs.size(); i++) {
                bombs.get(i).draw(g2);
            }

            for (int i = 0; i < flames.size(); i++) {
                flames.get(i).draw(g2);
            }
        } else {
            frame = image2.getSubimage(16 * tick, 0, 16, 16);
        }
        // vẽ nhân vật
        g2.drawImage(frame, x + 4, y + 4, gp.TILESIZE - 8, gp.TILESIZE - 8, null);
    }

    private void handleMovementsAndInputs() {
        // kiểm tra xem có ấn 1 trong 4 không
        if (!dead) {
            if (input.up == true || input.down == true
                    || input.left == true || input.right == true || input.bomb == true) {
                // nếu input = up thì trạng thái hoạt động là up
                if (input.up == true) {
                    if (movementBuffer == 0) {
                        if (y % gp.TILESIZE == 0) {
                            movementBuffer += gp.TILESIZE;
                        } else {
                            movementBuffer += y % gp.TILESIZE;
                        }
                        direction = "up";
                    } else if (direction.equals("down")
                            && GamePanel.tileManager.mapTileNum[x / gp.TILESIZE][y / gp.TILESIZE] != 41) {
                        movementBuffer = gp.TILESIZE - movementBuffer;
                        direction = "up";
                    }
                }
                if (input.down == true) {
                    if (movementBuffer == 0) {
                        if (y % gp.TILESIZE == 0) {
                            movementBuffer += gp.TILESIZE;
                        } else {
                            movementBuffer += gp.TILESIZE - y % gp.TILESIZE;
                        }
                        direction = "down";
                    } else if (direction.equals("up")
                            && GamePanel.tileManager.mapTileNum[x / gp.TILESIZE][(y + gp.TILESIZE - 1)
                                    / gp.TILESIZE] != 41) {
                        movementBuffer = gp.TILESIZE - movementBuffer;
                        direction = "down";
                    }
                }
                if (input.left == true) {
                    if (movementBuffer == 0) {
                        if (x % gp.TILESIZE == 0) {
                            movementBuffer += gp.TILESIZE;
                        } else {
                            movementBuffer += x % gp.TILESIZE;
                        }
                        direction = "left";
                    } else if (direction.equals("right")
                            && GamePanel.tileManager.mapTileNum[x / gp.TILESIZE][y / gp.TILESIZE] != 41) {
                        movementBuffer = gp.TILESIZE - movementBuffer;
                        direction = "left";
                    }
                }
                if (input.right == true) {
                    if (movementBuffer == 0) {
                        if (x % gp.TILESIZE == 0) {
                            movementBuffer += gp.TILESIZE;
                        } else {
                            movementBuffer += gp.TILESIZE - x % gp.TILESIZE;
                        }
                        direction = "right";
                    } else if (direction.equals("left")
                            && GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE - 1) / gp.TILESIZE][y
                                    / gp.TILESIZE] != 41) {
                        movementBuffer = gp.TILESIZE - movementBuffer;
                        direction = "right";
                    }
                }
                // nếu input là bomb thì set xem độ dài mảng bomb có hơn độ dài số lượng bomb
                // max không nếu không add
                // thêm 1 quả bomb vào list bomb
                if (input.bomb == true) {
                    if (bombs.size() < maxBomb) {
                        if (GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2) / gp.TILESIZE][(y + gp.TILESIZE / 2)
                                / gp.TILESIZE] != 41) {
                            bombs.add(new Bomb((x + gp.TILESIZE / 2) / gp.TILESIZE * gp.TILESIZE,
                                    (y + gp.TILESIZE / 2) / gp.TILESIZE * gp.TILESIZE,
                                    bombLength, gp));
                            GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2) / gp.TILESIZE][(y + gp.TILESIZE / 2)
                                    / gp.TILESIZE] = 41;
                            input.bomb = false;
                            gp.playSE(1);
                        }
                    }
                }
                // va chạm ban đầu = false
                // check va chạm vs bản đồ
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
                } else {
                    tick = 0;
                }
            } else {
                movementBuffer = 0;
                tick = 0;
            }
        } else {
            begin++;
            if (begin > interval) {
                tick++;
                if (tick >= maxFrame) {
                    finish = true;
                }
                begin = 0;
            }
        }
    }

    private void powerupsSetup() {
        int brickNumber;
        switch (gp.level) {
            case 1:
                brickNumber = 79;
                break;
            case 2:
                brickNumber = 44;
                break;
            default:
                brickNumber = 47;
                break;
        }

        this.mapItem.add(37);

        for (int i = 0; i < 4; i++) {
            this.mapItem.add(33);
        }
        for (int i = 0; i < 3; i++) {
            this.mapItem.add(34);
        }
        for (int i = 0; i < 4; i++) {
            this.mapItem.add(35);
        }
        for (int i = 0; i < 1; i++) {
            this.mapItem.add(36);
        }
        int n = mapItem.size();
        for (int i = 0; i < brickNumber - n; i++) {
            mapItem.add(0);
        }
        Collections.shuffle(mapItem);
    }

    private void handleBombs() {
        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).update();
            // nếu nổ
            if (bombs.get(i).exploded == true) {
                gp.playSE(2);
                flames.add(new Flame(bombs.get(i).x, bombs.get(i).y, gp, bombs.get(i), this, gp.enemies, bombLength));

                for (int j = 1; j <= bombLength; j++) {
                    if (bombs.get(i).desLeft == false) {
                        // tại vị trí ô bên trái đặt quả bom có giá trị bằng 1 thì set về giá trị bằng 0
                        // xóa item hủy diệt được
                        // sau đó gán defledt = true để mỗi lần phá chỉ phá đc 1 viên gạch
                        // nếu vị trí bên trái đặt quả bomb = 2 thì k phá hủy thứ j

                        if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bombs.get(i).x - j * gp.TILESIZE)
                                / gp.TILESIZE][(bombs.get(i).y) / gp.TILESIZE]].stiff) {
                            bombs.get(i).desLeft = true;
                        }
                        if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bombs.get(i).x
                                - j * gp.TILESIZE)
                                / gp.TILESIZE][(bombs.get(i).y) / gp.TILESIZE]].breakable) {
                            // GamePanel.tileManager.mapTileNum[(bombs.get(i).x - j * gp.TILESIZE)
                            // / gp.TILESIZE][(bombs.get(i).y) / gp.TILESIZE] = mapItem.get(0);
                            flames.get(i).x1 = (bombs.get(i).x - j * gp.TILESIZE) / gp.TILESIZE;
                            flames.get(i).y1 = (bombs.get(i).y) / gp.TILESIZE;
                            // mapItem.remove(0);
                            bombs.get(i).desLeft = true;
                            flames.get(i).flagLeft = true;
                        }
                    }
                    if (bombs.get(i).desRight == false) {
                        if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bombs.get(i).x + j * gp.TILESIZE)
                                / gp.TILESIZE][(bombs.get(i).y) / gp.TILESIZE]].stiff) {
                            bombs.get(i).desRight = true;
                        }
                        if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bombs.get(i).x
                                + j * gp.TILESIZE)
                                / gp.TILESIZE][(bombs.get(i).y) / gp.TILESIZE]].breakable) {
                            // GamePanel.tileManager.mapTileNum[(bombs.get(i).x + j * gp.TILESIZE)
                            // / gp.TILESIZE][(bombs.get(i).y) / gp.TILESIZE] = mapItem.get(0);
                            flames.get(i).x2 = (bombs.get(i).x + j * gp.TILESIZE) / gp.TILESIZE;
                            flames.get(i).y2 = (bombs.get(i).y) / gp.TILESIZE;
                            // mapItem.remove(0);
                            bombs.get(i).desRight = true;
                            flames.get(i).flagRight = true;
                        }

                    }
                    if (bombs.get(i).desUp == false) {
                        if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bombs.get(i).x)
                                / gp.TILESIZE][(bombs.get(i).y - j * gp.TILESIZE) / gp.TILESIZE]].stiff) {
                            bombs.get(i).desUp = true;
                        }
                        if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bombs.get(i).x)
                                / gp.TILESIZE][(bombs.get(i).y - j * gp.TILESIZE) / gp.TILESIZE]].breakable) {
                            // GamePanel.tileManager.mapTileNum[(bombs.get(i).x)
                            // / gp.TILESIZE][(bombs.get(i).y - j * gp.TILESIZE) / gp.TILESIZE] = mapItem
                            // .get(0);
                            flames.get(i).x3 = (bombs.get(i).x) / gp.TILESIZE;
                            flames.get(i).y3 = (bombs.get(i).y - j * gp.TILESIZE) / gp.TILESIZE;
                            // mapItem.remove(0);
                            bombs.get(i).desUp = true;
                            flames.get(i).flagUp = true;
                        }

                    }

                    if (bombs.get(i).desDown == false) {
                        if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bombs.get(i).x)
                                / gp.TILESIZE][(bombs.get(i).y + j * gp.TILESIZE) / gp.TILESIZE]].stiff) {
                            bombs.get(i).desDown = true;
                        }
                        if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bombs.get(i).x)
                                / gp.TILESIZE][(bombs.get(i).y + j * gp.TILESIZE) / gp.TILESIZE]].breakable) {
                            // GamePanel.tileManager.mapTileNum[(bombs.get(i).x)
                            // / gp.TILESIZE][(bombs.get(i).y + j * gp.TILESIZE) / gp.TILESIZE] = mapItem
                            // .get(0);
                            flames.get(i).x4 = (bombs.get(i).x) / gp.TILESIZE;
                            flames.get(i).y4 = (bombs.get(i).y + j * gp.TILESIZE) / gp.TILESIZE;
                            // mapItem.remove(0);
                            bombs.get(i).desDown = true;
                            flames.get(i).flagDown = true;
                        }

                    }
                }
                // sau khi dùng bomb thì vứt ra khỏi list

                GamePanel.tileManager.mapTileNum[bombs.get(i).x / gp.TILESIZE][bombs.get(i).y / gp.TILESIZE] = 0;
                bombs.remove(i);
                i--;
            }
        }
        for (int i = 0; i < flames.size(); i++) {
            flames.get(i).update();
            if (flames.get(i).finish) {
                if (flames.get(i).flagLeft == true) {
                    GamePanel.tileManager.mapTileNum[flames.get(i).x1][flames.get(i).y1] = mapItem.get(0);
                    mapItem.remove(0);
                }
                if (flames.get(i).flagRight == true) {
                    GamePanel.tileManager.mapTileNum[flames.get(i).x2][flames.get(i).y2] = mapItem.get(0);
                    mapItem.remove(0);
                }
                if (flames.get(i).flagUp == true) {
                    GamePanel.tileManager.mapTileNum[flames.get(i).x3][flames.get(i).y3] = mapItem.get(0);
                    mapItem.remove(0);
                }
                if (flames.get(i).flagDown == true) {
                    GamePanel.tileManager.mapTileNum[flames.get(i).x4][flames.get(i).y4] = mapItem.get(0);
                    mapItem.remove(0);
                }
                flames.remove(i);
                i--;
            }
        }
    }

    private void powerUps() {
        if (GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2) / gp.TILESIZE][(y + gp.TILESIZE / 2)
                / gp.TILESIZE] == 33) {
            gp.playSE(4);
            maxBomb++;
            GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2) / gp.TILESIZE][(y + gp.TILESIZE / 2)
                    / gp.TILESIZE] = 0;
        }
        if (GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2) / gp.TILESIZE][(y + gp.TILESIZE / 2)
                / gp.TILESIZE] == 34) {
            gp.playSE(4);
            this.speed++;
            this.interval--;
            GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2) / gp.TILESIZE][(y + gp.TILESIZE / 2)
                    / gp.TILESIZE] = 0;
        }
        if (GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2) / gp.TILESIZE][(y + gp.TILESIZE / 2)
                / gp.TILESIZE] == 35) {
            gp.playSE(4);
            bombLength++;
            GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2) / gp.TILESIZE][(y + gp.TILESIZE / 2)
                    / gp.TILESIZE] = 0;
        }
        if (GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2) / gp.TILESIZE][(y + gp.TILESIZE / 2)
                / gp.TILESIZE] == 36) {
            // flamepass
            flameResist = true;
            gp.playSE(4);
            // maxBomb += 1;
            GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2) / gp.TILESIZE][(y + gp.TILESIZE / 2)
                    / gp.TILESIZE] = 0;
        }

        if (GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2) / gp.TILESIZE][(y + gp.TILESIZE / 2)
                / gp.TILESIZE] == 37) {
            // portal
            if (gp.enemies.isEmpty()) {
                if (gp.level < gp.maxLevel) {
                    gp.level++;
                    gp.setupGame();
                    gp.state = gp.playState;
                    gp.playSE(4);

                } else {
                    gp.stopMusic();
                    gp.state = gp.gameWinState;
                }
            }
            // maxBomb += 1;
        }
    }

    private boolean deadYet() {
        for (int i = 0; i < gp.enemies.size(); i++) {
            if (gp.enemies.get(i).dead == false) {
                if (x + 18 <= gp.enemies.get(i).x + gp.TILESIZE && x + gp.TILESIZE >= gp.enemies.get(i).x + 18
                        && y + 18 <= gp.enemies.get(i).y + gp.TILESIZE && y + gp.TILESIZE >= gp.enemies.get(i).y + 18) {
                    return true;
                }
            }
        }
        return false;
    }

}
