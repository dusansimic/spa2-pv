import java.util.Arrays;
import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        Kurs[] kursevi = Kurs.ucitaj(Svetovid.in.readToken("Unesite naziv fajla za citanje:"));

        Svetovid.out.println("Moguce opcije za sortiranje:");
        Svetovid.out.println("\t1) Prirodno uredjenje");
        Svetovid.out.println("\t2) Valute i srednji kurs (srednji kursevi opadajuce)");
        Svetovid.out.println("\t3) Apsolutna razlika izmedju prodajnog i kupovnog kursa (opadajuce)");
        Svetovid.out.println("\t4) Apsolutna vrednost povecanja/smanjenja u procentima izmedju prodajnog i kupovnog (opadajuce)");
        Comparator<Kurs> c;
        switch (Svetovid.in.readInt("Odaberite opciju za sortiranje:")) {
            case 1:
                c = Kurs::compareTo;
                break;
            case 2:
                c = new KomparatorValutaSrednji();
                break;
            case 3:
                c = new ObrnutiKomparator(new KomparatorAbsDeltaProdajniKupovni());
                break;
            case 4:
                c = new ObrnutiKomparator(new KomparatorAbsDeltaProcenatProdajniKupovni());
                break;
            default:
                Svetovid.err.println("Opcija za sortiranje nije validna!");
                return;
        }

        sort(kursevi, c);

        Kurs.sacuvaj(Svetovid.in.readToken("Unesite naziv fajla za cuvanje:"), kursevi);
    }

    private static void sort(Kurs[] arr, Comparator<Kurs> c) {
        for (int i = arr.length - 1; i > 0; i--) {
            int max = 0;
            for (int j = 1; j <= i; j++) {
                if (c.compare(arr[j], arr[max]) > 0) {
                    max = j;
                }
            }

            Kurs t = arr[max];
            arr[max] = arr[i];
            arr[i] = t;
        }
    }
}
