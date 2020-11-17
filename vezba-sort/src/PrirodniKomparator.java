import java.util.Comparator;

public class PrirodniKomparator implements Comparator<Predmet> {
	@Override
	public int compare(Predmet p1, Predmet p2) {
		Comparator<Predmet> komp = new KompozitniKomparator(new KomparatorDan(), new KompozitniKomparator(new KomparatorSat(), new KomparatorSala()));
		return komp.compare(p1, p2);
	}
}
