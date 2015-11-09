
public class Instant {

	private String name;
	
	public Instant(String name) { // 0
		this.name = name; // 0
		System.out.println(this);
	}
	
	@Override
	public String toString() {
		return "Instant [name=" + name + "]"; // 3 
	}

	public static void main(String[] args) {
		if (args == null || args.length == 0) {
			// Ez true, ha nem adunk meg külön argumentumot
			// Szóval a fájl neve nincs az args tömbben, a cpp-vel ellentétben
		}
		String s = "Bank"; // 1 : "Bank"
		s.concat(" Tech"); // 2 : " Tech", "Bank Tech"
		// System.out.println("after concat: " + s); // az s értéke nem változott
		s.split(" "); // 0: Ez mindig 'new String[] {input.toString()}'-t return-öl, ha nincs egyezés, lásd implementáció
		// System.out.println(s.split(" ")[0] == s); // teszt
		s.concat(""); // 1 : ""; mást nem, lásd impl
		s.toLowerCase(); // 1: "bank"
		System.out.println(s); // 0
		// szóval eddig 5
		new Instant("Bank"); // 1: "Bank"
		// 9
	}
}
