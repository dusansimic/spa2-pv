import java.util.Objects;

public class Fudbaler {
    private String ime;
    private String pozicija;
    private int godinaRodjenja;
    private boolean prvaPostava;

    public Fudbaler(String ime, String pozicija, int godinaRodjenja, boolean prvaPostava) {
        this.ime = ime;
        this.pozicija = pozicija;
        this.godinaRodjenja = godinaRodjenja;
        this.prvaPostava = prvaPostava;
    }

    public String getIme() {
        return ime;
    }

    public String getPozicija() {
        return pozicija;
    }

    public int getGodinaRodjenja() {
        return godinaRodjenja;
    }

    public boolean isPrvaPostava() {
        return prvaPostava;
    }

    @Override
    public String toString() {
        return "Fudbaler{" +
                "ime='" + ime + '\'' +
                ", pozicija='" + pozicija + '\'' +
                ", godinaRodjenja=" + godinaRodjenja +
                ", prvaPostava=" + prvaPostava +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fudbaler fudbaler = (Fudbaler) o;
        return godinaRodjenja == fudbaler.godinaRodjenja &&
                prvaPostava == fudbaler.prvaPostava &&
                Objects.equals(ime, fudbaler.ime) &&
                Objects.equals(pozicija, fudbaler.pozicija);
    }

    @Override
    public int hashCode() {
        int rez = 11;

        if (ime != null) {
            rez += ime.hashCode() * 17;
        }

        if (pozicija != null) {
            rez += pozicija.hashCode() * 7;
        }

        rez += godinaRodjenja;

        rez += prvaPostava ? 0 : 1;

        return rez;
    }
}
