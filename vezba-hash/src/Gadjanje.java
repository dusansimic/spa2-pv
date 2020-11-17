import org.svetovid.io.SvetovidReader;

import java.util.Arrays;

/**
 * Gadjanje mete
 * 
 * Prosiruje InfoTip samo zbog jednostavnosti u TestHash, nema razloga inace.
 */
public class Gadjanje extends InfoTip {
	private int[] rezultati;

	public Gadjanje() {
		// prazan konstruktor potreban za test program
	}

	public Gadjanje(int[] rezultati) {
		this.rezultati = rezultati;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Gadjanje gadjanje = (Gadjanje) o;
		return Arrays.equals(rezultati, gadjanje.rezultati);
	}



//	@Override
//	public int hashCode() {
//		int res = 0;
//		if (rezultati == null) {
//			return res;
//		}
//		int n = rezultati.length;
//		for (int i = 0; i < n; i++) {
//            res += res * 31^(n-i-1) + rezultati[i]^17;
//		}
//		return res;
//	}


	@Override
	public int hashCode() {
		int res = 0;
		if (rezultati == null) {
			return res;
		}
		int n = rezultati.length;
		for (int i = 0; i < n; i++) {
			res = res * 31;
			res = res | rezultati[i];
		}
		return super.hashCode();
	}

	@Override
	public InfoTip ucitaj(SvetovidReader r) {
		int br = r.readInt();
		int[] rezultati = new int[br];
		for (int i = 0; i < br; i++) {
			rezultati[i] = r.readInt();
		}
		Gadjanje rez = new Gadjanje(rezultati);
		return rez;
	}

	public String toString() {
		String str = "Gadjanje (" + rezultati.length + "):";
		for (int i : rezultati) {
			str += " " + i;
		}
		return str;
	}

	// pomocni metod za lakse testiranje
	public static void main(String[] args) {
		new TestHash(new Gadjanje(), "res/", "t").run();
	}
	
}
