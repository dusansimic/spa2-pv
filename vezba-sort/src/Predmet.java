import java.util.Comparator;

public class Predmet implements Comparable<Predmet> {
	private final int dan;
	private final int pocetak;
	private final int kraj;
	private final String sala;
	private final int godina;
	private final String ime;

	public Predmet(int dan, int pocetak, int kraj, String sala, int godina, String ime) {
		this.dan = dan;
		this.pocetak = pocetak;
		this.kraj = kraj;
		this.sala = sala;
		this.godina = godina;
		this.ime = ime;
	}

	public int getDan() {
		return dan;
	}

	public int getPocetak() {
		return pocetak;
	}

	public int getKraj() {
		return kraj;
	}

	public String getSala() {
		return sala;
	}

	public int getGodina() {
		return godina;
	}

	public String getIme() {
		return ime;
	}

	@Override
	public String toString() {
		return "Predmet{" +
				"dan=" + dan +
				", pocetak=" + pocetak +
				", kraj=" + kraj +
				", sala='" + sala + '\'' +
				", godina=" + godina +
				", ime='" + ime + '\'' +
				'}';
	}

	@Override
	public int compareTo(Predmet predmet) {
		Comparator<Predmet> komp = new PrirodniKomparator();
		return komp.compare(this, predmet);
	}
}
