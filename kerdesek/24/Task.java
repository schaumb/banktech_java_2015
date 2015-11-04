import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.FileInputStream;

import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import eu.dorsum.javachallenge.Serial;

public class Task {

	public static void main(String[] args) throws Exception {
		FileInputStream fis = new FileInputStream("serial.obj");
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		Serial s = (Serial)ois.readObject();
		System.out.println(s.getNumber());
	}
}

