import java.util.Comparator;

public class KomparatorPoVrednosti implements Comparator<Put> {
    @Override
    public int compare(Put o1, Put o2) {
        return o2.getVrednost() - o1.getVrednost();
    }
}
