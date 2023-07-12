package byow.Core;

import java.util.*;

public class DungeonGenerator {
    private static final int SIZE_X = 50;
    private static final int SIZE_Y = 50;

    private static final int ROOM_MAX_SIZE = 10;
    private static final int ROOM_MIN_SIZE = 6;
    private static final int MAX_ROOMS = 30;

    private static final char TILE_WALL = '#';
    private static final char TILE_FLOOR = '.';
    private static final char TILE_DOOR = '+';

    private static char[][] dungeon;

    private static class Room {
        int x, y, width, height;

        Room(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        boolean intersects(Room other) {
            return (x <= other.x + other.width && x + width >= other.x &&
                    y <= other.y + other.height && y + height >= other.y);
        }

        void create() {
            for (int i = x; i < x + width; i++) {
                for (int j = y; j < y + height; j++) {
                    dungeon[i][j] = TILE_FLOOR;
                }
            }
        }
    }

    private static void initializeDungeon() {
        dungeon = new char[SIZE_X][SIZE_Y];
        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                dungeon[x][y] = TILE_WALL;
            }
        }
    }

    private static boolean canCarve(Room room) {
        for (int x = room.x - 1; x < room.x + room.width + 1; x++) {
            for (int y = room.y - 1; y < room.y + room.height + 1; y++) {
                if (x < 0 || x >= SIZE_X || y < 0 || y >= SIZE_Y) {
                    return false;
                }
                if (dungeon[x][y] != TILE_WALL) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void carve(Room room) {
        for (int x = room.x; x < room.x + room.width; x++) {
            for (int y = room.y; y < room.y + room.height; y++) {
                dungeon[x][y] = TILE_FLOOR;
            }
        }
    }

    private static void generateDungeon() {
        Random random = new Random();

        List<Room> rooms = new ArrayList<>();
        for (int i = 0; i < MAX_ROOMS; i++) {
            int width = random.nextInt(ROOM_MAX_SIZE - ROOM_MIN_SIZE + 1) + ROOM_MIN_SIZE;
            int height = random.nextInt(ROOM_MAX_SIZE - ROOM_MIN_SIZE + 1) + ROOM_MIN_SIZE;
            int x = random.nextInt(SIZE_X - width - 1) + 1;
            int y = random.nextInt(SIZE_Y - height - 1) + 1;

            Room newRoom = new Room(x, y, width, height);
            boolean intersects = false;
            for (Room room : rooms) {
                if (newRoom.intersects(room)) {
                    intersects = true;
                    break;
                }
            }

            if (!intersects) {
                newRoom.create();
                carve(newRoom);
                rooms.add(newRoom);
            }
        }
    }

    private static void printDungeon() {
        for (int y = 0; y < SIZE_Y; y++) {
            for (int x = 0; x < SIZE_X; x++) {
                System.out.print(dungeon[x][y]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        initializeDungeon();
        generateDungeon();
        printDungeon();
    }
}
