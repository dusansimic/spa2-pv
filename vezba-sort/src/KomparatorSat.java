import java.util.Comparator;

public class KomparatorSat implements Comparator<Predmet> {
	@Override
	public int compare(Predmet p1, Predmet p2) {
		return p1.getPocetak() - p2.getPocetak();
	}
}
