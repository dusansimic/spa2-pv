public class Suma {
	
	private long suma;
	private long brElemenata;
	
	public Suma() {
		this.suma = 0;
		this.brElemenata = 0;
	}

	public long getSuma() {
		return suma;
	}

	public void setSuma(long suma) {
		this.suma += suma;
		setBrElemenata();
	}

	public long getBrElemenata() {
		return brElemenata;
	}

	public void setBrElemenata() {
		this.brElemenata += 1;
	}
	
	

}
