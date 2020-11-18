import java.util.Comparator;

public class KomparatorAbsDeltaProcenatProdajniKupovni implements Comparator<Kurs> {
    @Override
    public int compare(Kurs k1, Kurs k2) {
        double p1 = Math.abs((k1.getKupovni() * 100.0) / k1.getProdajni() - 100.0);
        double p2 = Math.abs((k2.getKupovni() * 100.0) / k2.getProdajni() - 100.0);
        double rez = p1 - p2;
        return rez > 0.0 ? 1 : (rez < 0.0 ? -1 : 0);
    }
}
