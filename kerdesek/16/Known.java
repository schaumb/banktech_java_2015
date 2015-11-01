
import java.lang.String;

public class Known implements Unknown {

	public static void main(String[] args) {
		int i = n;									// 1
		int j = Unknown.f();						// 2
		Known known = Unknown.create(Known::new);	// 3
		known.g();									// 4
		//int k = n++;								// 5
	}
}
