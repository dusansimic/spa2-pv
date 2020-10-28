import java.util.Comparator;

public class KompozitniKomparator implements Comparator<Student> {
    private final Comparator<Student> primarni;
    private final Comparator<Student> sekundarni;

    public KompozitniKomparator(Comparator<Student> primarni, Comparator<Student> sekundarni) {
        this.primarni = primarni;
        this.sekundarni = sekundarni;
    }

    @Override
    public int compare(Student s1, Student s2) {
        int rez = primarni.compare(s1, s2);
        if (rez == 0) {
            rez = sekundarni.compare(s1, s2);
        }
        return rez;
    }
}
