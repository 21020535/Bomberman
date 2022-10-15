/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entity.Entity;
import entity.Player;

/**
 *
 * @author Lenovo
 */
public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTilePlayer(Player player) {
        // bên trái nhất của entity là vị trí entity + 4
        int leftmost = player.getX() + 1;
        // bên phải nhất của player là + size 1 ô - 4
        int rightmost = player.getX() + gp.TILESIZE - 1;
        int top = player.getY() + 1;
        int bottom = player.getY() + gp.TILESIZE - 1;

        // xác định vị trí bên trái nhất ở cột nào
        int leftCol = leftmost / gp.TILESIZE;
        // xác định vị trí bên phải nhất ở cột nà0
        int rightCol = rightmost / gp.TILESIZE;
        // xác định trên đầu thuộc dòng nào
        int topRow = top / gp.TILESIZE;
        // xác định dưới thân thuộc dòng nào
        int botRow = bottom / gp.TILESIZE;

        int tileNum1, tileNum2;

        if (player.input.up) {
            topRow = (top - player.getSpeed()) / gp.TILESIZE;
            // lấy giá trị của mapTile tại tọa độ [leftCol][topRow]
            tileNum1 = gp.tileManager.mapTileNum[gp.curMap][leftCol][topRow];
            tileNum2 = gp.tileManager.mapTileNum[gp.curMap][rightCol][topRow];
            // nếu item này có xảy ra va chạm k đc đi tiếp
            if (gp.tileManager.tiles[tileNum1].collision == true
                    || gp.tileManager.tiles[tileNum2].collision == true) {
                // va chạm nhân vật = true
                player.collide = true;
            }
        }
        if (player.input.down) {
            botRow = (bottom + player.getSpeed()) / gp.TILESIZE;
            tileNum1 = gp.tileManager.mapTileNum[gp.curMap][leftCol][botRow];
            tileNum2 = gp.tileManager.mapTileNum[gp.curMap][rightCol][botRow];
            if (gp.tileManager.tiles[tileNum1].collision == true
                    || gp.tileManager.tiles[tileNum2].collision == true) {
                player.collide = true;
            }
        }
        if (player.input.left) {
            leftCol = (leftmost - player.getSpeed()) / gp.TILESIZE;
            tileNum1 = gp.tileManager.mapTileNum[gp.curMap][leftCol][topRow];
            tileNum2 = gp.tileManager.mapTileNum[gp.curMap][leftCol][botRow];
            if (gp.tileManager.tiles[tileNum1].collision == true
                    || gp.tileManager.tiles[tileNum2].collision == true) {
                player.collide = true;
            }
        }
        if (player.input.right) {
            rightCol = (rightmost + player.getSpeed()) / gp.TILESIZE;
            tileNum1 = gp.tileManager.mapTileNum[gp.curMap][rightCol][topRow];
            tileNum2 = gp.tileManager.mapTileNum[gp.curMap][rightCol][botRow];
            if (gp.tileManager.tiles[tileNum1].collision == true
                    || gp.tileManager.tiles[tileNum2].collision == true) {
                player.collide = true;
            }
        }
    }

    public void checkTile(Entity entity) {
        // bên trái nhất của entity là vị trí entity + 4
        int leftmost = entity.getX() + 1;
        // bên phải nhất của entity là + size 1 ô - 4
        int rightmost = entity.getX() + gp.TILESIZE - 1;
        int top = entity.getY() + 1;
        int bottom = entity.getY() + gp.TILESIZE - 1;

        // xác định vị trí bên trái nhất ở cột nào
        int leftCol = leftmost / gp.TILESIZE;
        // xác định vị trí bên phải nhất ở cột nà0
        int rightCol = rightmost / gp.TILESIZE;
        // xác định trên đầu thuộc dòng nào
        int topRow = top / gp.TILESIZE;
        // xác định dưới thân thuộc dòng nào
        int botRow = bottom / gp.TILESIZE;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                // vì trục tọa độ hướng xuống nên muốn đi lên thì trên đầu (đầu - speed) sau đó
                // / size ô
                // xác định topRow đang ở dòng nào
                topRow = (top - entity.getSpeed()) / gp.TILESIZE;
                // lấy giá trị của mapTile tại tọa độ [leftCol][topRow]
                tileNum1 = gp.tileManager.mapTileNum[gp.curMap][leftCol][topRow];
                tileNum2 = gp.tileManager.mapTileNum[gp.curMap][rightCol][topRow];
                // nếu item này có xảy ra va chạm k đc đi tiếp
                if (gp.tileManager.tiles[tileNum1].collision == true
                        || gp.tileManager.tiles[tileNum2].collision == true) {
                    // va chạm nhân vật = true
                    entity.collide = true;
                }
                break;
            case "down":
                botRow = (bottom + entity.getSpeed()) / gp.TILESIZE;
                tileNum1 = gp.tileManager.mapTileNum[gp.curMap][leftCol][botRow];
                tileNum2 = gp.tileManager.mapTileNum[gp.curMap][rightCol][botRow];
                if (gp.tileManager.tiles[tileNum1].collision == true
                        || gp.tileManager.tiles[tileNum2].collision == true) {
                    entity.collide = true;
                }
                break;
            case "left":
                leftCol = (leftmost - entity.getSpeed()) / gp.TILESIZE;
                tileNum1 = gp.tileManager.mapTileNum[gp.curMap][leftCol][topRow];
                tileNum2 = gp.tileManager.mapTileNum[gp.curMap][leftCol][botRow];
                if (gp.tileManager.tiles[tileNum1].collision == true
                        || gp.tileManager.tiles[tileNum2].collision == true) {
                    entity.collide = true;
                }
                break;
            case "right":
                rightCol = (rightmost + entity.getSpeed()) / gp.TILESIZE;
                tileNum1 = gp.tileManager.mapTileNum[gp.curMap][rightCol][topRow];
                tileNum2 = gp.tileManager.mapTileNum[gp.curMap][rightCol][botRow];
                if (gp.tileManager.tiles[tileNum1].collision == true
                        || gp.tileManager.tiles[tileNum2].collision == true) {
                    entity.collide = true;
                }
                break;
        }

    }
}
