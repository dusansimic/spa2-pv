import org.svetovid.io.SvetovidReader;

/**
 * Ova klasa nam sluzi samo zato da pojednostavimo menjanje tipa u TestHash.
 * 
 * Inace uopste nije neophodno da tipovi prosiruju ovu klasu da bi se koristili
 * u OHashSet.
 */
public abstract class InfoTip {
	abstract public InfoTip ucitaj(SvetovidReader r);
}
