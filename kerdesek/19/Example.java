import java.lang.Thread;

class Counter{

	long count = 0;

	public void add(long value){
		this.count += value;
	}
}

class CounterThread extends Thread{

    private static Counter counter = new Counter();

    public void run() {
		for(int i=0; i<10; i++){
			// add01(i); // nem - tobb before -ba megy bele - nem szalbiztos, a CounterThread fuggvenyet szinkronizalja
			// add02(i); // igen - Statikuskent csak 1 fuggveny letezik, es ot szinkronizalja
			// add03(i); // igen - A statikus counter valtozot szinkronizalja
			// add04(i); // nem - tobb before -ba megy bele egyszerre - nem szalbiztos, a CounterThread objektumot szinkronizalja
        }
    }
	
	private synchronized void add01(int i){
		System.out.println("Before: " + counter.count);
		counter.add(i);
		System.out.println("After: " + counter.count);
	}

	private static synchronized void add02(int i){
		System.out.println("Before: " + counter.count);
		counter.add(i);
		System.out.println("After: " + counter.count);
	}
	
	private void add03(int i){
		synchronized(counter){
			System.out.println("Before: " + counter.count);
			counter.add(i);
			System.out.println("After: " + counter.count);
		}
	}

	private void add04(int i){
		synchronized(this){
			System.out.println("Before: " + counter.count);
			counter.add(i);
			System.out.println("After: " + counter.count);
		} 
	}
	
}

public class Example {
	public static void main(String[] args){
		  Thread  threadA = new CounterThread();
		  Thread  threadB = new CounterThread();

		  threadA.start();
		  threadB.start(); 
	}
}

