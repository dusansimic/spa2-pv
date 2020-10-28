import java.util.Comparator;

public class KomparatorDuzinaImena implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        int l1 = (s1.getPrezime() + s1.getIme()).length();
        int l2 = (s2.getPrezime() + s2.getIme()).length();
        return l1 - l2;
    }
}
