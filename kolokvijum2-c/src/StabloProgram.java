import org.svetovid.Svetovid;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

class Stablo {
    private static class Cvor {
        Patuljak info;
        Cvor levo;
        Cvor desno;

        // Dodat konstruktor
        public Cvor(Patuljak info, Cvor levo, Cvor desno) {
            this.info = info;
            this.levo = levo;
            this.desno = desno;
        }
    }

    private Cvor koren;

    // Dodat konstruktor za kreiranje novog stabla na osnovu prosledjenog cvora
    protected Stablo(Cvor cvor) {
        this.koren = cvor;
    }

    // ------ dodati opisane metode ------------

    // Metod 1
    public void ispisiNaSlovo(String slovo) {
        ispisiNaSlovo(koren, slovo);
    }

    private void ispisiNaSlovo(Cvor cvor, String slovo) {
        if (cvor == null)
            return;

        if (cvor.info.getIme().startsWith(slovo)) {
            System.out.println(cvor.info);
        }

        ispisiNaSlovo(cvor.levo, slovo);
        ispisiNaSlovo(cvor.desno, slovo);
    }

    // Metod 2
    public double sumaZlataNaSlovo(String slovo) {
        return sumaZlataNaSlovo(koren, slovo);
    }

    private double sumaZlataNaSlovo(Cvor cvor, String slovo) {
        if (cvor == null)
            return 0.0;

        if (cvor.info.getIme().startsWith(slovo))
            return cvor.info.getIskopaoZlata() + sumaZlataNaSlovo(cvor.levo, slovo) + sumaZlataNaSlovo(cvor.desno, slovo);

        return sumaZlataNaSlovo(cvor.levo, slovo) + sumaZlataNaSlovo(cvor.desno, slovo);
    }

    // Metod 3
    public Stablo iskopaoJos(String ime, double kolicina) {
        Cvor pocetni = kopijaCvora(koren, ime, kolicina);
        return new Stablo(pocetni);
    }

    private Cvor kopijaCvora(Cvor cvor, String ime, double kolicina) {
        if (cvor == null)
            return null;

        Patuljak patuljak = cvor.info.getIme().equals(ime)
                ? new Patuljak(cvor.info.getIme(), cvor.info.getUbioGoblina(), cvor.info.getGodinaRodjenja(),
                cvor.info.getIskopaoZlata() + kolicina)
                : new Patuljak(cvor.info.getIme(), cvor.info.getUbioGoblina(), cvor.info.getGodinaRodjenja(), cvor.info.getIskopaoZlata());

        Cvor rez = new Cvor(
                patuljak,
                kopijaCvora(cvor.levo, ime, kolicina),
                kopijaCvora(cvor.desno, ime, kolicina)
        );

        return rez;
    }

    // Metod 4
    public boolean ponavljaSeGodinaRodjenja() {
        HashMap<Integer, String> mapa = new HashMap<>();
        return ponavljaSeGodinaRodjenja(koren, mapa);
    }

    private boolean ponavljaSeGodinaRodjenja(Cvor cvor, HashMap<Integer, String> mapa) {
        if (cvor == null)
            return false;

        boolean jeste;
        if (cvor.info != null) {
            jeste = dodavanjePatuljka(mapa, cvor.info.getGodinaRodjenja(), cvor.info);
            if (jeste)
                return true;
        }

        return ponavljaSeGodinaRodjenja(cvor.levo, mapa) || ponavljaSeGodinaRodjenja(cvor.desno, mapa);
    }

    private boolean dodavanjePatuljka(HashMap<Integer, String> mapa, Integer godina, Patuljak p) {
        // Ako patuljak sa prosledjenom godinom vec postoji u mapi vracamo true sto znaci da imamo duplikat
        if (mapa.containsKey(godina))
            return true;
        else {
            mapa.put(godina, p.getIme());
            return false;
        }

    }

    // Metod 5
    public boolean jeSumarnoStablo() {
        return sumarnoStablo(koren);
    }

    private boolean sumarnoStablo(Cvor cvor) {
        // ako je cvor null ili nema decu onda racunamo da je stablo sumarno
        if (cvor == null || (cvor.levo == null && cvor.desno == null))
            return true;

        // u slucaju da desni cvor nepostoji provervamo da li je vrednost ubijenih trolova levog jednaka sa roditeljskim
        // i onda nastavljamo na levu stranu, identicno za desni cvor, na kraju ako postoje oba cvora proveravamo sumu
        if (cvor.desno == null) {
            return (cvor.levo.info.getUbioGoblina() == cvor.info.getUbioGoblina()) && sumarnoStablo(cvor.levo);
        } else if (cvor.levo == null) {
            return (cvor.desno.info.getUbioGoblina() == cvor.info.getUbioGoblina()) && sumarnoStablo(cvor.desno);
        } else {
            return (cvor.info.getUbioGoblina() == cvor.levo.info.getUbioGoblina() + cvor.desno.info.getUbioGoblina()
                    && sumarnoStablo(cvor.levo) && sumarnoStablo(cvor.desno));
        }
    }

    // ------ po potrebi i pomocne metode, konstruktore, klase i sl
    // ------ ne dodavati polja u klasu!
}

// Glavna klasa
public class StabloProgram {

    // Glavni program
    public static void main(String[] args) {

        // Napravimo pomocni objekat za ucitavanje i ispisivanje
        TreeIO<Stablo> io = new TreeIO<>(Stablo.class);

        // Procitamo stablo iz fajla
        // TODO Promeniti putanju!
        Stablo stablo = io.read(Svetovid.in("res/kol/sumarno-test.txt"));
        // alternativno mozemo iz nekog drugog fajla
        // otkomentarisati neki od redova dole, a skloniti ovaj iznad
        //Stablo stablo = io.read(Svetovid.in("Veliko.txt"));
        //Stablo stablo = io.read(Svetovid.in("Prazno.txt"));

        // Ispisemo ucitano stablo
        io.print(Svetovid.out, stablo);

        // Decimal format radi lepseg ispisa double vrednosti u programu
        DecimalFormat df = new DecimalFormat("#.##");

        // -------- ovde dodati pozive metoda ----------
        // Metod 1
        System.out.println("\nPatuljci na slovo S: ");
        stablo.ispisiNaSlovo("S");
        System.out.println();

        // Metod 2
        System.out.println("Suma zlata svih patuljaka na slovo O: " + df.format(stablo.sumaZlataNaSlovo("O")));

        // Metod 3
        //System.out.println("\nNovo stablo u kojem je Dalofraik iskopao jos 300.00 u zlatu: \n");
        //Stablo novo = stablo.iskopaoJos("Dalofraik",300.00);
        //io.print(Svetovid.out, novo);

        // Metod 4
        if (stablo.ponavljaSeGodinaRodjenja())
            System.out.println("Postoji ponavljanje godine rodjenja!");
        else
            System.out.println("Ne postoji ponavljanje godine rodjenja!");

        System.out.println("Stablo je sumarno:" + stablo.jeSumarnoStablo());
    }
}
