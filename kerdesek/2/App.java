// http://www.javadecompilers.com/
//package eu.dorsum.javachallenge.questions;

import java.io.PrintStream;
import java.util.function.IntFunction;

public class App {
    public static void main(String[] args) {
        String pass = "alma";
        int crc = 65535;
        IntFunction<Integer> supplier = c -> {
            for (int j = 0; j < pass.length(); ++j) {
                c = (c >>> 7 | c << 8) & 65535;
                c^=pass.charAt(0) & 255;
                c^=(c & 255) >> 4;
                c^=c << 13 & 65535;
                c^=(c & 255) << 5 & 65535;
            }
            return c&=65535;
        };
        /*boolean exit = true;
        if (exit) {
            System.out.println("exit");
            return;
        }*/
        System.out.println(supplier.apply(crc)); // 51091
    }
}