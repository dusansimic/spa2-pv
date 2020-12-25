import org.svetovid.io.SvetovidReader;

public class Mapa {
    public static final int PRINCEZA = 30;
    public static final int MINOTAUR = 20;
    public static final int ORUZIJE_MIN = 1;
    public static final int ORUZIJE_MAX = 10;
    public static final int ZIVA_OGRADA = -11;

    private int visina, sirina;

    private int[][] lavirint;
    private boolean[][] posecenost;

    public static Mapa ucitaj(String file) {
        if (!Svetovid.testIn(file)) {
            return null;
        }

        SvetovidReader in = Svetovid.in(file);

        Mapa mapa = new Mapa();

        mapa.sirina = in.readInt();
        mapa.visina = in.readInt();

        mapa.lavirint = new int[mapa.sirina][mapa.visina];
        mapa.posecenost = new boolean[mapa.sirina][mapa.visina];

        for (int j = 0; j < mapa.visina; j++) {
            for (int i = 0; i < mapa.sirina; i++) {
                mapa.lavirint[i][j] = in.readInt();
            }
        }

        in.close();

        return mapa;
    }

    public static void stampajMapu(Mapa mapa) {
        Svetovid.out.printf("%d x %d\n", mapa.sirina, mapa.visina);
        Svetovid.out.println("Lavirint:");
        for (int j = 0; j < mapa.visina; j++) {
            for (int i = 0; i < mapa.sirina; i++) {
                Svetovid.out.printf("%1$5d", mapa.lavirint[i][j]);
            }
            Svetovid.out.println();
        }
    }

    public static void stampajPosecenost(Mapa mapa) {
        Svetovid.out.printf("%d x %d\n", mapa.sirina, mapa.visina);
        Svetovid.out.println("Posecenost:");
        for (int j = 0; j < mapa.visina; j++) {
            for (int i = 0; i < mapa.sirina; i++) {
                Svetovid.out.printf("%1$5d", mapa.posecenost[i][j] ? 1 : 0);
            }
            Svetovid.out.println();
        }
    }

    public int getVisina() {
        return visina;
    }

    public int getSirina() {
        return sirina;
    }

    public int getLavirint(int x, int y) {
        return lavirint[x][y];
    }

    public boolean getPosecenost(int x, int y) {
        return posecenost[x][y];
    }

    public void setPosecenost(int x, int y, boolean value) {
        this.posecenost[x][y] = value;
    }

    public boolean isInLavirint(int x, int y) {
        return x >= 0 && x < sirina && y >= 0 && y < visina;
    }

    public boolean isZivaOgrada(int x, int y) {
        return lavirint[x][y] == ZIVA_OGRADA;
    }

    public boolean isNextToMinotaur(int x, int y) {
        if (isInLavirint(x-1, y) && lavirint[x-1][y] == MINOTAUR) {
            return true;
        }
        if (isInLavirint(x+1, y) && lavirint[x+1][y] == MINOTAUR) {
            return true;
        }
        if (isInLavirint(x, y-1) && lavirint[x][y-1] == MINOTAUR) {
            return true;
        }
        if (isInLavirint(x, y+1) && lavirint[x][y+1] == MINOTAUR) {
            return true;
        }
        return false;
    }

    public boolean isPrinceza(int x, int y) {
        return lavirint[x][y] == PRINCEZA;
    }
}
