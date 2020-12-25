import java.util.ArrayList;

public class Put {
    private ArrayList<Polje> polja;

    public Put() {
        this.polja = new ArrayList<Polje>();
    }

    public Put(Put original) {
        this();
        for (Polje p : original.polja) {
            dodaj(p.getX(), p.getY(), p.getV());
        }
    }

    public void dodaj(int x, int y, int v) {
        polja.add(new Polje(x, y, v));
    }

    public void izbaciKraj() {
        if (getLength() > 0) {
            polja.remove(getLength() - 1);
        }
    }

    public int getLength() {
        return polja.size();
    }

    public int getBrojPoljaSaOruzijem() {
        return (int) polja.stream().filter(polje -> polje.getV() >= Mapa.ORUZIJE_MIN && polje.getV() <= Mapa.ORUZIJE_MAX).count();
    }

    @Override
    public String toString() {
        return "Put{" +
                "polja=" + polja +
                '}';
    }
}
