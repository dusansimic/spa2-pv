import org.svetovid.Svetovid;
import org.svetovid.io.SvetovidReader;

import java.util.ArrayList;
import java.util.List;

public class NalazenjePuta {

    public static final int IZLAZ = -9;

    private static int sirina, visina;

    private static int[][] lavirint;
    private static boolean[][] posecenost;

    private static int ps, pv, k;

    private static int zabranjenRed;

    private Put optimalniPut;

    public NalazenjePuta(String fajl) {
        if (!Svetovid.testIn(fajl)) {
            throw new RuntimeException("Nije moguce ucitati fajl!");
        }

        SvetovidReader in = Svetovid.in(fajl);

        sirina = in.readInt();
        visina = in.readInt();

        lavirint = new int[sirina][visina];
        posecenost = new boolean[sirina][visina];

        for (int j = 0; j < visina; j++) {
            for (int i = 0; i < sirina; i++) {
                lavirint[i][j] = in.readInt();
            }
        }

        ps = in.readInt();
        pv = in.readInt();
        k = in.readInt();

        in.close();

        zabranjenRed = zabranjenRed();
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

    private int putevi() {
        List<Put> putevi = new ArrayList<Put>();
        nadjiPuteve(ps, pv, lavirint[ps][pv], new Put(), putevi);
        return putevi.size();
    }

    private int puteviK() {
        List<Put> putevi = new ArrayList<Put>();
        nadjiPuteve(ps, pv, lavirint[ps][pv], new Put(), putevi);
        return (int) putevi.stream().filter(p -> p.getLength() >= k).count();
    }

    private Put optimalniPut() {
        List<Put> putevi = new ArrayList<Put>();
        nadjiPuteve(ps, pv, lavirint[ps][pv], new Put(), putevi);
        return optimalniPut;
    }



    private void nadjiPuteve(int x, int y, int lastV, Put r, List<Put> rs) {
        if (x < 0 || x >= sirina || y < 0 || y >= visina) {
            return;
        }

        if (lavirint[x][y] != IZLAZ && lavirint[x][y] % 2 != lastV % 2) {
            return;
        }

        if (posecenost[x][y]) {
            return;
        }

        if (y == zabranjenRed) {
            return;
        }

        if (lavirint[x][y] == IZLAZ) {
            r.dodaj(x, y, lavirint[x][y]);
            rs.add(r.kopija());
            if (r.getLength() >= k) {
                if (optimalniPut == null || r.getVrednost() > optimalniPut.getVrednost()) {
                    optimalniPut = r.kopija();
                }
            }
            r.izbaciKraj();
            return;
        }

        posecenost[x][y] = true;
        r.dodaj(x, y, lavirint[x][y]);

        nadjiPuteve(x+1, y, lavirint[x][y], r, rs);
        nadjiPuteve(x-1, y, lavirint[x][y], r, rs);
        nadjiPuteve(x, y+1, lavirint[x][y], r, rs);
        nadjiPuteve(x, y-1, lavirint[x][y], r, rs);

        posecenost[x][y] = false;
        r.izbaciKraj();
    }

    private int zabranjenRed() {
        int zabranjenRed = 0;
        int maks = lavirint[0][0];

        for (int j = 0; j < visina; j++) {
            for (int i = 0; i < sirina; i++) {
                if (lavirint[i][j] > maks) {
                    maks = lavirint[i][j];
                    zabranjenRed = j;
                }
            }
        }

        return zabranjenRed;
    }

    public static void main(String[] args) {
        String fajl = Svetovid.in.readLine("Unesite ime fajla:");

        NalazenjePuta np = new NalazenjePuta(fajl);

        Svetovid.out.printf("Broj svih puteva: %d\n", np.putevi());
        Svetovid.out.printf("Broj puteva duzine makar k: %d\n", np.puteviK());

        Put opt = np.optimalniPut();
        if (opt != null) {
            Svetovid.out.println("Optimalni put:");
            Svetovid.out.println(opt);
        } else {
            Svetovid.out.println("Nema optimalnog puta!");
        }
    }
}
