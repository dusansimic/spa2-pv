import java.util.Comparator;

public class KomparatorIme implements Comparator<Predmet> {
	@Override
	public int compare(Predmet p1, Predmet p2) {
		return p1.getIme().compareTo(p2.getIme());
	}
}
