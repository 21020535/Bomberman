package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
    GamePanel gp;
    public Tile[] tiles; // mảng chứa các thông số txt tương ứng của các item
    public int mapTileNum[][]; // mảng 2 chiều để chứa vị trí và giá trị thông số txt tương ứng của các item

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[3];
        mapTileNum = new int [gp.maxCols][gp.maxRows];
        loadMap("/res/map/map1.txt");
        getTileImage();
    }

    // đọc map
    public void loadMap(String filePath) {
        try {
            // đọc file txt
            InputStream in = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            int col = 0;
            int row = 0;
            
            while (col < gp.maxCols && row < gp.maxRows) {
                // đọc dòng đầu tiên của txt thành 1 xâu
                String line = br.readLine();
                // biến đổi xâu vừa đọc thành các phần tử string của mảng numbers
                String numbers[] = line.split(" ");
                
                while (col < gp.maxCols) {
                    // ép kiểu các phần tử của numbers thành int
                    int num = Integer.parseInt(numbers[col]);
                    // đọc thông số vào mảng 2 chiều
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
            // mỗi item tương ứng vs 1 giá trị đã đc mặc định
            tiles[0] = new Tile();
            tiles[0].image = null;
            tiles[0].collision = false; // va chạm = false

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/brick.png"));
            tiles[1].collision = true; // va chạm = true
            tiles[1].breakable = true;
            
            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/wall.png"));
            tiles[2].collision = true;
            tiles[2].breakable = false;
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        // vẽ map bằng bảng txt
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
