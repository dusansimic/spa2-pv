import java.util.Comparator;

public class KomparatorGodina implements Comparator<Predmet> {
	@Override
	public int compare(Predmet p1, Predmet p2) {
		return p1.getGodina() - p2.getGodina();
	}
}
