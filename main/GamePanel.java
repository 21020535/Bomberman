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
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import entity.Player;
import entity.enemy.Enemy;
import tile.TileManager;

/**
 *
 * @author Lenovo
 */
public class GamePanel extends JPanel implements Runnable {
    // screen settings
    // cài đặt màn hình
    final int WINDOW_WIDTH = 1200;
    final int WINDOW_HEIGHT = 720;

    public final int TILESIZE = 48; // size của 1 ô

    public int maxCols = 25; // map gồm 25 cột
    public int maxRows = 15; // 15 dòng
    public final int maxMap = 3;
    public int curMap = 0;

    // time and fps handling stuffs
    private long thisTime, lastTime;
//    public boolean fullScreenOn = false;
    private final int FPS = 60;
    private final float drawInterval = 1000 / FPS;
    public KeyHandler input = new KeyHandler(this); // keyH

    public UI ui = new UI(this);

    public Thread gameThread;
    public Player player = new Player(this, input);
    // public Enemy enemy = new Enemy(this);
    public List<Enemy> enemies = new ArrayList<>();

    public TileManager tileManager;
    BufferedImage bg;
    public CollisionChecker cChecker = new CollisionChecker(this);

    Sound sound = new Sound();
    Sound se = new Sound();

    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int optionsState = 2;
    public final int gameOverState = 3;

    public boolean playing;

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

    public void setupGame() {
        gameState = titleState;
        ui = new UI(this);
        tileManager = new TileManager(this);
        player = new Player(this, input);
        // sound = new Sound();
        // se = new Sound();
        enemies.clear();
        enemies.add(new Enemy(this));
        playing = true;
    }

    // luồng bắt đầu game
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            setupGame();
            playMusic(3);
            lastTime = System.currentTimeMillis();
            double delta = 0;
            while (playing) {
                thisTime = System.currentTimeMillis();
                // xử lý fps
                delta += (thisTime - lastTime) / drawInterval;

                lastTime = thisTime;

                if (delta >= 1) {
                    // if (!paused) {
                    update();
                    repaint();
                    // }
                    // if (input.pause == true) {
                    //     paused = true;
                    // } else {
                    //     paused = false;
                    // }
                    delta--;
                }
            }
            stopMusic();
        }
    }

    // update nhân vật di chuyển va chạm thả bom
    public void update() {
        if (gameState == playState) {
            player.update();
            // enemy.update();
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).update();
            }
            if (player.dead) {
                playing = false;
            }
        }
        if (gameState == optionsState) {
            //
        }
    }

    // vẽ bg
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, null);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);

        // tile screen
        if (gameState != playState) {
            ui.draw(g2);
        } else {
            tileManager.draw(g2);
            player.draw(g2);
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).draw(g2);
            }
        }
        g2.dispose();
        // tile

    }

    public void playMusic(int num) {
        sound.setFile(num);
        sound.play();
        sound.loop();
    }

    public void stopMusic() {
        sound.stop();
//        System.out.println("music was stopped");
    }

    public void playSE(int num) {
        se.setFile(num);
        se.play();
    }
}
