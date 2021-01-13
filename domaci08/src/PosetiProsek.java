public class PosetiProsek implements IPoseti{
    private int brojCvorova;
    private double sumaPlata;

    @Override
    public void poseti(StabloOsoba.Cvor cvor) {
        sumaPlata += cvor.osoba.getPlata();
        brojCvorova++;
    }

    public double prosek (){
        return sumaPlata/brojCvorova;
    }
}
