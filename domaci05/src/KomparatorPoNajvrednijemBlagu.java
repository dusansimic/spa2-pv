import java.util.Comparator;

public class KomparatorPoNajvrednijemBlagu implements Comparator<Put> {
    @Override
    public int compare(Put o1, Put o2) {
        return o2.getNajvrednijeBlago() - o1.getNajvrednijeBlago();
    }
}
