import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

class StabloOsoba {

    // Tip koji opisuje jedan cvor u stablu
    protected static class Cvor {

        // Sadrzaj cvora
        public Osoba osoba;

        // Levo i desno podstablo
        public Cvor levo;
        public Cvor desno;

        // Jedini konstruktor
        public Cvor(Osoba osoba, Cvor levo, Cvor desno) {
            this.osoba = osoba;
            this.levo = levo;
            this.desno = desno;
        }

        @Override
        public String toString() {
            return osoba.toString() + " {" + (levo == null ? "null" : levo.toString()) + " - " + (desno == null ? "null" : desno.toString()) + "}";
        }
    }

    // Stablo ima referencu na korenski cvor
    protected final Cvor koren;

    // Kreiramo prazno stablo
    public StabloOsoba() {
        koren = null;
    }

    // Kreiramo stablo sa jednom osobom u korenu
    // i praznim levim i desnim podstablom
    public StabloOsoba(Osoba osoba) {
        koren = new Cvor(osoba, null, null);
    }

    // Specijalan konstruktor koji koriste neki metodi ove klase
    protected StabloOsoba(Cvor koren) {
        this.koren = koren;
    }

    public boolean jePrazno(){
        if (koren == null)
            return true;
        return false;
    }

    public boolean postojiElement(Cvor cvor, Osoba osoba){
        if (cvor == null)
            return false;

        if (Objects.equals(cvor.osoba, osoba))
            return true;

        boolean nadjenLevo = postojiElement(cvor.levo, osoba);
        if (nadjenLevo)
            return nadjenLevo;

        boolean nadjenDesno = postojiElement(cvor.desno, osoba);
        if (nadjenDesno)
            return nadjenDesno;

        return false;

    }

    public void stampajPreorder(Cvor cvor){
        if (cvor == null)
            return;

        System.out.println(cvor.osoba);
        stampajPreorder(cvor.levo);
        stampajPreorder(cvor.desno);
    }

    public void stampajInorder(Cvor cvor){
        if (cvor == null)
            return;

        stampajPreorder(cvor.levo);
        System.out.println(cvor.osoba);
        stampajPreorder(cvor.desno);
    }

    public void stampajPostorder(Cvor cvor){
        if (cvor == null)
            return;

        stampajPreorder(cvor.levo);
        stampajPreorder(cvor.desno);
        System.out.println(cvor.osoba);
    }

    public void stampajListove(Cvor cvor){
        if (cvor == null)
            return;

        if (cvor.levo == null && cvor.desno == null)
            System.out.println(cvor.osoba);
        else{
            stampajListove(cvor.levo);
            stampajListove(cvor.desno);
        }

    }

    public StabloOsoba pronadjiOsobu(Osoba osoba){
        Cvor cvor = pronadjiOsobu(koren, osoba);
        if (cvor == null)
            return null;
        return new StabloOsoba(cvor);

    }

    private Cvor pronadjiOsobu(Cvor cvor, Osoba osoba){
        if (cvor == null)
            return null;

        if (Objects.equals(cvor.osoba, osoba))
            return cvor;

        Cvor nadjenLevo = pronadjiOsobu(cvor.levo, osoba);
        if (nadjenLevo != null)
            return nadjenLevo;

        Cvor nadjenDesno = pronadjiOsobu(cvor.desno, osoba);
        if (nadjenDesno != null)
            return nadjenDesno;

        return null;

    }

    public List<Osoba> stampajSveIspod(Osoba sef){
        List<Osoba> osobe = new ArrayList<Osoba>();
        Cvor cvor = pronadjiOsobu(koren, sef);
        if (cvor != null)
            sveOsobe(cvor, osobe);
        return osobe;
    }

    private void sveOsobe(Cvor cvor, List<Osoba> lista){
        if (cvor == null)
            return;

        sveOsobe(cvor.levo, lista);
        lista.add(cvor.osoba);
        sveOsobe(cvor.desno, lista);
    }

    public boolean ubaci(Osoba roditelj, Osoba potomak, boolean levo){
        Cvor cvor = pronadjiOsobu(koren, roditelj);
        if (levo && cvor.levo == null) {
            cvor.levo = new Cvor(potomak, null, null);
            return true;
        }

        if (!levo && cvor.desno == null) {
            cvor.desno = new Cvor(potomak, null, null);
            return true;
        }

        return false;
    }

    public Osoba roditeljOd(Osoba potomak){
        Cvor cvor = roditeljOd(koren, null, potomak);
        if (cvor == null)
            return null;
        return cvor.osoba;

    }

    private Cvor roditeljOd(Cvor tekuci, Cvor roditelj, Osoba potomak){
        if (tekuci == null)
            return null;
        if (Objects.equals(tekuci.osoba, potomak))
            return roditelj;

        Cvor roditeljLevo = roditeljOd(tekuci.levo, tekuci, potomak);
        if (roditeljLevo != null)
            return roditeljLevo;

        Cvor roditeljDesno = roditeljOd(tekuci.desno, tekuci, potomak);
        if (roditeljDesno != null)
            return roditeljDesno;

        return null;
    }

    public Osoba optimalnaOsoba(Comparator<Osoba> komparator, Cvor cvor){
        if (cvor == null)
            return null;

        Osoba optimalnaLevo, optimalnaDesno;
        Osoba optimalna = cvor.osoba;

        if (cvor.levo != null) {
            optimalnaLevo = optimalnaOsoba(komparator, cvor.levo);
            if (komparator.compare(optimalna, optimalnaLevo) <0)
                optimalna = optimalnaLevo;
        }

        if (cvor.desno != null) {
            optimalnaDesno = optimalnaOsoba(komparator, cvor.desno);
            if (komparator.compare(optimalna, optimalnaDesno) <0)
                optimalna = optimalnaDesno;
        }

        return optimalna;

    }

    public StabloOsoba kopija() {
        return new StabloOsoba(kopijaR(koren, false));
    }

    public StabloOsoba obrnuto() {
        return new StabloOsoba(kopijaR(koren, true));
    }

    private Cvor kopijaR(Cvor cvor, boolean obrni) {
        if (cvor == null) return null;
        Cvor levo = kopijaR(cvor.levo, obrni);
        Cvor desno = kopijaR(cvor.desno, obrni);
        return obrni ? new Cvor(cvor.osoba.kopija(), desno, levo) : new Cvor(cvor.osoba.kopija(), levo, desno);
    }

    @Override
    public String toString() {
        return koren.toString();
    }
}