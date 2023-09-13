package bearmaps;

import org.junit.Test;

import java.util.HashSet;
import java.util.Optional;
import java.util.Random;

import static bearmaps.PrintHeapDemo.printSimpleHeapDrawing;
import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

	@Test(expected = IllegalArgumentException.class)
	public void testAdd() {
		ArrayHeapMinPQ<Integer> arrayHeapMinPQ = new ArrayHeapMinPQ<>();
		// one item
		arrayHeapMinPQ.add(1, 1);
		assertEquals(arrayHeapMinPQ.size(), 1);
		
		// multiple items
		arrayHeapMinPQ.add(3, 4);
		arrayHeapMinPQ.add(2, 6);
		assertEquals(arrayHeapMinPQ.size(), 3);
		
		// duplicate should not be added
		arrayHeapMinPQ.add(2, 6);
		assertEquals(arrayHeapMinPQ.size(),3);
		arrayHeapMinPQ.add(2, 2);
		assertEquals(arrayHeapMinPQ.size(),3);
	}
	
	@Test
	public void testContains() {
		ArrayHeapMinPQ<Integer> arrayHeapMinPQ = new ArrayHeapMinPQ<>();
		arrayHeapMinPQ.add(1, 1);
		assertTrue(arrayHeapMinPQ.contains(1));
		assertFalse(arrayHeapMinPQ.contains(2));
	}
	
	@Test
	public void testGetSmallest() {
		ArrayHeapMinPQ<Integer> arrayHeapMinPQ = new ArrayHeapMinPQ<>();
		arrayHeapMinPQ.add(3, 3);
		int smallest = arrayHeapMinPQ.getSmallest();
		assertEquals(smallest, 3);
		arrayHeapMinPQ.add(4, 4);
		smallest = arrayHeapMinPQ.getSmallest();
		assertEquals(smallest, 3);
		arrayHeapMinPQ.add(10, 1);
		smallest = arrayHeapMinPQ.getSmallest();
		assertEquals(smallest, 10);
		
		//random
		Random random = new Random();
		ArrayHeapMinPQ<Integer> randArrayHeapMinPQ = new ArrayHeapMinPQ<>();
		int r = Math.abs(random.nextInt(100));
		randArrayHeapMinPQ.add(1, r);
		randArrayHeapMinPQ.add(2, r * 2);
		smallest = randArrayHeapMinPQ.getSmallest();
		assertEquals(smallest, 1);
		randArrayHeapMinPQ.add(3, r-1);
		smallest = randArrayHeapMinPQ.getSmallest();
		assertEquals(smallest, 3);
	}
	
	@Test
	public void testRemoveSmallest() {
		ArrayHeapMinPQ<Integer> arrayHeapMinPQ = new ArrayHeapMinPQ<>();
		arrayHeapMinPQ.add(3, 3);
		arrayHeapMinPQ.add(4, 4);
		int smallest = arrayHeapMinPQ.removeSmallest();
		assertEquals(arrayHeapMinPQ.size(), 1);
		assertEquals(smallest, 3);
		int secondSmallest = arrayHeapMinPQ.getSmallest();
		assertEquals(secondSmallest, 4);
	}
	
	@Test
	public void testRemoveSmallestRandom() {
		Random random = new Random();
		ArrayHeapMinPQ<Integer> arrayHeapMinPQ = new ArrayHeapMinPQ<>();
		int smallest = 1000;
		NaiveMinPQ<Integer> naiveMinPQ = new NaiveMinPQ<>();
		for (int i=0; i < 100; i++) {
			int r = Math.abs(random.nextInt(200));
			try {
				arrayHeapMinPQ.add(r, r);
				if(r < smallest) smallest = r;
				
				naiveMinPQ.add(r, r);
			} catch(Exception e) {
			
			}
		}
		int s = arrayHeapMinPQ.removeSmallest();
		assertEquals(s, smallest);
		int n = naiveMinPQ.removeSmallest();
		assertEquals(s, n);
		
		for (int i=0; i < arrayHeapMinPQ.size() -10; i++) {
			s = arrayHeapMinPQ.removeSmallest();
			n = naiveMinPQ.removeSmallest();
			assertEquals(s, n);
		}
//		arrayHeapMinPQ.printHeap();
	}
	
	@Test
	public void testChangePriority() {
		ArrayHeapMinPQ<Integer> arrayHeapMinPQ = new ArrayHeapMinPQ<>();
		arrayHeapMinPQ.add(3, 3);
		arrayHeapMinPQ.add(1, 30);
		arrayHeapMinPQ.add(5, 5);
		int smallest = arrayHeapMinPQ.getSmallest();
		assertEquals(smallest, 3);
		arrayHeapMinPQ.changePriority(1, 1);
		smallest = arrayHeapMinPQ.getSmallest();
		assertEquals(smallest, 1);
	}
	
	@Test
	public void testSpeed() {
		ArrayHeapMinPQ<Integer> arrayHeapMinPQ = new ArrayHeapMinPQ<>();
		NaiveMinPQ<Integer> naiveMinPQ = new NaiveMinPQ<>();
		Random random = new Random();
		
		// initialize values
		HashSet<Integer> values = new HashSet<>();
		HashSet<Integer> otherValues = new HashSet<>();
		for(int i = 0; i < 100000; i++) {
			int r = Math.abs(random.nextInt(5000000));
			int p = Math.abs(random.nextInt(5000000));
			values.add(r);
			otherValues.add(p);
		}
		
		long start = System.currentTimeMillis();
		
		// initialize the two min-heaps
		for (int v : values) {
			arrayHeapMinPQ.add(v,v);
		}
		long end = System.currentTimeMillis();
		System.out.println("MY HEAP - ADD. Total time elapsed: " + (end - start)/1000.0 +  " seconds.");
		
		start = System.currentTimeMillis();
		
		for (int v : values) {
			naiveMinPQ.add(v,v);
		}
		end = System.currentTimeMillis();
		System.out.println("NAIVE HEAP - ADD. Total time elapsed: " + (end - start)/1000.0 +  " seconds.\n");
//
//		// Contains
//		start = System.currentTimeMillis();
//		for (int v : otherValues) {
//			arrayHeapMinPQ.contains(v);
//
//		}
//		end = System.currentTimeMillis();
//		System.out.println("MY HEAP - CONTAINS. Total time elapsed: " + (end - start)/1000.0 +  " seconds.");
//
//		start = System.currentTimeMillis();
//		for (int v : otherValues) {
//			naiveMinPQ.contains(v);
//		}
//		end = System.currentTimeMillis();
//		System.out.println("NAIVE HEAP - CONTAINS. Total time elapsed: " + (end - start)/1000.0 +  " seconds.\n");
//
//		// getSmallest
//		start = System.currentTimeMillis();
//		for (int v : otherValues) {
//			arrayHeapMinPQ.getSmallest();
//
//		}
//		end = System.currentTimeMillis();
//		System.out.println("MY HEAP - getSmallest. Total time elapsed: " + (end - start)/1000.0 +  " seconds.");
//
//		start = System.currentTimeMillis();
//		for (int v : otherValues) {
//			naiveMinPQ.getSmallest();
//		}
//		end = System.currentTimeMillis();
//		System.out.println("NAIVE HEAP - getSmallest. Total time elapsed: " + (end - start)/1000.0 +  " seconds.\n");
//

		// removeSmallest
		start = System.currentTimeMillis();
		for(int i = 0; i < 1000; i++) {
			arrayHeapMinPQ.removeSmallest();
		}

		end = System.currentTimeMillis();
		System.out.println("MY HEAP - removeSmallest. Total time elapsed: " + (end - start)/1000.0 +  " seconds.");

		start = System.currentTimeMillis();
		for(int i = 0; i < 1000; i++) {
			naiveMinPQ.removeSmallest();
		}
		end = System.currentTimeMillis();
		System.out.println("NAIVE HEAP - removeSmallest. Total time elapsed: " + (end - start)/1000.0 +  " seconds.\n");


//		// changePriority
//		start = System.currentTimeMillis();
//		for (int v : values) {
//			arrayHeapMinPQ.changePriority(v, v * Math.abs(random.nextInt(5)));
//		}
//		end = System.currentTimeMillis();
//		System.out.println("MY HEAP - getSmallest. Total time elapsed: " + (end - start)/1000.0 +  " seconds.");
//
//		start = System.currentTimeMillis();
//		for (int v : values) {
//			naiveMinPQ.changePriority(v, v * Math.abs(random.nextInt(5)));
//		}
//		end = System.currentTimeMillis();
//		System.out.println("NAIVE HEAP - getSmallest. Total time elapsed: " + (end - start)/1000.0 +  " seconds.\n");


	}
}
