import org.svetovid.io.SvetovidReader;

import java.util.*;

public class Main {

    private static Set<Fudbaler> god19;
    private static Set<Fudbaler> god20;

    public static void main(String[] args) {
        god19 = ucitaj(Svetovid.in.readToken("Unesite ime fajla iz 2019:"));
        god20 = ucitaj(Svetovid.in.readToken("Unesite ime fajla iz 2020:"));
        god19.retainAll(god20);

        String[] imena = new String[]{"Golman", "Napadac", "Sredisnji", "Odbrambeni"};
        int[] sume = new int[4];
        for (Fudbaler f : god19) {
            switch (f.getPozicija()) {
                case "Golman":
                    sume[0]++;
                    break;
                case "Napadac":
                    sume[1]++;
                    break;
                case "Sredisnji":
                    sume[2]++;
                    break;
                case "Odbrambeni":
                    sume[3]++;
            }
        }

        for (int i = 0; i < 4; i++) {
            Svetovid.out.printf("%s: %d\n", imena[i], sume[i]);
        }
    }

    public static Set<Fudbaler> ucitaj(String file) {
        if (!Svetovid.testIn(file)) {
            Svetovid.err.printf("Nije moguce ucitavanje fajla: \"%s\"\n", file);
            return null;
        }

        SvetovidReader in = Svetovid.in(file);
        int n = in.readInt();

        Set<Fudbaler> skup = new HashSet<>();

        for (int i = 0; i < n; i++) {
            String ime = in.readLine();
            String pozicija = in.readLine();
            int godina = in.readInt();
            boolean tim = in.readLine().equals("da");
            skup.add(new Fudbaler(ime, pozicija, godina, tim));
        }

        in.close();

        return skup;
    }
}
