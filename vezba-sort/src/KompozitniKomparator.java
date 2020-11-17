import java.util.Comparator;

public class KompozitniKomparator implements Comparator<Predmet> {
	private final Comparator<Predmet> c1;
	private final Comparator<Predmet> c2;

	public KompozitniKomparator(Comparator<Predmet> c1, Comparator<Predmet> c2) {
		this.c1 = c1;
		this.c2 = c2;
	}

	@Override
	public int compare(Predmet p1, Predmet p2) {
		int rez = c1.compare(p1, p2);
		if (rez == 0) {
			rez = c2.compare(p1, p2);
		}
		return rez;
	}
}
