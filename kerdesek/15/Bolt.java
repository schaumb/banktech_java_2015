import java.util.concurrent.Callable;
import java.util.List;
import java.util.Arrays;

public class Bolt {
	public static Float vasarol(float penz, Termek termek){
		if(penz >= termek.getAr()){
			penz = new Float(penz-termek.getAr());
		} else {
			System.out.println("Nem tudod megvasarolni a termeket: " +   termek.name());
		}
		return penz;
	}
	public enum Termek {
		CERUZA(new Float(0.1f)),
		RADIR(Float.valueOf("0.2f")),
		VONALZO(0.3f),
		TOLL((float)0.4);
		private final Float ar;
		Termek(float ar){
			this.ar = ar;
		}	
		public float getAr(){
			return this.ar;
		}
	}


	// NEXT PERMUTATION
	// http://codeforces.com/blog/entry/3980
	
	@SuppressWarnings("unchecked")
	private static Comparable[] nextPermutation( final Comparable[] c ) {
		// 1. finds the largest k, that c[k] < c[k+1]
		int first = getFirst( c );
		if ( first == -1 ) return null; // no greater permutation
		// 2. find last index toSwap, that c[k] < c[toSwap]
		int toSwap = c.length - 1;
		while ( c[ first ].compareTo( c[ toSwap ] ) >= 0 )
			--toSwap;
		// 3. swap elements with indexes first and last
		swap( c, first++, toSwap );
		// 4. reverse sequence from k+1 to n (inclusive) 
		toSwap = c.length - 1;
		while ( first < toSwap )
			swap( c, first++, toSwap-- );
		return c;
	}

	// finds the largest k, that c[k] < c[k+1]
	// if no such k exists (there is not greater permutation), return -1
	@SuppressWarnings("unchecked")
	private static int getFirst( final Comparable[] c ) {
		for ( int i = c.length - 2; i >= 0; --i )
			if ( c[ i ].compareTo( c[ i + 1 ] ) < 0 )
				return i;
		return -1;
	}

	// swaps two elements (with indexes i and j) in array 
	private static void swap( final Comparable[] c, final int i, final int j ) {
		final Comparable tmp = c[ i ];
		c[ i ] = c[ j ];
		c[ j ] = tmp;
	}



	public static class Vasarol implements Callable<Boolean> {
		private final Termek termek;
		
		public Vasarol(Termek termek) {
			this.termek = termek;
		}
		
		@Override
		public Boolean call() throws Exception {

			System.out.println("Van " + Bolt.f.toString() + " penzem, vasarolok ennyiert: " + termek.getAr());
			float cp = Bolt.f;
			Bolt.f = Bolt.vasarol(Bolt.f, termek);
			System.out.println("Siker? " + (f != cp) + ", maradt: " + Bolt.f.toString() + " cash");
			return Bolt.f != cp;
		}
	}

	public static Float f = new Float(1.0f);
	public static int countOfBad = 0;
	public static int countOfGood = 0;
	public static int countOfEnough = 0;
	static Callable<Boolean> vasarlasok [] = new Vasarol[]{ new Vasarol(Termek.CERUZA), 
															new Vasarol(Termek.RADIR), 
															new Vasarol(Termek.VONALZO), 
															new Vasarol(Termek.TOLL) };

	private static void tryToBuy( Comparable[] arr ) throws Exception {
		boolean result = true;
		f = new Float(1.0f);

		for(Comparable c : arr ) {
			result &= vasarlasok[(Integer)c].call();
		}

		if(!result) {
			++countOfBad;
		} else if(f > 0.0f) {
			++countOfGood;
		} else {
			++countOfEnough;
		}
		System.out.println();
	}

	private static void runPermutations( Comparable[] c ) throws Exception {
		tryToBuy(c);
		while ( ( c = nextPermutation( c ) ) != null ) {
			tryToBuy(c);
		}
	}
	
	public static void main(String[] args) throws Exception {
		//Float penz = new Float(1.0f); 
		//penz = Bolt.vasarol(penz, Termek.CERUZA); pelda
		
		Integer arr [] = {0, 1, 2, 3};
		
		runPermutations(arr);
		
		System.out.println(" + -ba  maradt: " + countOfGood);
		System.out.println(" 0 -ba  maradt: " + countOfEnough);
		System.out.println(" - -ba  maradt: " + countOfBad);
	}
  }
