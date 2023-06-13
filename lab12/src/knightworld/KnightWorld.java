package knightworld;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Draws a world consisting of knight-move holes.
 */
public class KnightWorld {

    private TETile[][] tiles;
    private Queue<int[]> queue;
    private final boolean[] drawnGrids;

    private int WIDTH = 50;
    private int HEIGHT = 30;
    private int holeSize = 1;
    private final int[][] knightPaths = {
            { -1,-2 }, { -2, 1 },
            { 2, -1 }, { 1, 2 }
    };

    public KnightWorld(int width, int height, int holeSize) {
        WIDTH = width;
        HEIGHT = height;
        this.holeSize = holeSize;

        // init tiles and fill with floors
        tiles = new TETile[width][height];
        for (int x = 0; x < width; x++) {
            Arrays.fill(tiles[x], Tileset.LOCKED_DOOR2);
        }

        // then draw knight holes
        // init drawnGrids
        drawnGrids = new boolean[width * height];
        queue = new LinkedList<>();

        drawHoles();
    }

    private void drawHoles() {
        // pick a random location as a start
        int[] randomLocation = getRandomLocation();
        addHolesToBeDrawn(randomLocation);

        // 从随机位置开始算 knight 位置并绘制
        while(!queue.isEmpty()) {
            int[] location = queue.remove();
            visit(location);
        }
    }

    // 输入一个点，获取它所在的 size * size 大小 hole 的所有点坐标，将它们加入 queue 等待 visit
    // 传入的点为 hole 的左下角的点
    private void addHolesToBeDrawn(int[] coordinate) {
        if(holeSize == 1) {
            queue.add(coordinate);
        } else {
            for(int i=0; i<holeSize; i++) {
                for(int j=0; j<holeSize; j++) {
                    int newX = coordinate[0] + i;
                    int newY = coordinate[1] + j;
                    addCoordinatesToQueue(newX, newY);
                }
            }
        }
    }

    // visit means：
    // 1) draw point as hole
    // 2) mark visited and
    // 3) add its unvisited siblings to queue
    private void visit(int[] point) {
        int number = xyToNumber(point);
        int x = point[0]; int y = point[1];
        // draw and mark
        tiles[x][y] = Tileset.NOTHING;
        drawnGrids[number] = true;
        addUnvisitedKnightPaths(point);
    }

    // 输入一个点，计算它的 Knight Path（符合马走法的可能点位）
    // 将合法且未访问的点加入 queue
    private void addUnvisitedKnightPaths(int[] point) {
        int x = point[0];
        int y = point[1];
        for(int[] p : knightPaths) {
            int newX = x + p[0] * holeSize;
            int newY = y + p[1] * holeSize;
            addCoordinatesToQueue(newX, newY);
        }
    }


    // util functions
    // 输入坐标，将合法且未访问过的hole坐标加入queue
    private void addCoordinatesToQueue(int x, int y) {
        if(isValidCoordinate(x, y) && !isVisited(x, y)) {
            int[] newPoint = new int[]{ x, y };
            queue.add(newPoint);
        }
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
    }

    private boolean isVisited(int x, int y) {
        int index = xyToNumber(x, y);
        return drawnGrids[index];
    }

    // 将一个二维数组的坐标转为对应一维数组中的编号
    private int xyToNumber(int[] coordinate) {
        int x = coordinate[0];
        int y = coordinate[1];
        return y * WIDTH + x;
    }

    private int xyToNumber(int x, int y) {
        return y * WIDTH + x;
    }

    private int[] getRandomLocation() {
        // generate random x coordinate in [0, WIDTH);
        int x = ThreadLocalRandom.current().nextInt(0, WIDTH);
        int y = ThreadLocalRandom.current().nextInt(0, HEIGHT);
        return new int[]{ x,y };
    }

    /** Returns the tiles associated with this KnightWorld. */
    public TETile[][] getTiles() {
        return tiles;
    }

    public static void main(String[] args) {
        // Change these parameters as necessary
        int width = 50;
        int height = 40;
        int holeSize = 4;

        KnightWorld knightWorld = new KnightWorld(width, height, holeSize);

        TERenderer ter = new TERenderer();
        ter.initialize(width, height);
        ter.renderFrame(knightWorld.getTiles());

    }
}
