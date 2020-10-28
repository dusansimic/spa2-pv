import org.svetovid.Svetovid;
import org.svetovid.io.SvetovidReader;
import org.svetovid.io.SvetovidWriter;

import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        // Ucitavanje fajla
        String fajl = Svetovid.in.readToken("Unesite ime fajla za ucitavanje:");
        Student[] niz = ucitajStudente(fajl);

        if (niz != null) {
            // Ispisivanje nesortiranog niza studenata
            Svetovid.out.println();
            Svetovid.out.println("Nesortirani niz:");
            Svetovid.out.println();
            stampajNiz(niz);

            // Sortiranje
            Svetovid.out.println();
            Svetovid.out.println("Odaberite nacin sortiranja:");
            Svetovid.out.println("1) Prirodni poredak (leksikografski)");
            Svetovid.out.println("2) Godina i prezime studenta");
            Svetovid.out.println("3) Duzina prezimena i imena studenta");
            int nacin = Svetovid.in.readInt(">");
            switch (nacin) {
            case 1:
                // Jeste ovo bespotrebna apstrakcija ali sam tako uradio da bi se sortiralo koristeci sortiraj() metodu
                // umesto Arrays.sort().
                sortiraj(niz, new PrirodniKomparator());
                break;
            case 2:
                sortiraj(niz, new KomparatorPoGodinaPrezime());
                break;
            case 3:
                sortiraj(niz, new KomparatorDuzinaImena());
                break;
            default:
                System.err.println("Neispravan nacin sortiranja!");
                return;
            }

            // Ispisivanje sortiranog niza studenata
            Svetovid.out.println();
            Svetovid.out.println("Sortirani niz:");
            Svetovid.out.println();
            stampajNiz(niz);

            // Pizanje sortiranog niza u izlazni fajl
            String izlazniFajl = Svetovid.in.readToken("Unesite ime fajla za ispis:");
            snimiStudente(niz, izlazniFajl);

            return;
        }

        System.err.println("Greska pri ucivanju niza.");
    }

    public static void sortiraj(Student[] arr, Comparator<Student> c) {
        Student t;
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (c.compare(arr[j], arr[i]) > 0) {
                    t = arr[i];
                    arr[i] = arr[j];
                    arr[j] = t;
                }
            }
        }
    }

    /**
     * Funkcija za ucitavanje studenata iz fajla. U slucaju nedostuponog fajla, vraca se null. U slucaju
     * uspesnog citanja iz fajla, vraca se niz studenata.
     * @param fajl putanja do fajla za citanje
     * @return Niz studenata iz fajla
     */
    public static Student[] ucitajStudente(String fajl) {
        if (!Svetovid.testIn(fajl)) {
            return null;
        }

        SvetovidReader in = Svetovid.in(fajl);

        int n = in.readInt();

        Student[] rez = new Student[n];

        for (int i = 0; i < n; i++) {
            String prezime = in.readToken();
            String ime = in.readToken();
            int godina = in.readInt();

            rez[i] = new Student(ime, prezime, godina);
        }

        in.close();

        return rez;
    }

    /**
     * Metoda za stampanje niza studenata na konzolu.
     * @param niz Niz studenata koji ce biti ispisan na konzolu
     */
    public static void stampajNiz(Student[] niz) {
        for (Student stud : niz) {
            System.out.println(stud);
        }
    }

    /**
     * Metoda za pisanje niza studenata u fajl. Studenti su ispisani po definisanim pravilima u tekstu
     * zadatka. Ukoliko nije moguce pisati u odabrani fajl, greska ce biti ispisana i izvrsavanje funkcije
     * se prekida.
     * @param niz Niz studenata za pisanje u fajl
     * @param fajl Putanja do fajla u koji se upisuju studenti
     */
    public static void snimiStudente(Student[] niz, String fajl) {
        if (!Svetovid.testOut(fajl)) {
            System.err.printf("Nije moguce pisanje u fajl \"%s\"\n", fajl);
            return;
        }

        SvetovidWriter out = Svetovid.out(fajl);

        out.println(niz.length);

        for (Student stud : niz) {
            out.println(stud.getPrezime());
            out.println(stud.getIme());
            out.println(stud.getGodina());
        }

        out.close();
    }
}
