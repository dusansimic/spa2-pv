import org.svetovid.Svetovid;

// Konkretno stablo koje sadrzi ocene

// Glavna klasa
public class StabloOsobaProgram {

	// Glavni program
	public static void main(String[] args) {

		// Napravimo pomocni objekat za ucitavanje i ispisivanje
		TreeIO<StabloOsoba> io = new TreeIO<>(StabloOsoba.class);

		// Procitamo stablo iz fajla
		StabloOsoba stablo = io.read(Svetovid.in("res/Osobe.txt"));

		// Ispisemo ucitano stablo
		io.print(Svetovid.out, stablo);
//
//		System.out.println(stablo.prosecnaPlata2());
//
//		Osoba x = new Osoba("Nina", "Ninkov", 219710);
//		stablo.sviNadredjeni(x).stream().forEach(System.out::println);
//
//		for (Osoba o: stablo.sviSaIstomPlatom())
//			System.out.println(o);

//		stablo.ispisiOsobeSaVecomPlatomOdSefa();

//		stablo.vecaPlataOdDirektora().stream().forEach(System.out::println);

		stablo.plataIznadProseka().stream().forEach(System.out::println);

		System.out.println(stablo.drugaPoReduSaNajvecomPlatom());

	}
}
