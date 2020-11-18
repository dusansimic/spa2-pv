public class KomparatorValutaSrednji extends KompozitniKomparator {
    public KomparatorValutaSrednji() {
        super(new KomparatorValuta(), new ObrnutiKomparator(new KomparatorSrednji()));
    }
}
