import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Put {

    private ArrayList<Polje> polja;

    public Put () {
        polja = new ArrayList<Polje> ();
    }

    public Put (Put original){
        this();
        for (Polje p: original.polja)
            dodaj(p.getX(), p.getY(), p.getV());
    }

    public void dodaj (int x, int y, int v) {
        polja.add(new Polje(x, y, v));
    }

    public Put kopija(){
        Put rez = new Put();
        for (Polje p: polja)
            rez.dodaj(p.getX(), p.getY(), p.getV());
        return rez;
    }

    public void izbaciKraj (){
        if (getLength() > 0)
            polja.remove(getLength()-1);
        else
            System.out.println("Put ne sadrzi polja.");
    }

    public int getLength () {
        return polja.size();
    }

    public int getVrednost(){
        int rez = 0;
        for (Polje p: polja){
            rez += p.getV();
        }
        return rez;
    }

    public int getNajvrednijeBlago (){
        Polje poljeSaMaxVrednoscu = polja.stream().max(Comparator.comparing(Polje::getV)).orElseThrow(NoSuchElementException::new);
        return poljeSaMaxVrednoscu.getV();
    }

    public int getBrojPoljaSaBlagom () {
        long brojPolja = polja.stream().filter(p -> p.getV() >0).count();
        return (int) brojPolja;
    }

    public int getNajviseUzastopnihPoljaSaBlagom(){
        int brojac = 0;
        int rezultat = 0;

        for (Polje p: polja){
            if (p.getV() == 0)
                brojac = 0;
            else if (p.getV() > 0){
                brojac++;
                rezultat = Math.max(rezultat, brojac);
            }
        }
        return rezultat;
    }

    public String toString () {
        StringBuilder sb = new StringBuilder();
        sb.append("Resenje: [");
        if (getLength()>0) {
            sb.append(polja.get(0));
            for (int i=1; i < getLength(); i++)
                sb.append(", " + polja.get(i));
        }
        sb.append(" ]");
        return sb.toString();
    }


}
