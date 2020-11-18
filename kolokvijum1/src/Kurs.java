import org.svetovid.io.SvetovidReader;
import org.svetovid.io.SvetovidWriter;

public class Kurs implements Comparable<Kurs> {
    private final String valuta;
    private final int kupovni;
    private final int srednji;
    private final int prodajni;
    private final int dan;

    public Kurs(String valuta, int kupovni, int srednji, int prodajni, int dan) {
        this.valuta = valuta;
        this.kupovni = kupovni;
        this.srednji = srednji;
        this.prodajni = prodajni;
        this.dan = dan;
    }

    public String getValuta() {
        return valuta;
    }

    public int getKupovni() {
        return kupovni;
    }

    public int getSrednji() {
        return srednji;
    }

    public int getProdajni() {
        return prodajni;
    }

    public static Kurs[] ucitaj(String file) {
        if (!Svetovid.testIn(file)) {
            Svetovid.err.println("Ucitavanje nije moguce!");
            return null;
        }

        SvetovidReader in = Svetovid.in(file);

        int n = in.readInt();
        Kurs[] kursevi = new Kurs[n];

        for (int i = 0; i < n; i++) {
            kursevi[i] = new Kurs(in.readToken(), in.readInt(), in.readInt(), in.readInt(), in.readInt());
        }

        in.close();

        return kursevi;
    }

    public static void sacuvaj(String file, Kurs[] kursevi) {
        if (!Svetovid.testOut(file)) {
            Svetovid.err.println("Cuvanje nije moguce!");
            return;
        }

        SvetovidWriter out = Svetovid.out(file);

        out.println(kursevi.length);

        for (Kurs kurs : kursevi) {
            out.println(kurs.valuta);
            out.println(kurs.kupovni);
            out.println(kurs.srednji);
            out.println(kurs.prodajni);
            out.println(kurs.dan);
        }

        out.close();
    }

    @Override
    public int compareTo(Kurs kurs) {
        return this.kupovni - kurs.kupovni;
    }

    @Override
    public String toString() {
        return "Kurs{" +
                "valuta='" + valuta + '\'' +
                ", kupovni=" + kupovni +
                ", srednji=" + srednji +
                ", prodajni=" + prodajni +
                ", dan=" + dan +
                '}';
    }
}
