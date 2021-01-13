import java.util.Comparator;

public class KomparatorPoDuzini implements Comparator<Put> {
    @Override
    public int compare(Put o1, Put o2) {
        return o1.getLength() - o2.getLength();
    }
}
