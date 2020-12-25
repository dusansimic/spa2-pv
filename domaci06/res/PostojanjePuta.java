package vezbe.cas5.zadatak;

import org.svetovid.Svetovid;
import org.svetovid.io.SvetovidReader;

import java.util.ArrayList;
import java.util.List;

public class PostojanjePuta {

    public static final int IZLAZ = -99;
    public static final int ZID = -11;

    private static int visina, sirina;

    private static int [][] lavirint;
    private static boolean [][] posecenost;

    public static boolean ucitaj (String fajl){
        if (!Svetovid.testIn(fajl))
            return false;

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
        return true;
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

    public static void stampajPosecenost (){
        System.out.println(visina + " " + sirina);
        System.out.println("Posecenost: ");
        for (int j = 0; j< visina; j++){
            for (int i = 0; i< sirina; i++) {
                System.out.printf("%1$5d",posecenost[i][j]?1:0);
            }
            System.out.println();
        }
    }

    private static boolean postojiPut(int x, int y, List<String> polja, boolean rupa){

        if (x <0 || x>=sirina || y < 0 || y >=visina)
            return false;

        if (lavirint[x][y] == ZID)
            return false;

        if (posecenost[x][y])
            return false;

        if (lavirint[x][y] == IZLAZ) {
            polja.add(x + "-" + y);
            return true;
        }

        if (rupa && lavirint[x][y] == -1)
            return false;

        posecenost[x][y] = true;
        polja.add(x + "-" + y);

        if (postojiPut(x+1,y, polja, lavirint[x][y] == -1))
            return true;

        if (postojiPut(x-1, y, polja, lavirint[x][y] == -1))
            return true;

        if (postojiPut(x, y+1, polja, lavirint[x][y] == -1))
            return true;

        if (postojiPut(x, y-1, polja, lavirint[x][y] == -1))
            return true;

        posecenost[x][y] = false;
        polja.remove(x + "-" + y);

        return false;

    }

    public static void main (String [] args){
        System.out.println("Unesite ime fajla: ");
        String imef = Svetovid.in.readLine();
        if (imef.equalsIgnoreCase(""))
            imef="res/lav1.txt";

        if (ucitaj(imef)){
            stampaj();
            List<String> polja = new ArrayList<String>();
            System.out.println("Unesite koordinate:");
            int x = Svetovid.in.readInt();
            int y = Svetovid.in.readInt();

            if (postojiPut(x,y, polja, lavirint[x][y]==-1))
                System.out.println("Put postoji.");
                stampajPosecenost();
                System.out.println(polja);

        }

    }



}
