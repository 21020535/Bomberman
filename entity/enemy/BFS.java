package entity.enemy;

import java.util.ArrayDeque;
import java.util.Deque;

import main.GamePanel;

class Cell {
    public int x, y;

    public Cell(int i, int j) {
        x = i;
        y = j;
    }
}

public class BFS {
    private static int[][] d = new int[GamePanel.maxCols][GamePanel.maxRows];
    private static boolean[][] visited = new boolean[GamePanel.maxCols][GamePanel.maxRows];
    private static int[] moveX = { 0, 0, 1, -1 };
    private static int[] moveY = { 1, -1, 0, 0 };

    public static int find(int xOnealVal, int yOnealVal, int xVal, int yVal) {
        if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[xOnealVal][yOnealVal]].collision == true)
            return 10000;
        for (int i = 0; i < GamePanel.maxCols; i++) {
            for (int j = 0; j < GamePanel.maxRows; j++) {
                d[i][j] = 0;
                visited[i][j] = false;
            }
        }
        Deque<Cell> q = new ArrayDeque<>();
        q.offer(new Cell(xOnealVal, yOnealVal));
        visited[xOnealVal][yOnealVal] = true;
        while (!q.isEmpty()) {
            int x = q.peek().x;
            int y = q.peek().y;
            q.poll();

            if (x == xVal && y == yVal)
                break;

            for (int i = 0; i < 4; ++i) {
                int u = x + moveX[i];
                int v = y + moveY[i];

                if (u >= GamePanel.maxCols || u < 0)
                    continue;
                if (v >= GamePanel.maxRows || v < 0)
                    continue;
                if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[u][v]].collision == true)
                    continue;

                if (!visited[u][v]) {
                    d[u][v] = d[x][y] + 1;
                    visited[u][v] = true;
                    q.offer(new Cell(u, v));
                }
            }
        }
        return d[xVal][yVal];
    }
}