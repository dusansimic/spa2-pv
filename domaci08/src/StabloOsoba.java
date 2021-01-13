import java.util.*;

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

    public double prosecnaPlata() {
        Prosek prosek = new Prosek();
        prosecnaPlata(koren, prosek);
        return prosek.getSumaPlata() / prosek.getBrojCvorova();

    }

    private void prosecnaPlata(Cvor cvor, Prosek prosek) {
        if (cvor == null)
            return;

        prosek.setBrojCvorova(prosek.getBrojCvorova() + 1);
        prosek.setSumaPlata(prosek.getSumaPlata() + cvor.osoba.getPlata());
        prosecnaPlata(cvor.levo, prosek);
        prosecnaPlata(cvor.desno, prosek);
    }

    public double prosecnaPlata2() {
        PosetiProsek posetilac = new PosetiProsek();
        posetiInOrder(koren, posetilac);
        return posetilac.prosek();
    }

    private void posetiInOrder(Cvor cvor, IPoseti posetilac) {
        if (cvor == null)
            return;

        posetiInOrder(cvor.levo, posetilac);
        posetilac.poseti(cvor);
        posetiInOrder(cvor.desno, posetilac);
    }

    public List<Osoba> sviNadredjeni(Osoba o){
        List<Osoba> lista = new ArrayList<>();
        sviNadredjeni(koren, o, lista);
        Collections.reverse(lista);
        return lista;
    }

    private boolean sviNadredjeni (Cvor cvor, Osoba o, List<Osoba> lista) {

        if (cvor == null)
            return false;

        if (Objects.equals(cvor.osoba, o))
            return true;

        boolean levo = sviNadredjeni(cvor.levo, o, lista);
        boolean desno = false;
        if (!levo)
            desno = sviNadredjeni(cvor.desno, o, lista);

        if (levo || desno)
            lista.add(cvor.osoba);

        return levo || desno;

    }

    public List<Osoba> sviSaIstomPlatom(){
        HashMap<Integer, ArrayList<Osoba>> plate = new HashMap<>();
        sviSaIstomPlatom(koren, plate);
        List<Osoba> krajnjiRezultat = new ArrayList<>();
        for (ArrayList<Osoba> osobe: plate.values()){
            if (osobe.size() > 1)
                krajnjiRezultat.addAll(osobe);
        }
        return krajnjiRezultat;

    }

    private void sviSaIstomPlatom(Cvor cvor, HashMap<Integer, ArrayList<Osoba>> plate ){
        if (cvor == null)
            return;

        if (cvor.osoba != null){
            dodajUMapu(plate, cvor.osoba.getPlata(), cvor.osoba);
        }

        sviSaIstomPlatom(cvor.levo, plate);
        sviSaIstomPlatom(cvor.desno, plate);

    }

    private void dodajUMapu (HashMap<Integer, ArrayList<Osoba>> plate, Integer plata, Osoba o){
        if (!plate.containsKey(plata))
            plate.put(plata, new ArrayList<>());
        plate.get(plata).add(o);
    }


    public void ispisiOsobeSaVecomPlatomOdSefa(){
        ispisiOsobeSaVecomPlatomOdSefa(koren, koren);
    }

    private void ispisiOsobeSaVecomPlatomOdSefa(Cvor dete, Cvor roditelj){
        if (dete == null)
            return;

        if (dete.osoba.getPlata()>roditelj.osoba.getPlata())
            System.out.println(dete.osoba);

        ispisiOsobeSaVecomPlatomOdSefa(dete.levo, dete);
        ispisiOsobeSaVecomPlatomOdSefa(dete.desno, dete);
    }

    public List<Osoba> vecaPlataOdDirektora() {
        List<Osoba> lista = new ArrayList<>();
        if (koren == null)
            return lista;

        vecaPlataOdDirektora(koren, koren.osoba.getPlata(), lista);
        return lista;
    }

    private void vecaPlataOdDirektora(Cvor cvor, double plata, List<Osoba> osobe){

        if (cvor == null)
            return;

        if (cvor.osoba.getPlata() > plata)
            osobe.add(cvor.osoba);

        vecaPlataOdDirektora(cvor.levo, plata, osobe);
        vecaPlataOdDirektora(cvor.desno, plata, osobe);

    }

    public List<Osoba> plataIznadProseka() {
        double prosek = prosecnaPlata();
        List<Osoba> lista = new ArrayList<>();
        vecaPlataOdDirektora(koren, prosek, lista);
        return lista;
    }

    public Osoba drugaPoReduSaNajvecomPlatom() {
        Osoba[] najveca = new Osoba[]{null, null};
        drugaPoReduR(koren, najveca);
        return najveca[1];
    }

    private void drugaPoReduR(Cvor cvor, Osoba[] najveca) {
        if (cvor == null) {
            return;
        }

        if (najveca[0] == null) {
            najveca[0] = cvor.osoba;
        } else if (cvor.osoba.getPlata() > najveca[0].getPlata()) {
            najveca[1] = najveca[0];
            najveca[0] = cvor.osoba;
        } else if (najveca[1] == null) {
            najveca[1] = cvor.osoba;
        } else if (cvor.osoba.getPlata() > najveca[1].getPlata()) {
            najveca[1] = cvor.osoba;
        }

        drugaPoReduR(cvor.levo, najveca);
        drugaPoReduR(cvor.desno, najveca);
    }
}