import java.util.Comparator;

public class ObrnutiKomparator implements Comparator<Predmet> {
	private final Comparator<Predmet> c;

	public ObrnutiKomparator(Comparator<Predmet> c) {
		this.c = c;
	}

	@Override
	public int compare(Predmet p1, Predmet p2) {
		return -c.compare(p1, p2);
	}
}
