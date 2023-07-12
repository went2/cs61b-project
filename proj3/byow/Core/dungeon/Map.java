package byow.Core.dungeon;

import byow.TileEngine.TETile;

public class Map {
    public int width = 64;
    public int height = 30;
    protected TETile[][] tiles;
    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new TETile[width][height];
    }

    public void fill(TETile tile) {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                tiles[x][y] = tile;
            }
        }
    }

    public TETile getTile(int x, int y) {
        return tiles[x][y];
    }

    public TETile[][] getTiles() {
        return tiles;
    }
}
