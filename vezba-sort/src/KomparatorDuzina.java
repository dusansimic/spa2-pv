import java.util.Comparator;

public class KomparatorDuzina implements Comparator<Predmet> {
	@Override
	public int compare(Predmet p1, Predmet p2) {
		return (p1.getKraj() - p1.getPocetak()) - (p2.getKraj() - p2.getPocetak());
	}
}
