package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

// xu ly tat ca giao dien ng dung
// menu,in chu,pause,option...
public class UI {
    GamePanel gp;
    Font arial_40;

    BufferedImage bombImage, menuImage;
    public int commandNumber = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        try {
            bombImage = ImageIO.read(getClass().getResourceAsStream("/res/bomb/fake.png"));
            menuImage = ImageIO.read(getClass().getResourceAsStream("/res/menu.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        // menu
        if (gp.gameState == gp.titleState) {
            drawTitleScreen(g2);
        }
    }

    public void drawTitleScreen(Graphics2D g2) {
        // Ten Game
        // ve man hinh menu
        g2.drawImage(menuImage,0,0,gp.WINDOW_WIDTH,gp.WINDOW_HEIGHT,null);
        // ve ten game
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
        String text = "BOMBERMAN";
        int x = getForCenteredtext(text,g2);
        int y = gp.TILESIZE * 3;

        // bong chu
        g2.setColor(Color.RED);
        g2.drawString(text, x + 8, y + 8);

        // mau chu
        g2.setColor(Color.white);
        g2.drawString(text, x,y);

        // menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));

        text = "NEW GAME";
        x = getForCenteredtext(text,g2);
        y += gp.TILESIZE * 4;
        g2.drawString(text,x,y);
        if (commandNumber == 0) {
            g2.drawString(">",x - gp.TILESIZE, y);
        }

        text = "LOAD GAME";
        x = getForCenteredtext(text,g2);
        y += gp.TILESIZE ;
        g2.drawString(text,x,y);
        if (commandNumber == 1) {
            g2.drawString(">",x - gp.TILESIZE, y);
        }

        text = "QUIT";
        x = getForCenteredtext(text,g2);
        y += gp.TILESIZE ;
        g2.drawString(text,x,y);
        if (commandNumber == 2) {
            g2.drawString(">",x - gp.TILESIZE, y);
        }
    }


//    public void drawItem(Graphics2D g2) {
//        g2.drawImage(bombImage, 0, 0, gp.TILESIZE, gp.TILESIZE,null);
//    }
    // lay vi tri chinh giua
    public int getForCenteredtext(String text,Graphics2D g2) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.WINDOW_WIDTH/2 - length/2;
        return x;
    }
}
