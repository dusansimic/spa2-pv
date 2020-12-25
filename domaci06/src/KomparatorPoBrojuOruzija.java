import java.util.Comparator;

public class KomparatorPoBrojuOruzija implements Comparator<Put> {
    @Override
    public int compare(Put p1, Put p2) {
        return p1.getBrojPoljaSaOruzijem() - p2.getBrojPoljaSaOruzijem();
    }
}
