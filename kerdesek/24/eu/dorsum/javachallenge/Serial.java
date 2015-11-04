package eu.dorsum.javachallenge;

import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.FileInputStream;

import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ClassNotFoundException;


public class Serial implements Serializable {

	private static final long serialVersionUID = 1L;
	private long number;

	public Serial(long number) {
		this.number = number;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		out.writeObject(Long.toHexString(number));
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		number = Long.parseLong((String) in.readObject());
	}
	
	public long getNumber() {
		return number;
	}	
}

