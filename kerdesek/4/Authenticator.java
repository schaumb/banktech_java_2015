import java.security.MessageDigest;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.BufferedReader;
import java.io.FileReader;

public class Authenticator {
	
	private static final String PASSWORD_ENCODED = "824949B53942423F4263F3464332A699EFECDD28E4B58D4450F3C54BE37C938D";
	
	public static void main(String[] args) throws Exception{
		try(BufferedReader br = new BufferedReader(new FileReader("out.txt"))) {
			for(String line; (line = br.readLine()) != null; ) {
				if(checkPassword(line)) {
					System.out.println(line);
					return;
				}
			}
		}
	}
	
	public static boolean checkPassword(String password) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] digest = md.digest(password.getBytes());
		String hex = new HexBinaryAdapter().marshal(digest);
		
		return hex.equals(PASSWORD_ENCODED);
	}
	
}