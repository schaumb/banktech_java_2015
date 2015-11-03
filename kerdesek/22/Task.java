import java.text.DecimalFormat;

public class Task {

	public static void main(String[] args) {
		System.out.println(0x7fffffffffffffffl); // 9223372036854775807, nem primszam, 7^2×73×127×337×92737×649657
		
		System.out.println(9223372036854775907d); // pontatlan abrazolas miatt 9223372036854776000 lesz -> nem primszam
		// System.out.println(new DecimalFormat("#000000000000000000000").format(9223372036854775907d));

		System.out.println(0b111111111111111111111111111111111111111111111111111111111100111l); // 9223372036854775783 primszam

		System.out.println(Integer.MAX_VALUE); // 2147483647, primszam, de nem a legnagyobb

		System.out.println(9.223372036854776E18); // pontatlan abrazolas
		// System.out.println(new DecimalFormat("#000000000000000000000").format(9.223372036854776E18));

		System.out.println(Double.POSITIVE_INFINITY); // nem pontos ertek, es nem prim
	}
}
