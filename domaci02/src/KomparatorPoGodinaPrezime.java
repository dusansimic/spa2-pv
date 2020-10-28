import java.util.Comparator;

public class KomparatorPoGodinaPrezime extends KompozitniKomparator {
    public KomparatorPoGodinaPrezime() {
        super(new KomparatorGodina(), new KomparatorPrezime());
    }
}
