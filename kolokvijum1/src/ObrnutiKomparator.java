import java.util.Comparator;

public class ObrnutiKomparator implements Comparator<Kurs> {
    private final Comparator<Kurs> c;

    public ObrnutiKomparator(Comparator<Kurs> c) {
        this.c = c;
    }

    @Override
    public int compare(Kurs k1, Kurs k2) {
        return -c.compare(k1, k2);
    }
}
