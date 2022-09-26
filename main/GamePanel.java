/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;


import entity.Player;
import tile.TileManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Lenovo
 */
public class GamePanel extends JPanel implements Runnable{
    // screen settings
    final int WINDOW_WIDTH = 1200;
    final int WINDOW_HEIGHT = 720;
    
    public final int TILESIZE = 48;
    
    public int maxCols = 25;
    public int maxRows = 15;
    
    // time and fps handling stuffs
    private long thisTime, lastTime;
    private final int FPS = 60;
    private final float drawInterval = 1000 / FPS;
    
    KeyHandler input = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, input);
    TileManager tileManager = new TileManager(this);
    BufferedImage bg;
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(input);
        this.setFocusable(true);
        this.setBackground(Color.white);
        try {
            bg = ImageIO.read(getClass().getResourceAsStream("/res/bg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
            
            delta += (thisTime - lastTime) / drawInterval;
            
            lastTime = thisTime;
            
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }
    
    public void update() {
        player.update();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, null);
        
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);
        
        tileManager.draw(g2);
        player.draw(g2);
        
        g2.dispose();
    }
}
