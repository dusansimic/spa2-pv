import java.util.Comparator;

public class KomparatorSrednji implements Comparator<Kurs> {
    @Override
    public int compare(Kurs k1, Kurs k2) {
        return k1.getSrednji() - k2.getSrednji();
    }
}
