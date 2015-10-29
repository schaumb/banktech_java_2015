import java.io.*;
import java.nio.file.*;

public class Book {
	
	public static void main(String[] args) throws IOException {

		try (FileInputStream fis = new FileInputStream("Book.class")) {

			int sum = 0;
		    int b;
		    while ((b = fis.read()) != -1) {
		    	++sum;

		    	StringBuilder sb = new StringBuilder();
		    	sb.append("b: ").append(b).append('\t');

		    	String hex = Integer.toHexString(b);
		    	sb.append("hex: ").append(hex);

		        System.out.println(sb.toString());
		    }
		    System.out.println("sum: " + sum);

		}
	}

}