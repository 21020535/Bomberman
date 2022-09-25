/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;


import entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author Lenovo
 */
public class GamePanel extends JPanel implements Runnable{
    // screen settings
    final int WINDOW_WIDTH = 1280;
    final int WINDOW_HEIGHT = 720;
    
    // time and fps handling stuffs
    private long thisTime, lastTime;
    private final int FPS = 60;
    private final float drawInterval = 1000 / FPS;
    
    KeyHandler input = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, input);
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(input);
        this.setFocusable(true);
        this.setBackground(Color.white);
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
        
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);
        
        
        player.draw(g2);
        
        g2.dispose();
    }
}
