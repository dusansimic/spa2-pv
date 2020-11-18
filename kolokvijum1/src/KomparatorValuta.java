import java.util.Comparator;

public class KomparatorValuta implements Comparator<Kurs> {
    @Override
    public int compare(Kurs k1, Kurs k2) {
        return k1.getValuta().compareTo(k2.getValuta());
    }
}
