import java.util.Comparator;

public class KomparatorGodina implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return s1.getGodina() - s2.getGodina();
    }
}
