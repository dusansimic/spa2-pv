import org.svetovid.Svetovid;

/**
 * Test program za hash funkcije i equals funkciju
 *
 * Napravljen je da prikazuje rezultate ubacivanja istog fajla u tabele
 * razlicitih velicina.
 *
 * Tip podataka koji se ubacuje se lako moze promeniti prosledjivanjem
 * odgovarajuceg tipa i imena foldera u konstruktoru, ili promenama dve
 * promenljive na pocetku klase.
 *
 * Medjutim da bi to radilo tipovi treba da prosiruju klasu InfoTip i da imaju
 * ili prazan konstruktor ili da se nekako drugacije inicijalizuje objekat
 * `element` u ovoj klasi.
 *
 * Glavni razlog za koriscenje InfoTipa kao dodatne klase je da imamo jednostavan
 * nacin za ucitavanje pojedinacnih podataka datog tipa iz fajla.
 *
 * Takodje se ocekuje da postoje fajlovi tipa "t0" i "t1" za testiranje. Osnova
 * imena fajla se moze proslediti u konstruktoru, ali ce program svakako traziti
 * fajlove koji se zavrsavaju sa 0 i 1. To se moze promeniti u kodu ovog
 * programa.
 *
 * @Version 1.1. 2017
 */
public class TestHash {

	// treba postaviti tip ovog objekta i folder u kome su adekvatni
	// podaci da bi se radilo sa drugim podacima
	// ovo se treba raditi preko konstruktora
	private InfoTip element ;
	private String folder ;
	private String osnova;

	/**
	 *  Konstruktor treba pozvati sa jednom instancom elementa tipa koji
	 *  se ubacuju u skup, te dati folder u kome su podaci (moze biti i
	 *  trenutni "" ili "./") i osnovu imena fajla koji se ucitavaju, tj
	 *  na osnovu ce se dodati "0.txt" i "1.txt" za osnovne i napredne testove.
	 */
	public TestHash(InfoTip element, String folder, String osnova) {
		this.element = element;
		this.folder = folder;
		this.osnova = osnova;
	}

	public TestHash(InfoTip element, String folder) {
		this(element, folder, "/t");
	}

	/*
	 * Pravi praznu mapu i ispisuje je
	 */
	public void emptyTest() {
		StatSet<Object> hash = new StatSet<>();
		hash.printStats();
	}

	/*
	 * ucitava podatke iz datog fajla u tabelu date velicine, pri cemu su podaci
	 * datog tipa, te ispisuje na kraju statistike o tabeli.
	 */
	public void infoTest(String fajl, int size, InfoTip i) {
		StatSet<InfoTip> hash = new StatSet<>();
		System.out.println("poceto ucitavanje");
		if (Svetovid.testIn(fajl)) {
			while (Svetovid.in(fajl).hasMore()) {
				InfoTip sledeci = i.ucitaj(Svetovid.in(fajl));
				hash.add(sledeci);
			}
			Svetovid.closeIn(fajl);

			hash.printStats();
		} else {
			System.err.println("ne moze se otvoriti fajl:" + fajl);
		}
	}

	/*
	 * Testira ubacivanje i izbacivanje elemenata iz tabele, cime se dodatno
	 * proverava da li hash/equals rade kako treba
	 */
	public void addRemoveTest(String fajl, int size, InfoTip i) {
		StatSet<InfoTip> hash = new StatSet<>();
		if (Svetovid.testIn(fajl)) {
			while (Svetovid.in(fajl).hasMore()) {
				hash.add(i.ucitaj(Svetovid.in(fajl)));
			}
			Svetovid.closeIn(fajl);

			System.out
					.println("-- dodatni testovi ubacivanja i izbacivanja --");
			//hash.printStats();
			InfoTip it = hash.someElement();
			if (!hash.add(it)) {
				System.out.println("uspeh: nije ponovo dodat");
			} else {
				System.out.println("neuspeh: ponovo dodat!");
			}
			if (hash.remove(it)) {
				System.out.println("uspeh: uklonjen");
				if (hash.add(it)) {
					System.out.println("uspeh: vracen");
				} else {
					System.out.println("neuspeh: nije vracen");
				}
			} else {
				System.out.println("neuspeh: nije uklonjen!");
			}

			System.out.println("ponovo ubacivanje svih:");
			int br = 0;
			while (Svetovid.in(fajl).hasMore()) {
				if (hash.add(i.ucitaj(Svetovid.in(fajl))))
					br++;
			}
			Svetovid.closeIn(fajl);
			if (br == 0)
				System.out.println("+ok, nijedan nije dodat");
			else
				System.out.println("!!ponovo dodato elemnata:" + br);

		} else {
			System.err.println("ne moze se otvoriti fajl!");
		}
	}

	// i je neki koji bi trebao biti "prazan"
	public void equalsTest(String fajl, InfoTip i) {
		if (Svetovid.testIn(fajl)) {

			InfoTip prvi = i.ucitaj(Svetovid.in(fajl));
			InfoTip drugi = i.ucitaj(Svetovid.in(fajl));

			Svetovid.closeIn(fajl);

			InfoTip prviA = i.ucitaj(Svetovid.in(fajl));

			Svetovid.closeIn(fajl);
			System.out.println();

			System.out
					.println("-- dodatni testovi za equals --");
			equalsHelper(prvi,drugi,false,"prva dva iz fajla");
			equalsHelper(prvi,prvi,true,"dva bukvalno ista");
			equalsHelper(prvi,prviA,true,"dva sa istim sadrzajem");
			equalsHelper(prvi,null,false,"sa null");
			equalsHelper(prvi,i,false,"sa 'praznim' datim u pokretanju");
			equalsHelper(i,drugi,false,"'prazni' sa drugim");

		} else {
			System.err.println("ne moze se otvoriti fajl!");
		}
	}

	private void equalsHelper(Object a, Object b, boolean ocekivano, String poruka) {
		System.out.printf(">>%1$-40s",poruka);
		try {
			boolean res = a.equals(b);
			System.out.println(" "+ ((ocekivano==res)?"+ok":"!!") + '(' + res + ')');
		} catch (Exception ex) {
			System.out.println("!! izuzetak");
			ex.printStackTrace(System.out);
		}
	}

	public void run() {
		// emptyTest();

		String fajl = osnova + "1.txt";

		infoTest(folder + fajl, 997, element);
		Svetovid.out.println();

		// add remove radimo na manjem fajlu
		addRemoveTest(folder + osnova + "0.txt", 101, element);
		equalsTest(folder + osnova + "0.txt", element);
		System.out.print("Hash na 'praznom':");
		try {
			System.out.println(element.hashCode());
		} catch (Exception ex) {
			System.out.println("!! izuzetak");
			ex.printStackTrace(System.out);
		}
		System.out.println();
		System.out.println("NAPOMENA: testovi ne mogu garantovi potpunu ispravnost funkcija.");
	}

	public static void main(String[] args) {
		System.out.println("Ovaj program ne treba pokretati. Pogledati uputstva.");
		System.out.println("Druge klase treba da instanciraju objekat ovog tipa"
			+ " i da pokrenu 'run' na njemu.");
	}
}
