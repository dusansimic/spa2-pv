import java.util.ArrayList;
import java.util.List;

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
	}

    private Cvor koren;

	public Stablo(Cvor koren) {
		this.koren = koren;
	}

	public void ispisiNS() {
		ispisiNSR(koren);
	}

	private void ispisiNSR(Cvor n) {
    	if (n == null) {
    		return;
		}

    	if (n.info.getRegistracija().startsWith("NS")) {
    		System.out.println(n.info);
		}

    	ispisiNSR(n.levo);
		ispisiNSR(n.desno);
	}

	public long presliPlavi() {
		return presliPlaviR(koren);
	}

	private long presliPlaviR(Cvor n) {
    	if (n == null) {
			return 0;
		}

    	return (n.info.getBoja().equals("Plava") ? n.info.getKilometraza() : 0) + presliPlaviR(n.levo) + presliPlaviR(n.desno);
	}

	public Stablo presaoJos(String registracija, long km) {
		return new Stablo(presaoJosR(koren, registracija, km));
	}

	private Cvor presaoJosR(Cvor c, String registracija, long km) {
		if (c == null) return null;
		Automobil a = c.info.getRegistracija().equals(registracija) ?
			new Automobil(
					c.info.getModel(),
					c.info.getBoja(),
					c.info.getRegistracija(),
					c.info.getKilometraza() + km
			) :
			new Automobil(
					c.info.getModel(),
					c.info.getBoja(),
					c.info.getRegistracija(),
					c.info.getKilometraza()
			);

		return new Cvor(
				a,
				presaoJosR(c.levo, registracija, km),
				presaoJosR(c.desno, registracija, km)
		);
	}

	public List<Automobil> sortiraniPlavi() {
		ArrayList<Automobil> l = new ArrayList<Automobil>();
		sortiraniPlaviR(koren, l);
		l.sort((a1, a2) -> (int) Math.signum(a1.getKilometraza() - a2.getKilometraza()));
		return l;
	}

	private void sortiraniPlaviR(Cvor c, List<Automobil> l) {
		if (c == null) {
			return;
		}

    	if (c.info.getBoja().equals("Plava")) {
    		l.add(c.info);
		}

    	sortiraniPlaviR(c.levo, l);
		sortiraniPlaviR(c.desno, l);
	}

	public boolean jeBST() {
	    return jeBSTR(koren).getBST();
	}

	private BSTPair jeBSTR(Cvor c) {
	    if (c == null) {
	    	return null;
		}

		BSTPair left = jeBSTR(c.levo);
	    if (left != null) {
			if (!left.getBST() && c.info.getKilometraza() < left.getKM()) {
				return new BSTPair(false, c.info.getKilometraza());
			}
		}

		BSTPair right = jeBSTR(c.desno);
	    if (right != null) {
			if (!right.getBST() | c.info.getKilometraza() > right.getKM()) {
				return new BSTPair(false, c.info.getKilometraza());
			}
		}

		return new BSTPair(true, c.info.getKilometraza());
	}

    // ------ dodati opisane metode ------------
    // ------ po potrebi i pomocne metode, konstruktore, klase i sl
    // ------ ne dodavati polja u klasu!
}

abstract class Pair<TFirst, TSecond> {
	protected TFirst first;
	protected TSecond second;

	public Pair(TFirst first, TSecond second) {
		this.first = first;
		this.second = second;
	}
}

class BSTPair extends Pair<Boolean, Long> {
	public BSTPair(Boolean aBoolean, Long aLong) {
		super(aBoolean, aLong);
	}

	public Boolean getBST() {return first;}

	public Long getKM() {return second;}
}


// Glavna klasa
public class StabloProgram {

	// Glavni program
	public static void main(String[] args) {

		// Napravimo pomocni objekat za ucitavanje i ispisivanje
		TreeIO<Stablo> io = new TreeIO<>(Stablo.class);

		// Procitamo stablo iz fajla
		Stablo stablo = io.read(Svetovid.in("res/Malo.txt"));
		// alternativno mozemo iz nekog drugog fajla
		// otkomentarisati neki od redova dole, a skloniti ovaj iznad
		//Stablo stablo = io.read(Svetovid.in("Veliko.txt"));
		//Stablo stablo = io.read(Svetovid.in("Prazno.txt"));

		// Ispisemo ucitano stablo
		io.print(Svetovid.out, stablo);

		// -------- ovde dodati pozive metoda ----------

		stablo.ispisiNS();
		Svetovid.out.println(stablo.presliPlavi());
		String registracija = Svetovid.in.readLine("Unesite registraciju:");
		long km = Svetovid.in.readLong("Unesite dodatnu kilometrazu:");
		io.print(Svetovid.out, stablo.presaoJos(registracija, km));
		Svetovid.out.println(stablo.sortiraniPlavi());
		Svetovid.out.println(stablo.jeBST());
	}
}
