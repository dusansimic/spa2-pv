public class Polje {
    private int x, y, v;

    public int getX() {
        return x;
    }

    @Override
    public String toString() {
        return "(" + x +
                ", " + y +
                ", " + v + ")";
    }

    public int getY() {
        return y;
    }

    public int getV() {
        return v;
    }

    public Polje(int x, int y, int v) {
        this.x = x;
        this.y = y;
        this.v = v;
    }
}
