import org.svetovid.Svetovid;

import java.util.Comparator;
import java.util.List;

// Konkretno stablo koje sadrzi ocene

// Glavna klasa
public class StabloOsobaProgram {

	// Glavni program
	public static void main(String[] args) {

		// Napravimo pomocni objekat za ucitavanje i ispisivanje
		TreeIO<StabloOsoba> io = new TreeIO<>(StabloOsoba.class);

		// Procitamo stablo iz fajla
		StabloOsoba stablo = io.read(Svetovid.in("res/Osobe-mini.txt"));

		// Ispisemo ucitano stablo
		io.print(Svetovid.out, stablo);

		System.out.println(stablo.jePrazno()? "Da":"Ne");

		Osoba osobaX = new Osoba("Raja", "Rajkovic", 31985);
		boolean postoji = stablo.postojiElement(stablo.koren, osobaX);
		System.out.println(postoji?"Da":"Ne");

		stablo.stampajInorder(stablo.koren);
		System.out.println();
		stablo.stampajPreorder(stablo.koren);
		System.out.println();
		stablo.stampajPostorder(stablo.koren);

		stablo.stampajListove(stablo.koren);

		Osoba osobaY = new Osoba("Raja", "Rajkovic",31985);
		StabloOsoba novoStablo = stablo.pronadjiOsobu(osobaY);
		io.print(Svetovid.out, novoStablo);

		List<Osoba> osobe = stablo.stampajSveIspod(osobaX);
		for (Osoba o: osobe)
			System.out.println(o);
		novoStablo.ubaci(osobaY, new Osoba("Sasa", "Pesic", 445234),true);
		io.print(Svetovid.out, novoStablo);

		System.out.println(stablo.roditeljOd(osobaX));

		Comparator<Osoba> komparatorPoPlati = new Comparator<Osoba>() {
			@Override
			public int compare(Osoba o1, Osoba o2) {
				return o1.getPlata() - o2.getPlata();
			}
		};

		Osoba optimalna = stablo.optimalnaOsoba(komparatorPoPlati, stablo.koren);
		System.out.println(optimalna);

		Svetovid.out.println(stablo);
		StabloOsoba kopija = stablo.kopija();
		kopija.ubaci(new Osoba("Sasa", "Pesic", 445234), new Osoba("Dušan", "Simić", 2143531),true);
		Svetovid.out.println(stablo);
		Svetovid.out.println(kopija);
	}
}
