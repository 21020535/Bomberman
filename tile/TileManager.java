package tile;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import main.GamePanel;

public class TileManager {
    GamePanel gp;
    Tile[] tiles;
    int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[3];
        mapTileNum = new int [gp.maxCols][gp.maxRows];
        loadMap();
        getTileImage();
    }
    
    public void loadMap() {
        try {
            InputStream in = getClass().getResourceAsStream("/res/map/map1.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            int col = 0;
            int row = 0;
            
            while (col < gp.maxCols && row < gp.maxRows) {
                String line = br.readLine();
                
                String numbers[] = line.split("");
                
                while (col < gp.maxCols) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                
                col = 0;
                row++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTileImage() {
        try {
            tiles[0] = new Tile();
            tiles[0].image = null;

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/brick.png"));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        
        while (col < gp.maxCols && row < gp.maxRows) {
            int tileNum = mapTileNum[col][row];
            g2.drawImage(tiles[tileNum].image, x, y, gp.TILESIZE, gp.TILESIZE, null);
            col++;
            x += gp.TILESIZE;
            
            if (col == gp.maxCols) {
                col = 0;
                x = 0;
                row++;
                y += gp.TILESIZE;
            }
        }
    }

}
