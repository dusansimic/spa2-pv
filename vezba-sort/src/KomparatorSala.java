import java.util.Comparator;

public class KomparatorSala implements Comparator<Predmet> {
	@Override
	public int compare(Predmet p1, Predmet p2) {
		return p1.getSala().compareTo(p2.getSala());
	}
}
