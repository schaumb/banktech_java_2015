import java.lang.StringBuilder;

public class Task {
	public static StringBuilder getName(){
		StringBuilder builder = new StringBuilder();
			try {
				builder.append("Loxon");
				return builder.append("4");
			} finally {
				builder = builder.append("Ever");
				builder = null;
		}
	}

	public static void main(String[] args) {
		System.out.println(getName());
		// A. nem, akkor nullptrexception lenne
		// B. nem, marhaság. Akkor nem működne
		// C. nem, sosem térünk vissza null-al. A return szépen megjegyzi a referenciát, és azzal tér vissza (E.)
		// D. nem, lsd futtatás
		// E. igen, lsd futtatás
		// F. igen, nem módosítjuk. Miért módosítaná, hisz a builder ref-el térünk vissza így is, úgy is 
	}
}
