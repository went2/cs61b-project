package byow.GeoPrimitives;

public class Container extends Rectangle {
    public Corridor corridor;

    public Container(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void setCorridor(Corridor corridor) {
        this.corridor = corridor;
    }
    public Point<Integer> center() {
        return new Point<>(x + width / 2, y + height / 2);
    }
}
