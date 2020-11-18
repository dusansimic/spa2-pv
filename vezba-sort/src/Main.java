import org.svetovid.Svetovid;
import org.svetovid.io.SvetovidReader;
import org.svetovid.io.SvetovidWriter;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

public class Main {

	public static void main(String[] args) {
		Predmet[] predmets = uctiajPodatke(Svetovid.in.readToken("Fajl za ucitavanje:"));

		Svetovid.out.println("Ponudjeni komparatori:");
		Svetovid.out.println("\t* Po imenu predmeta (ime)");
		Svetovid.out.println("\t* Po godini studija, danu u nedelji i satima (gds)");
		Svetovid.out.println("\t* Po salama, danu i satu opacajude (sdso)");
		Svetovid.out.println("\t* Po duzini predavanja (duz)");
		Svetovid.out.println("\t* Prirodnim poretkom (prirodni)");
		String komparator = Svetovid.in.readToken("Odaberite komparator:");
		Comparator<Predmet> c;
		switch (komparator) {
			case "ime":
				c = new KomparatorIme();
				break;
			case "gds":
				c = new KomparatorGodinaDanSat();
				break;
			case "sdso":
				c = new KomparatorSalaDanSatOpadajuce();
				break;
			case "duz":
				c = new KomparatorDuzina();
				break;
			default:
				c = new PrirodniKomparator();
		}

		Svetovid.out.println(Arrays.toString(predmets));

		Svetovid.out.println("Ponudjeni algoritmi za sortiranje:");
		Svetovid.out.println("\t* Bubble sort (bubble)");
		Svetovid.out.println("\t* Insertion sort (insertion)");
		Svetovid.out.println("\t* Selection sort (selection)");
		Svetovid.out.println("\t* Arrays.sort (arr)");
		String sortiranje = Svetovid.in.readToken("Odaberite algoritam za sortiranje:");
		switch (sortiranje) {
			case "bubble":
				bubbleSort(predmets, c);
				break;
			case "insertion":
				insertionSort(predmets, c);
				break;
			case "selection":
			    selectionSort(predmets, c);
				break;
			default:
				Arrays.sort(predmets, c);
		}
		Svetovid.out.println(Arrays.toString(predmets));

		ispisiPodatke(Svetovid.in.readToken("Fajl za ispisivanje:"), predmets);
	}

	private static Predmet[] uctiajPodatke(String file) {
		if (!Svetovid.testIn(file)) {
			Svetovid.err.println("Nije moguce ucitati fajl.");
			return null;
		}

		SvetovidReader in = Svetovid.in(file);

		int n = in.readInt();

		Predmet[] predmets = new Predmet[n];

		for (int i = 0; i < n; i++) {
			Predmet p = new Predmet(in.readInt(), in.readInt(), in.readInt(), in.readLine(), in.readInt(), in.readLine());
			predmets[i] = p;
		}

		in.close();

		return predmets;
	}

	private static void ispisiPodatke(String file, Predmet[] arr) {
		if (!Svetovid.testOut(file)) {
			Svetovid.err.println("Nije moguce ispisati podatke u prosledjeni fajl!");
			return;
		}

		SvetovidWriter out = Svetovid.out(file);

		out.println(arr.length);

		for (Predmet p : arr) {
			out.println();
			out.println(p.getDan());
			out.println(p.getPocetak());
			out.println(p.getKraj());
			out.println(p.getSala());
			out.println(p.getGodina());
			out.println(p.getIme());
		}

		out.close();
	}

	private static void bubbleSort(Predmet[] arr, Comparator<Predmet> c) {
		Predmet tmp;
		for (int i = arr.length - 1; i > 0; i--) {
			for (int j = 0; j < i; j++) {
				if (c.compare(arr[j], arr[j+1]) > 0) {
					tmp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = tmp;
				}
			}
		}
	}

	private static void insertionSort(Predmet[] arr, Comparator<Predmet> c) {
		Predmet cur;
		for (int i = 1; i < arr.length; i++) {
			cur = arr[i];
			for (int j = i - 1; j >= 0; j--) {
				if (c.compare(cur, arr[j]) < 0) {
					arr[j+1] = arr[j];
					arr[j] = cur;
				} else {
					arr[j+1] = cur;
					break;
				}
			}
		}
	}

	private static void selectionSort(Predmet[] arr, Comparator<Predmet> c) {
		for (int i = arr.length - 1; i >= 1; i--) {
			int max = 0;
			for (int j = 1; j <= i; j++) {
				if (c.compare(arr[j], arr[max]) > 0) {
					max = j;
				}
			}

			if (max != i) {
				Predmet tmp = arr[max];
				arr[max] = arr[i];
				arr[i] = tmp;
			}
		}
	}

}
