import java.util.Comparator;

public class KomparatorDan implements Comparator<Predmet> {
	@Override
	public int compare(Predmet p1, Predmet p2) {
		return p1.getDan() - p2.getDan();
	}
}
