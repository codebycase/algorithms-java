package a11_parallel_computing;

import java.math.BigInteger;
import java.util.Date;
import java.util.Random;

public class ReaderWriter {
	static String data = new Date().toString();
	static Random random = new Random();

	static Object LR = new Object();
	static int readCount = 0;
	static Object LW = new Object();

	public static class Task {
		static Random r = new Random();

		static void doSomeThingElse() {
			BigInteger b = BigInteger.probablePrime(521, r);
			System.out.println(" identified a big prime: " + b.mod(BigInteger.TEN));
			try {
				Thread.sleep(r.nextInt(1000));
			} catch (InterruptedException e) {
				// Time to move on.
			}
		}
	}

	// LR and LW are static members of type Object in the RW class.
	// They serve as read and write locks. The static integer
	// field readCount in RW tracks the number of readers.
	public static class Reader extends Thread {
		String name;

		Reader(String name) {
			this.name = name;
		}

		public void run() {
			while (true) {
				synchronized (ReaderWriter.LR) {
					ReaderWriter.readCount++;
				}
				System.out.println("Reader " + name + " is about to read");
				System.out.println(ReaderWriter.data);
				synchronized (ReaderWriter.LR) {
					ReaderWriter.readCount--;
					ReaderWriter.LR.notify();
				}
				Task.doSomeThingElse();
			}
		}
	}

	public static class Writer extends Thread {
		String name;

		Writer(String name) {
			this.name = name;
		}

		public void run() {
			while (true) {
				synchronized (ReaderWriter.LW) {
					boolean done = false;
					while (!done) {
						synchronized (ReaderWriter.LR) {
							if (ReaderWriter.readCount == 0) {
								// @exclude
								System.out.println("Writer " + name + " is about to write");
								// @include
								ReaderWriter.data = new Date().toString();
								done = true;
							} else {
								// Use wait/notify to avoid busy waiting.
								try {
									// Protect against spurious notify, see
									while (ReaderWriter.readCount != 0) {
										ReaderWriter.LR.wait();
									}
								} catch (InterruptedException e) {
									System.out.println("InterruptedException in Writer wait");
								}
							}
						}
					}
				}
				Task.doSomeThingElse();
			}
		}
	}

	public static void main(String[] args) {
		Thread r0 = new Reader("r0");
		Thread r1 = new Reader("r1");
		Thread w0 = new Writer("w0");
		Thread w1 = new Writer("w1");
		r0.start();
		r1.start();
		w0.start();
		w1.start();
		try {
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

}
