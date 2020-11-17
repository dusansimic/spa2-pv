import java.util.Comparator;

public class KomparatorGodinaDanSat extends KompozitniKomparator {
	public KomparatorGodinaDanSat() {
		super(new KomparatorGodina(), new KompozitniKomparator(new KomparatorDan(), new KomparatorSat()));
	}
}
