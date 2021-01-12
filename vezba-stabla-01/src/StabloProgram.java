import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.svetovid.Svetovid;

class Stablo {
	private static class Cvor {
		Automobil info;
		Cvor levo;
		Cvor desno;

		public Cvor(Automobil info, Cvor levo, Cvor desno) {
			this.info = info;
			this.levo = levo;
			this.desno = desno;
		}

		public Cvor kopija() {
			return new Cvor(info, levo, desno);
		}
	}

	private Cvor koren;

	// Konstruktor za prazno stablo
	public Stablo() {
		this.koren = null;
	}

	public Stablo(Automobil auto) {
		this.koren = new Cvor(auto, null, null);
	}

	protected Stablo(Cvor cvor) {
		this.koren = cvor;
	}

	// ------ dodati opisane metode ------------

	// Ispisuje registracije svih automobila iz Novog Sada
	public void ispisiNS() {
		System.out.println("Svi automobili iz Novog Sada: ");
		sviIzNs(koren);
	}

	private void sviIzNs(Cvor cvor) {
		if (cvor == null)
			return;

		if (cvor.info.getRegistracija().startsWith("NS"))
			System.out.println(cvor.info.getRegistracija() + " | " + cvor.info.getKilometraza());

		sviIzNs(cvor.levo);
		sviIzNs(cvor.desno);
	}

	// Vraća sumu kilometraza svih plavih automobila
	public long presliPlavi() {
		Suma suma = new Suma();
		presliPlavi(koren, suma);
		System.out.println("Plavi automobili su presli: ");
		return suma.getSuma();
	}

	public long presliPlavi2() {
		return presliPlavi(koren);
	}

	private void presliPlavi(Cvor cvor, Suma suma) {
		if (cvor == null)
			return;

		if (cvor.info.getBoja().equals("Plava"))
			suma.setSuma(cvor.info.getKilometraza());

		presliPlavi(cvor.levo, suma);
		presliPlavi(cvor.desno, suma);
	}

	// Alternativna verzija prethodne metode bez klase Suma
	private long presliPlavi(Cvor cvor) {
		if (cvor == null)
			return 0;

		if (cvor.info.getBoja().equals("Plava"))
			return cvor.info.getKilometraza() + presliPlavi(cvor.levo) + presliPlavi(cvor.desno);

		return presliPlavi(cvor.levo) + presliPlavi(cvor.desno);
	}

	/*
	 * Pronalazi u stablu auto sa prosleđenom registracijom i kreira novo stablo u
	 * kojem pronađeni auto ima za @param long km povećanu pređenu kilometražu
	 */
	public Stablo presaoJos(String registracija, long km) {
		Cvor korenNovog = kopijaCvora(koren, registracija, km);
		return new Stablo(korenNovog);
	}

	private Cvor kopijaCvora(Cvor cvor, String reg, long km) {
		if (cvor == null)
			return null;

		/*
		 * Ako je registracija aut. u tekućem čvoru jednaka sa prosleđenom onda pravimo
		 * novi aut. sa povećanom kilometražom, ako nije onda auto. dobija vrednost iz
		 * tekućeg čvora
		 */
		Automobil auto = cvor.info.getRegistracija().equals(reg)
				? new Automobil(cvor.info.getModel(), cvor.info.getBoja(), reg, (cvor.info.getKilometraza() + km))
				: cvor.info;

		Cvor novi = new Cvor(auto, kopijaCvora(cvor.levo, reg, km), kopijaCvora(cvor.desno, reg, km));

		return novi;
	}

	/*
	 * Vraća sortiranu listu automobila koji imaju vecu kilometražu od svog
	 * roditelja i susednog čvora (bratskog)
	 */
	public List<Automobil> sortiraniPlavi() {
		List<Automobil> lista = new ArrayList<Automobil>();
		listaPlavih(lista, koren, null);

		Collections.sort(lista, new Comparator<Automobil>() {
			@Override
			public int compare(Automobil a1, Automobil a2) {
				return (int) (a1.getKilometraza() - a2.getKilometraza());
			}
		});

		return lista;
	}

	private void listaPlavih(List<Automobil> lista, Cvor tekuci, Cvor roditelj) {
		if (tekuci == null)
			return;

		// Prvo proverimo da li je auto plavi i da li ima roditelja
		if (tekuci.info.getBoja().equals("Plava") && roditelj != null) {
			// zatim proverimo da li tekući čvor ima veću kilometražu od roditelja
			if (tekuci.info.getKilometraza() > roditelj.info.getKilometraza()) {
				/*
				 * Gledamo da li naš tekući čvor ima susednog i da li susedni zadovoljava uslov.
				 * Ako je tekući čvor taj koji proveravamo, to jest ako upoređujemo sa samim
				 * sobom taj čvor onda neće biti dodat u listu jer će kilometraža biti ista
				 */
				if (roditelj.levo != null && roditelj.levo.info.getKilometraza() < tekuci.info.getKilometraza())
					lista.add(tekuci.info);

				if (roditelj.desno != null && roditelj.desno.info.getKilometraza() < tekuci.info.getKilometraza())
					lista.add(tekuci.info);
			}
		}

		listaPlavih(lista, tekuci.levo, tekuci);
		listaPlavih(lista, tekuci.desno, tekuci);
	}

	// Vraća true ako je trenutno stablo, binarno stablo pretraživanja
	public boolean jeBST() {
		return jeBSTpom(koren, Long.MIN_VALUE, Long.MAX_VALUE);
	}

	private boolean jeBSTpom(Cvor tekuci, long minKm, long maxKm) {
		if (tekuci == null)
			return true;

		if (tekuci.info.getKilometraza() < minKm || tekuci.info.getKilometraza() > maxKm)
			return false;

		return (jeBSTpom(tekuci.levo, minKm, tekuci.info.getKilometraza() - 1)
				&& jeBSTpom(tekuci.desno, tekuci.info.getKilometraza() + 1, maxKm));
	}

	// ------ po potrebi i pomocne metode, konstruktore, klase i sl
	// ------ ne dodavati polja u klasu!
}

// Glavna klasa
public class StabloProgram {

	// Glavni program
	public static void main(String[] args) {

		// Napravimo pomoćni objekat za uitavanje i ispisivanje
		TreeIO<Stablo> io = new TreeIO<>(Stablo.class);

		// Pročitamo stablo iz fajla
		Stablo stablo = io.read(Svetovid.in("res/domaci9/Veliko.txt"));

		// Ispisemo ucitano stablo
		io.print(Svetovid.out, stablo);
		System.out.println();

		// -------- ovde dodati pozive metoda ----------

		stablo.ispisiNS();
		System.out.println(stablo.presliPlavi() + " | " + stablo.presliPlavi2());
		System.out.println();
		// Stablo novo = stablo.presaoJos("BG 082-ZD", 12000);
		// io.print(Svetovid.out, novo);

		System.out.println(stablo.sortiraniPlavi());

		if (stablo.jeBST())
			System.out.println("Stablo jeste BST.");
		else
			System.out.println("Stablo nije BST.");
	}
}
