import java.util.Comparator;

public class PrirodniKomparator implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return s1.compareTo(s2);
    }
}
