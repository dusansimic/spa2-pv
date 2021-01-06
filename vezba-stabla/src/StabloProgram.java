import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.svetovid.Svetovid;

class Stablo {
    private static class Cvor {
    	Automobil info;
    	Cvor levo;
    	Cvor desno;
    	
    	public Cvor(Automobil info, Cvor levo, Cvor desno) {
    		this.info = info;
    		this.levo = levo;
    		this.desno = desno;
    	}
    	
    	public Cvor kopija() {
    		return new Cvor(info, levo, desno);
    	}
    }

    private Cvor koren;

    // konstruktor za prazno stablo
    public Stablo() {
    	this.koren = null;
    }
    
    public Stablo(Automobil auto) {
    	this.koren = new Cvor(auto, null, null);
    }
    
    protected Stablo(Cvor cvor) {
    	this.koren = cvor;
    }
    
    
    // ------ dodati opisane metode ------------
    
    // ispisuje registracije svih automobila iz Novog Sada
    public void ispisiNS() {
    	System.out.println("Svi automobili iz Novog Sada: ");
    	sviIzNs(koren);
    }
    
    private void sviIzNs(Cvor cvor) {
    	if (cvor == null) 
    		return;
    	
    	if (cvor.info.getRegistracija().contains("NS")) {
    		System.out.println(cvor.info.getRegistracija());
    	}
    	
    	sviIzNs(cvor.levo);
    	sviIzNs(cvor.desno);    	
    }
    
    // vraca sumu kilometraza svih plavih automobila
    public long presliPlavi() {
    	Suma suma = new Suma();
    	presliPlavi(koren, suma);
    	System.out.println("Plavi automobili su presli: ");
    	return suma.getSuma();
    }
    
    private void presliPlavi(Cvor cvor, Suma suma) {
    	if (cvor == null)
    		return;
    	
    	if (cvor.info.getBoja().equals("Plava")) {
    		suma.setSuma(cvor.info.getKilometraza());
    	}
    	
    	presliPlavi(cvor.levo, suma);
    	presliPlavi(cvor.desno, suma);
    }
    

    /*
     * Pronalazi u stablu auto sa prosledjenom registracijom i
     * kreira novo stablo u kojem pronadjeni auto ima za @param long km
     * povecanu predjenu kilometrazu
     */
    public Stablo presaoJos(String registracija, long km) {
    	Cvor korenNovog = kopijaCvora(koren, registracija, km);
    	return new Stablo(korenNovog);
    }
    
    private Cvor kopijaCvora(Cvor cvor, String reg, long km) {
    	if (cvor == null) 
    		return null;
    	
    	Cvor novi;
    	if (cvor.info.getRegistracija().equals(reg)) {
    		Automobil auto = new Automobil(cvor.info.getModel(), 
    										cvor.info.getBoja(), 
    											reg, (cvor.info.getKilometraza() + km));
    		novi = new Cvor(auto, null, null);
    	} else {
    		novi = new Cvor(cvor.info, null, null);
    	}
    	 
    	novi.levo = kopijaCvora(cvor.levo, reg, km);
    	novi.desno = kopijaCvora(cvor.desno, reg, km);
    	return novi;
    }
    
    /*
     * Vraca listu automobila koji imaju vecu kilometrazu od 
     * svog roditelja i susednog cvora (bratskog)
     */
    public List<Automobil> sortiraniPlavi() {
    	List<Automobil> lista = new ArrayList<Automobil>();
    	listaPlavih(lista, koren, null);
    	return lista;
    }
    
    private void listaPlavih(List<Automobil> lista, Cvor tekuci, Cvor roditelj) {
    	if (tekuci == null) 
    		return;
    	
    	// prvo proverimo da li je auto plavi i da li ima roditelja
    	if (tekuci.info.getBoja().equals("Plava") && roditelj != null) {
    		// zatim proverimo da li tekuci cvor ima vecu kilometrazu
    		// od roditelja ako nema onda ne proveravamo nista dalje
    		if (tekuci.info.getKilometraza() > roditelj.info.getKilometraza()) {
    			// gledamo da li nas tekuci cvor ima susednog i da li susedni zadovoljava uslov
    			if (roditelj.levo != null && roditelj.levo.info.getKilometraza() < tekuci.info.getKilometraza()) {
    				lista.add(tekuci.info);
    			}
    			if (roditelj.desno != null && roditelj.desno.info.getKilometraza() < tekuci.info.getKilometraza()) {
    				lista.add(tekuci.info);
    			}
    			
    		}
    	}
    	
    	listaPlavih(lista, tekuci.levo, tekuci);
    	listaPlavih(lista, tekuci.desno, tekuci);    	
    }
    
    public boolean jeBST() {
    	return jeBSTpom(koren, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    private boolean jeBSTpom(Cvor tekuci, long minKm, long maxKm) {
    	if (tekuci == null)
    		return true;
    	
    	if (tekuci.info.getKilometraza() < minKm || tekuci.info.getKilometraza() > maxKm) 
    		return false;
    	
    	return (jeBSTpom(tekuci.levo, minKm, tekuci.info.getKilometraza()-1) &&
    			jeBSTpom(tekuci.desno, tekuci.info.getKilometraza()+1, maxKm));
    }
    
    
    
    // ------ po potrebi i pomocne metode, konstruktore, klase i sl
    // ------ ne dodavati polja u klasu!
}

// Glavna klasa
public class StabloProgram {

	// Glavni program
	public static void main(String[] args) {

		// Napravimo pomocni objekat za ucitavanje i ispisivanje
		TreeIO<Stablo> io = new TreeIO<>(Stablo.class);

		// Procitamo stablo iz fajla
		Stablo stablo = io.read(Svetovid.in("res/domaci9/Veliko.txt"));
		// alternativno mozemo iz nekog drugog fajla
		// otkomentarisati neki od redova dole, a skloniti ovaj iznad
		//Stablo stablo = io.read(Svetovid.in("Veliko.txt"));
		//Stablo stablo = io.read(Svetovid.in("Prazno.txt"));

		// Ispisemo ucitano stablo
		io.print(Svetovid.out, stablo);
		System.out.println();
		
		
		// -------- ovde dodati pozive metoda ----------
		
		stablo.ispisiNS();
		System.out.println(stablo.presliPlavi());
		System.out.println();
		Stablo novo = stablo.presaoJos("BG 082-ZD", 12000);
		//io.print(Svetovid.out, novo);
		//System.out.println();
		System.out.println(stablo.sortiraniPlavi());
		
		if(stablo.jeBST()) {
			System.out.println("Stablo jeste BST.");
		} else {
			System.out.println("Stablo nije BST.");
		}
	}
}
