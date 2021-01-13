import java.util.Objects;

// Tip podataka koji predstavlja jednog patuljka
// Jednom napravljen objekat ovog tipa nije moguce menjati
public final class Patuljak {

	// Ime patuljka, npr. "Dimptibunvalt"
	private final String ime;

	// Koliko je goblina ubio, npr. 42
	private final int ubioGoblina;

	// Koje godine se patuljak rodio, npr. 1273
	private final int godinaRodjenja;

	// Koliko kila zlata je iskopao
	private final double iskopaoZlata;

	// Jedini konstruktor pomocu kojeg je moguce napraviti patuljka
	public Patuljak(String ime, int ubioGoblina, int godinaRodjenja, double iskopaoZlata) {
		if (ime == null) {
			throw new IllegalArgumentException("ime");
		}
		this.ime = ime;
		this.ubioGoblina = ubioGoblina;
		this.godinaRodjenja = godinaRodjenja;
		this.iskopaoZlata = iskopaoZlata;
	}

	public String getIme() {
		return ime;
	}

	public int getUbioGoblina() {
		return ubioGoblina;
	}

	public int getGodinaRodjenja() {
		return godinaRodjenja;
	}

	public double getIskopaoZlata() {
		return iskopaoZlata;
	}

	@Override
	public int hashCode() {
		final int prost = 31;
		int rezultat = 1;
		rezultat = prost * rezultat + ime.hashCode();
		rezultat = prost * rezultat + ubioGoblina;
		rezultat = prost * rezultat + godinaRodjenja;
		long temp = Double.doubleToLongBits(iskopaoZlata);
		rezultat = prost * rezultat + (int) (temp ^ (temp >>> 32));
		return rezultat;
	}

	// Dva patuljka su jednaka ako su im jednaka sva polja
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Patuljak that = (Patuljak) obj;
		if (!Objects.equals(this.ime,  that.ime)) {
			return false;
		}
		if (this.ubioGoblina != that.ubioGoblina) {
			return false;
		}
		if (this.godinaRodjenja != that.godinaRodjenja) {
			return false;
		}
		if (this.iskopaoZlata != that.iskopaoZlata) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return ime + ";" + ubioGoblina + ";" + godinaRodjenja + ";" + iskopaoZlata;
	}

	public static Patuljak fromString(String string) {
		if (string == null) {
			return null;
		}
		String[] delovi = string.split(";");
		return new Patuljak(delovi[0], Integer.parseInt(delovi[1]), Integer.parseInt(delovi[2]), Double.parseDouble(delovi[3]));
	}
}
