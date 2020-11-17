import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * StatSet je primer standardnog Java skupa (interfejs `Set`) sa dodatnim
 * vodjenjem računa o tome kako se podaci smeštaju u okviru skupa, što može
 * pomoći da se bolje oceni kvalitet funkcije `hashCode` za konkretne objekte u
 * skupu.
 *
 * http://perun.dmi.rs
 *
 * @param <E>
 *            Tip objekata koji će biti čuvani u skupu
 */
public class StatSet<E> extends AbstractSet<E> {

	private final HashSet<E> set = new HashSet<E>();
	private final HashMap<Integer, Integer> count = new HashMap<>();

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public boolean isEmpty() {
		return set.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return set.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		// posto elementi mogu da se uklone preko iteratora
		// moramo paziti da se brojaci odrzavaju ispravno
		return new CountUpdateIterator<E>(set.iterator());
	}

	@Override
	public Object[] toArray() {
		return set.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return set.toArray(a);
	}

	@Override
	public boolean add(E e) {
		boolean b = set.add(e);
		if (b) {
			incCount(e.hashCode());
		}
		return b;
	}

	@Override
	public boolean remove(Object o) {
		boolean b = set.remove(o);
		if (b) {
			decCount(o.hashCode());
		}
		return b;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean b = set.addAll(c);
		recalculateCount();
		return b;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean b = set.retainAll(c);
		recalculateCount();
		return b;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean b = set.removeAll(c);
		recalculateCount();
		return b;
	}

	@Override
	public void clear() {
		count.clear();
		set.clear();
	}

	private void incCount(int h) {
		Integer i = count.get(h);
		if (i == null)
			i = new Integer(0);
		count.put(h, i + 1);
	}

	private void decCount(int h) {
		Integer i = count.get(h);
		if (i == null)
			i = 0;
		count.put(h, i - 1);
	}

	public int getNumberOfValues() {
		return count.size();
	}

	public double getAverageChainLength() {
		double avg = 0.0;
		if (set.size() > 0) {
			for (Integer i : count.values()) {
				avg += i;
			}
			avg = avg / count.size();
		}
		return avg;
	}

	public double getStdDevChainLength() {
		return stDevFromAvg(getAverageChainLength());
	}

	private double stDevFromAvg(double avg) {
		double std = 0.0;
		if (set.size() > 0) {
			for (Integer i : count.values()) {
				std += (i - avg) * (i - avg);
			}
			std = std / count.size();
		}
		return Math.sqrt(std);
	}

	public int getLongestChain() {
		return Collections.max(count.values());
	}

	public double getChiSquare(long range) {
		double chi = 0.0;
		if (set.size() > 0) {
			double avg = ((double) set.size()) / (double) range;
			for (Integer i : count.values()) {
				chi += (i - avg) * (i - avg) / avg;
			}
		}
		return chi;
	}

	public void printStats() {
		System.out.printf("Number of elements:\t%6d\n", set.size());
		int numv = getNumberOfValues();
		System.out.printf("Different values:\t%6d\t(%.2f %%)\n", numv, numv*100.0/set.size());
		double avg = getAverageChainLength();
		System.out.printf("Avg. search chain len.:\t%5.2f +- %5.2f\n", avg, stDevFromAvg(avg));
		System.out.printf("Longest search chain:\t%5d\n", getLongestChain());
		System.out.printf("Chi square (no. of el):\t%,14.2f\n" , getChiSquare(set.size()));
		System.out.printf("Chi square (diff el.):\t%,14.2f\n", getChiSquare(count.size()));
	}

	public double getChiSquare() {
		// vraca za ceo moguc opseg vrednosti
		return getChiSquare(2L * Integer.MAX_VALUE + 2);
	}

	public E someElement() {
		if (!set.isEmpty()) {
			return set.iterator().next();
		} else {
			return null;
		}
	}

	private void recalculateCount() {
		count.clear();
		for (E el : set) {
			incCount(el.hashCode());
		}
	}

	/**
	 * Dodatna pomocna klasa koja brine o odrzavanju brojaca u slucaju da se
	 * objekti uklanjaju preko iteratora.
	 *
	 */
	private class CountUpdateIterator<F> implements Iterator<F> {

		private final Iterator<F> iterator;

		private F trenutni = null;

		public CountUpdateIterator(Iterator<F> it) {
			iterator = it;
		}

		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public F next() {
			trenutni = iterator.next();
			return trenutni;
		}

		@Override
		public void remove() {
			iterator.remove();
			if (trenutni != null) {
				decCount(trenutni.hashCode());
			}
		}
	}
}
