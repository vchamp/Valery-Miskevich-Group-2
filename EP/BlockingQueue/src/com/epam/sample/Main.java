package com.epam.sample;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

	public static Object lock = new Object();
	
	public static void main(String[] args) {
		BlockingQueue q = new ArrayBlockingQueue<String>(10);
		Producer p = new Producer(q);
		Consumer c1 = new Consumer("first", q);
		Consumer c2 = new Consumer("second", q);
		new Thread(p).start();
		new Thread(c1).start();
		new Thread(c2).start();

	}

	static class Producer implements Runnable {
		private final BlockingQueue queue;

		Producer(BlockingQueue q) {
			queue = q;
		}

		@Override
		public void run() {
			int i = 0;
			while (i < 20) {
				i++;
				try {
					queue.put(i+" object");
					System.out.println("Producer put object " + i);
//					dump(queue);
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

	}

	private static void dump(BlockingQueue queue2) {
		synchronized (lock) {
			System.out.println("Queue state: size = " + queue2.size());
			System.out.println("======================================");
		}

	}

	static class Consumer implements Runnable {
		private final BlockingQueue queue;
		public final String name;

		Consumer(String name, BlockingQueue q) {
			queue = q;
			this.name = name;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			while (!queue.isEmpty()) {
				try {
					String s = (String) queue.take();
					System.out.println(String.format("Consumer %s took %s", name, s));
//					dump(queue);
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

	}
}
