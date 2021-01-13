import java.util.Comparator;

public class KomparatorPoUzastopnimPoljimaSaBlagom implements Comparator<Put> {
    @Override
    public int compare(Put o1, Put o2) {

        return o2.getNajviseUzastopnihPoljaSaBlagom() - o1.getNajviseUzastopnihPoljaSaBlagom();

    }
}
