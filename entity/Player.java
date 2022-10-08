/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
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
    public KeyHandler input;
    List<Bomb> bombs = new ArrayList<>();
    List<Flame> flames = new ArrayList<>();
    public int bombLength, maxBomb;
    private int movementBuffer = 0;

    public Player(GamePanel gp, KeyHandler input) {
        this.gp = gp;
        this.input = input;
        setDefaultValues();
        getPlayerImage();
    }

    // đọc file ảnh của nhân vật
    public void getPlayerImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/player2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // set các thông số mặc định
    public void setDefaultValues() {
        x = 48;
        y = 48;
        speed = 3;
        direction = "down";
        tick = 0;
        maxFrame = 4;
        begin = 0;
        interval = 7;
        bombLength = 1;
        maxBomb = 2;
    }

    public void update() {
        // kiểm tra xem có ấn 1 trong 4 không
        if (input.up == true || input.down == true
                || input.left == true || input.right == true || input.bomb == true) {
            // nếu input = up thì trạng thái hoạt động là up
            if (input.up == true) {
                if (movementBuffer == 0) {
                    movementBuffer += gp.TILESIZE;
                    direction = "up";
                } else if (direction.equals("down")) {
                    movementBuffer = gp.TILESIZE - movementBuffer;
                    direction = "up";
                }
                // direction = "up";
            }
            if (input.down == true) {
                if (movementBuffer == 0) {
                    movementBuffer += gp.TILESIZE;
                    direction = "down";
                } else if (direction.equals("up")) {
                    movementBuffer = gp.TILESIZE - movementBuffer;
                    direction = "down";
                }
                // direction = "down";
            }
            if (input.left == true) {
                if (movementBuffer == 0) {
                    movementBuffer += gp.TILESIZE;
                    direction = "left";
                } else if (direction.equals("right")) {
                    movementBuffer = gp.TILESIZE - movementBuffer;
                    direction = "left";
                }
                // direction = "left";
            }
            if (input.right == true) {
                if (movementBuffer == 0) {
                    movementBuffer += gp.TILESIZE;
                    direction = "right";
                } else if (direction.equals("left")) {
                    movementBuffer = gp.TILESIZE - movementBuffer;
                    direction = "right";
                }
                // direction = "right";
            }
            // nếu input là bomb thì set xem độ dài mảng bomb có hơn độ dài số lượng bomb
            // max không nếu không add
            // thêm 1 quả bomb vào list bomb
            if (input.bomb == true) {
                if (bombs.size() < maxBomb) {
                    bombs.add(new Bomb((x + 12) / gp.TILESIZE * gp.TILESIZE, (y + 12) / gp.TILESIZE * gp.TILESIZE,
                            bombLength, gp));
                    input.bomb = false;
                }
            }
            // va chạm ban đầu = false
            collide = false;
            // check va chạm vs bản đồ
            gp.cChecker.checkTile(this);

            // if (collide == false) {
            //     if (input.up) {
            //         y -= speed;
            //     }
            //     if (input.down) {
            //         y += speed;
            //     }
            //     if (input.left) {
            //         x -= speed;
            //     }
            //     if (input.right) {
            //         x += speed;
            //     }
            // }
            // animating
        }
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
        }
        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).update();
            // nếu nổ
            if (bombs.get(i).exploded == true) {
                for (int j = 1; j <= bombLength; j++) {
                    flames.add(new Flame(bombs.get(i).x, bombs.get(i).y, gp, bombs.get(i), bombLength));
                    if (bombs.get(i).desLeft == false) {
                        // tại vị trí ô bên trái đặt quả bom có giá trị bằng 1 thì set về giá trị bằng 0
                        // xóa item hủy diệt được
                        // sau đó gán defledt = true để mỗi lần phá chỉ phá đc 1 viên gạch
                        // nếu vị trí bên trái đặt quả bomb = 2 thì k phá hủy thứ j

                        if (gp.tileManager.mapTileNum[(bombs.get(i).x + 12 - j * gp.TILESIZE)
                                / gp.TILESIZE][(bombs.get(i).y) / gp.TILESIZE] == 2) {
                            bombs.get(i).desLeft = true;
                        }
                        if (gp.tileManager.mapTileNum[(bombs.get(i).x + 12 - j * gp.TILESIZE)
                                / gp.TILESIZE][(bombs.get(i).y) / gp.TILESIZE] == 1) {
                            gp.tileManager.mapTileNum[(bombs.get(i).x + 12 - j * gp.TILESIZE)
                                    / gp.TILESIZE][(bombs.get(i).y) / gp.TILESIZE] = 0;
                            bombs.get(i).desLeft = true;
                        }
                    }
                    if (bombs.get(i).desRight == false) {
                        if (gp.tileManager.mapTileNum[(bombs.get(i).x + 12 + j * gp.TILESIZE)
                                / gp.TILESIZE][(bombs.get(i).y) / gp.TILESIZE] == 2) {
                            bombs.get(i).desRight = true;
                        }
                        if (gp.tileManager.mapTileNum[(bombs.get(i).x + 12 + j * gp.TILESIZE)
                                / gp.TILESIZE][(bombs.get(i).y) / gp.TILESIZE] == 1) {
                            gp.tileManager.mapTileNum[(bombs.get(i).x + 12 + j * gp.TILESIZE)
                                    / gp.TILESIZE][(bombs.get(i).y) / gp.TILESIZE] = 0;
                            bombs.get(i).desRight = true;
                        }
                    }
                    if (bombs.get(i).desUp == false) {
                        if (gp.tileManager.mapTileNum[(bombs.get(i).x)
                                / gp.TILESIZE][(bombs.get(i).y + 12 - j * gp.TILESIZE) / gp.TILESIZE] == 2) {
                            bombs.get(i).desUp = true;
                        }
                        if (gp.tileManager.mapTileNum[(bombs.get(i).x)
                                / gp.TILESIZE][(bombs.get(i).y + 12 - j * gp.TILESIZE) / gp.TILESIZE] == 1) {
                            gp.tileManager.mapTileNum[(bombs.get(i).x)
                                    / gp.TILESIZE][(bombs.get(i).y + 12 - j * gp.TILESIZE) / gp.TILESIZE] = 0;
                            bombs.get(i).desUp = true;
                        }
                    }

                    if (bombs.get(i).desDown == false) {
                        if (gp.tileManager.mapTileNum[(bombs.get(i).x)
                                / gp.TILESIZE][(bombs.get(i).y + 12 + j * gp.TILESIZE) / gp.TILESIZE] == 2) {
                            bombs.get(i).desDown = true;
                        }
                        if (gp.tileManager.mapTileNum[(bombs.get(i).x)
                                / gp.TILESIZE][(bombs.get(i).y + 12 + j * gp.TILESIZE) / gp.TILESIZE] == 1) {
                            gp.tileManager.mapTileNum[(bombs.get(i).x)
                                    / gp.TILESIZE][(bombs.get(i).y + 12 + j * gp.TILESIZE) / gp.TILESIZE] = 0;
                            bombs.get(i).desDown = true;
                        }
                    }
                }
                // sau khi dùng bomb thì vứt ra khỏi list
                bombs.remove(i);
                i--;
            }
        }
        for (int i = 0; i < flames.size(); i++) {
            flames.get(i).update();
            if (flames.get(i).finish) {
                flames.remove(i);
                i--;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage frame = null;

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

        // vẽ nhân vật
        g2.drawImage(frame, x + 4, y + 4, gp.TILESIZE - 8, gp.TILESIZE - 8, null);
    }
}
