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
import entity.enemy.*;
import tile.TileManager;

/**
 *
 * @author Lenovo
 */
public class GamePanel extends JPanel implements Runnable {
    // screen settings
    // cài đặt màn hình
    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 720;

    public final int TILESIZE = 48; // size của 1 ô

    public static int maxCols = 25; // map gồm 25 cột
    public static int maxRows = 15; // 15 dòng

    // time and fps handling stuffs
    private long thisTime, lastTime;
    // public boolean fullScreenOn = false;
    private final int FPS = 60;
    private final float drawInterval = 1000 / FPS;

    public Thread gameThread;

    public KeyHandler input = new KeyHandler(this); // keyH

    public UI ui = new UI(this);

    public static TileManager tileManager;

    public CollisionChecker cChecker = new CollisionChecker(this);

    public Player player = new Player(this, input);
    public List<Enemy> enemies = new ArrayList<>();

    private Lighting lighting;
    private BufferedImage bg;

    Sound sound = new Sound();
    Sound se = new Sound();

    public int level = 1;
    public final int maxLevel = 3;

    public int state;
    public final int titleState = 0;
    public final int playState = 1;
    public final int optionsState = 2;
    public final int gameOverState = 3;
    public final int gameWinState = 4;
    public boolean playing;

    private boolean set = false;

    // in ra bg
    public GamePanel() {
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setDoubleBuffered(true);
        this.addKeyListener(input);
        this.setFocusable(true);
        
    }

    public void setupGame() {
        state = titleState;
        try {
            bg = ImageIO.read(getClass().getResourceAsStream("/res/bg" + level + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ui = new UI(this);
        tileManager = new TileManager(this, level);
        if (level >= 1) {
            lighting = new Lighting(this, 350);
        }
        player = new Player(this, input);
        enemySetup();
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
            playMusic(3);
            setupGame();
            lastTime = System.currentTimeMillis();
            double delta = 0;
            while (playing) {
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
    }

    // update nhân vật di chuyển va chạm thả bom
    public void update() {
        if (state == playState) {
            player.update();
            // enemy.update();
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).update();
                if (enemies.get(i).finish) {
                    enemies.remove(i);
                    i--;
                }
            }
            if (player.dead) {
                if (!set) {
                    player.getPlayerImage();
                    set = true;
                }
                if (player.finish) {
                    state = gameOverState;
                    stopMusic();
                }
            }
            if (level > maxLevel) {
                state = gameWinState;
                stopMusic();
            }
        }
        if (state == optionsState) {
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
        if (state != playState) {
            ui.draw(g2);
        } else {
            tileManager.draw(g2);
            player.draw(g2);
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).draw(g2);
            }
            if (level >= 1) {
                // lighting.draw(g2);
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
        // System.out.println("music was stopped");
    }

    public void playSE(int num) {
        se.setFile(num);
        se.play();
    }

    private void enemySetup() {
        enemies.clear();
        switch (level) {
            case 1:
                enemies.add(new Smile1(1104, 624, this));
                enemies.add(new Slime2(1104, 48, this));
                enemies.add(new Slime3(48, 624, this));
                enemies.add(new Slime4(624,48,this));
                break;
            case 2:
                enemies.add(new Duck(1056, 48*8, this));
                enemies.add(new Pug(1056, 48, this));
                enemies.add(new Chicken(48,624,this));
                enemies.add(new Squirrel(48*18, 48*11, this));
                break;
            case 3:
                enemies.add(new Dumb(1104, 48, this));
                enemies.add(new Kiki(1104, 624, this));
                enemies.add(new Zombie(48, 624, this));
                enemies.add(new Bat(1104, 48*7, this));
                enemies.add(new Zombie(48, 624, this));
                break;
        }
    }
}
