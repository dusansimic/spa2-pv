import java.util.Comparator;

public class KomparatorPrezime implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return s1.getPrezime().compareTo(s2.getPrezime());
    }
}
