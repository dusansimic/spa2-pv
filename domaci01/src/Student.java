public class Student implements Comparable<Student> {
    private final String ime;
    private final String prezime;
    private final int godina;

    public Student(String ime, String prezime, int godina) {
        this.ime = ime;
        this.prezime = prezime;
        this.godina = godina;
    }

    @Override
    public String toString() {
        return "Student{" +
                "ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", godina=" + godina +
                '}';
    }

    @Override
    public int compareTo(Student that) {
        // Prvo se studenti sortiraju po godini upisa
        int rez = this.godina - that.godina;
        // Ukoliko im je godina upisa ista onda se sortiraju po prezimenu pa po imenu
        if (rez == 0) {
            rez = this.prezime.compareTo(that.prezime);
            if (rez == 0) {
                rez = this.ime.compareTo(that.ime);
            }
        }
        return rez;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public int getGodina() {
        return godina;
    }
}
