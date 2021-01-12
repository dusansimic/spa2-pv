import java.util.Objects;

// Tip podataka koji predstavlja jedan automobil
// Jednom napravljen objekat ovog tipa nije moguce menjati
public final class Automobil {

	// Model automobila, npr. "Opel Corsa"
	private final String model;

	// Njegova boja, npr. "Bela"
	private final String boja;

	// Registarska oznaka, npr. "NS 123-AB"
	private final String registracija;

	// Broj kilometara koliko je automobil presao, npr. 24364
	private final long kilometraza;

	// Jedini konstruktor pomocu kojeg je moguce napraviti automobil
	public Automobil(String model, String boja, String registracija, long kilometraza) {
		if (model == null) {
			throw new IllegalArgumentException("model");
		}
		this.model = model;
		if (boja == null) {
			throw new IllegalArgumentException("boja");
		}
		this.boja = boja;
		if (registracija == null) {
			throw new IllegalArgumentException("registracija");
		}
		this.registracija = registracija;
		this.kilometraza = kilometraza;
	}

	public String getModel() {
		return model;
	}

	public String getBoja() {
		return boja;
	}

	public String getRegistracija() {
		return registracija;
	}

	public long getKilometraza() {
		return kilometraza;
	}

	@Override
	public int hashCode() {
		final int prost = 31;
		int rezultat = 1;
		rezultat = prost * rezultat + model.hashCode();
		rezultat = prost * rezultat + boja.hashCode();
		rezultat = prost * rezultat + registracija.hashCode();
		rezultat = prost * rezultat + (int) (kilometraza ^ (kilometraza >>> 32));
		return rezultat;
	}

	// Dva automobila su jednaka ako su im jednaka sva polja
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
		Automobil that = (Automobil) obj;
		if (!Objects.equals(this.model,  that.model)) {
			return false;
		}
		if (!Objects.equals(this.boja,  that.boja)) {
			return false;
		}
		if (!Objects.equals(this.registracija,  that.registracija)) {
			return false;
		}
		if (this.kilometraza != that.kilometraza) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return model + ";" + boja + ";" + registracija + ";" + kilometraza;
	}

	public static Automobil fromString(String string) {
		if (string == null) {
			return null;
		}
		String[] delovi = string.split(";");
		return new Automobil(delovi[0], delovi[1], delovi[2], Long.parseLong(delovi[3]));
	}
}
