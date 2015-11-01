import java.util.Arrays;
import java.util.stream.Stream;
import java.util.function.BinaryOperator;

public class Logic {

	public static boolean doLogic(Boolean... bs) {
		return	Arrays.asList(bs).stream().sorted()  // false -ok elore, utana true-k
				.skip( // hagyjuk ki az elso N elemet
					Arrays.asList(bs).stream().filter(p -> p).count() // a true-k szamanyit 
				).anyMatch(p -> !p) // van meg false, akkor jo, azaz:  count(true) < count(false) 
				&&
				Arrays.asList(bs).stream().reduce(false, (b1, b2) -> b1 = b2) 
				// utolsó true. binOp értékül adódik, majd visszaadódik minden elemre (foldol). 
				// de nem == van, ezért ez értékadás. Az utolsó értéke lesz a visszatérési érték (aminek truenak kell lennie)
				&&
				Arrays.asList(bs).stream().findFirst().orElseGet(() -> false); 
				// az első true. Ha nem lenne true, vagy nem lenne első érték, akkor false lesz
	}
	
	public static void main(String[] args) {
		System.out.println(doLogic(true, false, false, false, true)); // A. nem
		// B. igen, az első és az utolsó paraméter is kötelezően true
		// C. nem, több false-nak kell lennie benne mint true-nak
		// D. igen, lsd C.
		// E. igen, mindenképp van 2 true, és ennél több false
		System.out.println(doLogic(true, false, false, false, false, false, false, false, false, false, true)); // F. nem
		System.out.println(doLogic(true, false, false, false, false, true)); // G. nem
		System.out.println(doLogic(true, false, false, false, false, true)); // H. nem
		
	}
}
