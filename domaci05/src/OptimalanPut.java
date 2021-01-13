import org.svetovid.Svetovid;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OptimalanPut {
    public static final int IZLAZ = -99;
    public static final int ZID = -11;

    private static int visina, sirina;

    private static int [][] lavirint;
    private static boolean [][] posecenost;

    private Put optimalniPut;

    public OptimalanPut (String fajl) {
        if (!Svetovid.testIn(fajl))
            throw new RuntimeException("Nemoguce je ucitati fajl.");

        sirina = Svetovid.in(fajl).readInt();
        visina = Svetovid.in(fajl).readInt();
        lavirint = new int[sirina][visina];
        posecenost = new boolean [sirina][visina];

        for (int j = 0; j< visina; j++){
            for (int i = 0; i< sirina; i++) {
                lavirint[i][j] = Svetovid.in(fajl).readInt();
            }
        }
        Svetovid.closeIn(fajl);
        stampaj();
    }


    public static void stampaj (){
        System.out.println(visina + " " + sirina);
        System.out.println("Lavirint: ");
        for (int j = 0; j< visina; j++){
            for (int i = 0; i< sirina; i++) {
                System.out.printf("%1$5d",lavirint[i][j]);
            }
            System.out.println();
        }
    }

    public Put najkraciPut (int x, int y){
        Put r = new Put ();
        optimalniPut (x, y, r, new KomparatorPoDuzini());
        return optimalniPut;
    }

    public Put najvrednijiPut(int x, int y) {
        Put r = new Put ();
        optimalniPut (x, y, r, new KomparatorPoVrednosti());
        return optimalniPut;
    }

    private Put najvrednijeBlagoPut(int x, int y) {
        Put r = new Put ();
        optimalniPut (x, y, r, new KomparatorPoNajvrednijemBlagu());
        return optimalniPut;
    }


    private Put najviseBlagaPut(int x, int y) {
        Put r = new Put ();
        optimalniPut (x, y, r, new KomparatorPoBrojuPoljaSaBlagom());
        return optimalniPut;
    }

    private Put najviseUzastopnihBlagaPut(int x, int y) {
        Put r = new Put ();
        optimalniPut (x, y, r, new KomparatorPoUzastopnimPoljimaSaBlagom());
        return optimalniPut;
    }



    private void optimalniPut(int x, int y, Put r, Comparator<Put> comparator){

        if (x <0 || x>=sirina || y < 0 || y >=visina)
            return;

        if (lavirint[x][y] == ZID)
            return;

        if (posecenost[x][y])
            return;

        if (lavirint[x][y] ==IZLAZ){
            r.dodaj(x, y, 0);
//            Svetovid.out.println(r);
            if (optimalniPut == null || comparator.compare(r, optimalniPut) <0 ){
                optimalniPut = r.kopija();
            }
            r.izbaciKraj();
            return;
        }

        posecenost[x][y] = true;
        r.dodaj(x, y, lavirint[x][y]);

        optimalniPut(x+1,y,r, comparator);
        optimalniPut(x-1, y, r, comparator);
        optimalniPut(x, y+1, r, comparator);
        optimalniPut(x, y-1, r, comparator);

        posecenost[x][y] = false;
        r.izbaciKraj();
    }

    private List<Put> sviPutevi(int x, int y) {
        List<Put> putevi = new ArrayList<Put>();
        sviPutevi(x, y, new Put(), putevi);
        return putevi;
    }

    private int prebrojPuteve(int x, int y) {
        List<Put> ps = sviPutevi(x, y);
        return ps.size();
    }

    private int prebrojPuteveViseOd3UzastopnaBlaga(int x, int y) {
        List<Put> ps = sviPutevi(x, y);
        return (int) ps.stream().filter(p -> p.getNajviseUzastopnihPoljaSaBlagom() > 3).count();
    }

    private void sviPutevi(int x, int y, Put r, List<Put> rs){

        if (x <0 || x>=sirina || y < 0 || y >=visina)
            return;

        if (lavirint[x][y] == ZID)
            return;

        if (posecenost[x][y])
            return;

        if (lavirint[x][y] ==IZLAZ){
            r.dodaj(x, y, 0);
            rs.add(r.kopija());
            r.izbaciKraj();
            return;
        }

        posecenost[x][y] = true;
        r.dodaj(x, y, lavirint[x][y]);

        sviPutevi(x+1, y, r, rs);
        sviPutevi(x-1, y, r, rs);
        sviPutevi(x, y+1, r, rs);
        sviPutevi(x, y-1, r, rs);

        posecenost[x][y] = false;
        r.izbaciKraj();
    }


    public static void main (String [] args) {
        System.out.println("Unesite ime fajla: ");
        String fajl = Svetovid.in.readLine();
        if (!Svetovid.testIn(fajl)) {
            System.out.println("Nemoguce je ucitati fajl.");
            return;
        }

        if (fajl.equalsIgnoreCase(""))
            fajl = "res/blago1.txt";

        OptimalanPut optP = new OptimalanPut(fajl);
        Put put;

        System.out.println("Najkraci put je:");
        put = optP.najkraciPut(0,0);
        if (put != null)
            System.out.println(put);
        else
            System.out.println("Nema resenja.");

        System.out.println("Najvredniji put je:");
        put = optP.najvrednijiPut(0,0);
        if (put != null)
            System.out.println(put);
        else
            System.out.println("Nema resenja.");

        System.out.println("Put sa najvrednijim blagom je:");
        put = optP.najvrednijeBlagoPut(0,0);
        if (put != null){
            System.out.println(put);
            System.out.println("Najvrednije blago: " + put.getNajvrednijeBlago());
        }
        else
            System.out.println("Nema resenja.");


        System.out.println("Put sa najvise blaga je:");
        put = optP.najviseBlagaPut(0,0);
        if (put != null){
            System.out.println(put);
            System.out.println("Broj blaga: " + put.getBrojPoljaSaBlagom());
        }
        else
            System.out.println("Nema resenja.");

        System.out.println("Put sa najvise uzastopnih blaga je:");
        put = optP.najviseUzastopnihBlagaPut(0,0);
        if (put != null){
            System.out.println(put);
            System.out.println("Broj blaga: " + put.getNajviseUzastopnihPoljaSaBlagom());
        }
        else
            System.out.println("Nema resenja.");

        System.out.print("Svi putevi: ");
        System.out.println(optP.sviPutevi(0, 0));

        System.out.print("Broj puteva: ");
        System.out.println(optP.prebrojPuteve(0, 0));

        System.out.print("Broj puteva sa vise od 3 uzastopna blaga: ");
        System.out.println(optP.prebrojPuteveViseOd3UzastopnaBlaga(0, 0));
    }


}
