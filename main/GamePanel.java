/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import entity.Player;
import entity.enemy.Enemy;
import tile.TileManager;

/**
 *
 * @author Lenovo
 */
public class GamePanel extends JPanel implements Runnable{
    // screen settings
    // cài đặt màn hình
    final int WINDOW_WIDTH = 1200;
    final int WINDOW_HEIGHT = 720;

    public final int TILESIZE = 48; // size của 1 ô
    
    public int maxCols = 25; // map gồm 25 cột
    public int maxRows = 15; // 15 dòng
    
    // time and fps handling stuffs
    private long thisTime, lastTime;
    private final int FPS = 60;
    private final float drawInterval = 1000 / FPS;
    
    public KeyHandler input = new KeyHandler();
    public Thread gameThread;
    public Player player = new Player(this, input);
    public Enemy enemy = new Enemy(this);
    public TileManager tileManager = new TileManager(this);
    BufferedImage bg;
    public CollisionChecker cChecker = new CollisionChecker(this);

    // in ra bg
    public GamePanel() {
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setDoubleBuffered(true);
        this.addKeyListener(input);
        this.setFocusable(true);
        try {
            bg = ImageIO.read(getClass().getResourceAsStream("/res/bg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // luồng bắt đầu game
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override
    public void run() {
        lastTime = System.currentTimeMillis();
        double delta = 0;
        
        while (gameThread != null) {
            thisTime = System.currentTimeMillis();
            // xử lý fps
            delta += (thisTime - lastTime) / drawInterval;
            
            lastTime = thisTime;
            
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    // update nhân vật di chuyển va chạm thả bom
    public void update() {
        player.update();
        enemy.update();
    }

    // vẽ bg
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, null);
        
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);
        
        tileManager.draw(g2);
        
        player.draw(g2);
        enemy.draw(g2);
        
        g2.dispose();
    }
}
