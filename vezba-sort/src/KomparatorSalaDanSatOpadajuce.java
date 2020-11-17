import java.util.Comparator;

public class KomparatorSalaDanSatOpadajuce extends ObrnutiKomparator {
	public KomparatorSalaDanSatOpadajuce() {
		super(new KompozitniKomparator(new KomparatorSala(), new KompozitniKomparator(new KomparatorDan(), new KomparatorSat())));
	}
}
