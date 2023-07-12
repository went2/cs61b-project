package byow.GeoPrimitives;

import java.util.Iterator;

public class Rectangle implements Iterable<Point<Integer>>{
    public int x;
    public int y;
    public int width;
    public int height;

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Tests if the interior of the rect intersects the interior of a specified rect area.
    public boolean intersects(Rectangle r) {
        for(Point<Integer> grid : r) {
            if(contains(grid)) {
                return true;
            }
        }
        return false;
    }

    // Tests if the specified coordinates are inside the boundary of the Rect
    public boolean contains(int x, int y) {
        return inRangeX(x) && inRangeY(y);
    }

    public boolean contains(Point<Integer> g) {
        return inRangeX(g.x) && inRangeY(g.y);
    }

    private boolean inRangeX(int x) {
        return x >= this.x && x <= this.x + width - 1;
    }
    private boolean inRangeY(int y) {
        return y >= this.y && y <= this.y + height - 1;
    }

    public Iterator<Point<Integer>> iterator() {
        return new RectIterator();
    }

    private class RectIterator implements Iterator<Point<Integer>> {
        // one grid map to one tile in the map
        private final Point<Integer> pos;

        public RectIterator() {
            pos = new Point<>(x,y);
        }

        public boolean hasNext() {
            return contains(pos);
        }
        public Point<Integer> next() {
            Point<Integer> returnGrid = new Point<>(pos.x, pos.y);
            if(pos.x == x + width - 1) {
                pos.x = x;
                pos.y += 1;
            } else {
                pos.x += 1;
            }
            return returnGrid;
        }
    }
}
