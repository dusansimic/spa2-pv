public class Polje {
    private int x, y, v;

    public Polje(int x, int y, int v) {
        this.x = x;
        this.y = y;
        this.v = v;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getV() {
        return v;
    }

    @Override
    public String toString() {
        return "Polje{" +
                "x=" + x +
                ", y=" + y +
                ", v=" + v +
                '}';
    }
}
