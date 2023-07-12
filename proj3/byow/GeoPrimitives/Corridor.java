package byow.GeoPrimitives;

public class Corridor extends Rectangle{
    public Corridor(int x, int y, int width, int height) {
        super(x,y,width,height);
    }

    // 0 horizontal, 1 vertical
    public int direction() {
        return width > height ? 0 : 1;
    }
}
