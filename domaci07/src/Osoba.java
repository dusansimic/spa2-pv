import java.util.Objects;

// Tip podataka koji predstavlja jednu osobu
public class Osoba {

	private final String ime;
	private final String prezime;
	private final int plata;

	public Osoba(String ime, String prezime, int plata) {
		if (ime == null) {
			throw new IllegalArgumentException("ime");
		}
		this.ime = ime;
		if (prezime == null) {
			throw new IllegalArgumentException("prezime");
		}
		this.prezime = prezime;
		this.plata = plata;
	}

	public String getIme() {
		return ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public int getPlata() {
		return plata;
	}

	public Osoba kopija() {
		return new Osoba(ime, prezime, plata);
	}

	@Override
	public int hashCode() {
		final int prostBroj = 31;
		int rezultat = 1;
		rezultat = prostBroj * rezultat + ime.hashCode();
		rezultat = prostBroj * rezultat + prezime.hashCode();
		return rezultat;
	}

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
		Osoba that = (Osoba) obj;
		if (!Objects.equals(this.ime, that.ime)) {
			return false;
		}
		if (!Objects.equals(this.prezime, that.prezime)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return ime + " " + prezime + " " + plata;
	}

	public static Osoba parseOsoba(String string) {
		if (string == null) {
			return null;
		}
		String[] delovi = string.split(" ");
		return new Osoba(delovi[0], delovi[1], Integer.parseInt(delovi[2]));
	}
}
