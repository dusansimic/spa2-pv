import java.util.Comparator;

public class KomparatorAbsDeltaProdajniKupovni implements Comparator<Kurs> {
    @Override
    public int compare(Kurs k1, Kurs k2) {
        int d1 = Math.abs(k1.getProdajni() - k1.getKupovni());
        int d2 = Math.abs(k2.getProdajni() - k2.getKupovni());
        return d1 - d2;
    }
}
