import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NalazenjePuta {
    private static Put optimalniPut;

    private static void optimalniPut(int x, int y, Mapa mapa, Put put, Comparator<Put> c) {
        if (!mapa.isInLavirint(x, y)) {
            return;
        }

        if (mapa.isZivaOgrada(x, y)) {
            return;
        }

        if (mapa.getPosecenost(x, y)) {
            return;
        }

        if (mapa.isNextToMinotaur(x, y)) {
            return;
        }

        if (mapa.isPrinceza(x, y)) {
            put.dodaj(x, y, 0);
            if (optimalniPut == null || c.compare(put, optimalniPut) > 0) {
                optimalniPut = new Put(put);
            }
            put.izbaciKraj();
            return;
        }

        mapa.setPosecenost(x, y, true);
        put.dodaj(x, y, mapa.getLavirint(x, y));

        optimalniPut(x+1, y, mapa, put, c);
        optimalniPut(x-1, y, mapa, put, c);
        optimalniPut(x, y+1, mapa, put, c);
        optimalniPut(x, y-1, mapa, put, c);

        mapa.setPosecenost(x, y, false);
        put.izbaciKraj();
    }

    public static void main(String[] args) {
        String file = Svetovid.in.readLine("Unesite ime fajla:");
        if (file.equalsIgnoreCase("")) {
            file = "res/u1.txt";
        }

        Mapa mapa = Mapa.ucitaj(file);
        if (mapa == null) {
            return;
        }

        Mapa.stampajMapu(mapa);
        List<String> polja = new ArrayList<String>();

        optimalniPut(0, 0, mapa, new Put(), new KomparatorPoBrojuOruzija());

        Svetovid.out.println(optimalniPut);
    }
}
