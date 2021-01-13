import java.util.Comparator;

public class KomparatorPoBrojuPoljaSaBlagom implements Comparator<Put> {
    @Override
    public int compare(Put o1, Put o2) {
        return o2.getBrojPoljaSaBlagom() - o1.getBrojPoljaSaBlagom();
    }
}
