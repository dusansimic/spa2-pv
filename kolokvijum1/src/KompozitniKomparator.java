import java.util.Comparator;

public class KompozitniKomparator implements Comparator<Kurs> {
    private final Comparator<Kurs> c1;
    private final Comparator<Kurs> c2;

    public KompozitniKomparator(Comparator<Kurs> c1, Comparator<Kurs> c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public int compare(Kurs k1, Kurs k2) {
        int rez = c1.compare(k1, k2);
        if (rez == 0) {
            rez = c2.compare(k1, k2);
        }
        return rez;
    }
}
