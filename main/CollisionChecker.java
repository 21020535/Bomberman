/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entity.Entity;

/**
 *
 * @author Lenovo
 */
public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int leftmost = entity.getX() + 4;
        int rightmost = entity.getX() + gp.TILESIZE - 4;
        int top = entity.getY() + 4;
        int bottom = entity.getY() + gp.TILESIZE - 4;

        int leftCol = leftmost / gp.TILESIZE;
        int rightCol = rightmost / gp.TILESIZE;
        int topRow = top / gp.TILESIZE;
        int botRow = bottom / gp.TILESIZE;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                topRow = (top - entity.getSpeed()) / gp.TILESIZE;
                tileNum1 = gp.tileManager.mapTileNum[leftCol][topRow];
                tileNum2 = gp.tileManager.mapTileNum[rightCol][topRow];
                if (gp.tileManager.tiles[tileNum1].collision == true
                        || gp.tileManager.tiles[tileNum2].collision == true) {
                    entity.collide = true;
                }
                break;
            case "down":
                botRow = (bottom + entity.getSpeed()) / gp.TILESIZE;
                tileNum1 = gp.tileManager.mapTileNum[leftCol][botRow];
                tileNum2 = gp.tileManager.mapTileNum[rightCol][botRow];
                if (gp.tileManager.tiles[tileNum1].collision == true
                        || gp.tileManager.tiles[tileNum2].collision == true) {
                    entity.collide = true;
                }
                break;
            case "left":
                leftCol = (leftmost - entity.getSpeed()) / gp.TILESIZE;
                tileNum1 = gp.tileManager.mapTileNum[leftCol][topRow];
                tileNum2 = gp.tileManager.mapTileNum[leftCol][botRow];
                if (gp.tileManager.tiles[tileNum1].collision == true
                        || gp.tileManager.tiles[tileNum2].collision == true) {
                    entity.collide = true;
                }
                break;
            case "right":
                rightCol = (rightmost + entity.getSpeed()) / gp.TILESIZE;
                tileNum1 = gp.tileManager.mapTileNum[rightCol][topRow];
                tileNum2 = gp.tileManager.mapTileNum[rightCol][botRow];
                if (gp.tileManager.tiles[tileNum1].collision == true
                        || gp.tileManager.tiles[tileNum2].collision == true) {
                    entity.collide = true;
                }
                break;
        }

    }
}
