package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {
	private static final Random r = new Random(500);
	@Test
	public void testNaive() {
		Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
		Point p2 = new Point(3.3, 4.4);
		Point p3 = new Point(-2.9, 4.2);
		
		NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
		Point ret = nn.nearest(3.0, 4.0); // returns p2
		assertEquals(p2, ret);
		assertEquals(p2.getX(), ret.getX(), 0);
	}
	
	@Test
	public void testBuild() {
		Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
		Point p2 = new Point(3.3, 4.4);
		Point p3 = new Point(-2.9, 4.2);
		
		KDTree nn = new KDTree(List.of(p1, p2, p3));
		Point ret = nn.nearest(3.0, 4.0); // returns p2
		assertEquals(p2, ret);
		assertEquals(p2.getX(), ret.getX(), 0);
	}
	
	@Test
	public void simpleTest() {
		Point a = new Point(2,3);
		Point b = new Point(4,2);
		Point c = new Point(4,5);
		Point d = new Point(3,3);
		Point e = new Point(1,5);
		
		KDTree kdTree = new KDTree(List.of(a,b,c,d,e));
		Point n = kdTree.nearest(0,7);
		assertEquals(e, n);
	}
	
	@Test
	public void bigTest()  {
		Random random = new Random();
		List<Point> points = new ArrayList<>();
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10000000; i++) {
			Point p = new Point(random.nextInt(), random.nextInt());
			points.add(p);
		}
		long end = System.currentTimeMillis();
		System.out.println("Total time elapsed loop: " + (end - start)/1000.0 +  " seconds.");
		
		start = System.currentTimeMillis();
		NaivePointSet naivePointSet = new NaivePointSet(points);
		end = System.currentTimeMillis();
		System.out.println("Total time elapsed Naive init: " + (end - start)/1000.0 +  " seconds.");
		
		start = System.currentTimeMillis();
		KDTree kdTree = new KDTree(points);
		end = System.currentTimeMillis();
		System.out.println("Total time elapsed KD init: " + (end - start)/1000.0 +  " seconds.");
		
		Point target = new Point(random.nextInt(), random.nextInt());
		
		start = System.currentTimeMillis();
		Point kn = kdTree.nearest(target.getX(), target.getY());
		end = System.currentTimeMillis();
		System.out.println("Total time elapsed KD: " + (end - start)/1000.0 +  " seconds.");
		
		start = System.currentTimeMillis();
		Point nn = naivePointSet.nearest(target.getX(), target.getY());
		end = System.currentTimeMillis();
		System.out.println("Total time elapsed N: " + (end - start)/1000.0 +  " seconds.");
		
		assertEquals(kn,nn);
	}
	
	@Test
	public void bigRandTest()  {
		List<Point> points = new ArrayList<>();
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			Point p = new Point(r.nextDouble(), r.nextDouble());
			points.add(p);
		}
		
		long end = System.currentTimeMillis();
		System.out.println("Total time elapsed loop: " + (end - start)/1000.0 +  " seconds.");
		
		start = System.currentTimeMillis();
		NaivePointSet naivePointSet = new NaivePointSet(points);
		end = System.currentTimeMillis();
		System.out.println("Total time elapsed Naive init: " + (end - start)/1000.0 +  " seconds.");
		
		start = System.currentTimeMillis();
		KDTree kdTree = new KDTree(points);
		end = System.currentTimeMillis();
		System.out.println("Total time elapsed KD init: " + (end - start)/1000.0 +  " seconds.");
		
		double kdTotal = 0.0;
		double nTotal = 0.0;
		for (int i = 0; i< 10000; i++) {
			Point target = new Point(r.nextDouble(), r.nextDouble());
			
			start = System.currentTimeMillis();
			Point kn = kdTree.nearest(target.getX(), target.getY());
			end = System.currentTimeMillis();
			kdTotal += (end - start)/1000.0;
			
			start = System.currentTimeMillis();
			Point nn = naivePointSet.nearest(target.getX(), target.getY());
			end = System.currentTimeMillis();
			nTotal += (end - start)/1000.0;
			
			assertEquals(nn.getX(),kn.getX(), 0.00001);
			assertEquals(nn.getY(),kn.getY(), 0.00001);
		}
		System.out.println("Total time elapsed KD: " + kdTotal +  " seconds.");
		System.out.println("Total time elapsed N: " + nTotal +  " seconds.");
		
	}
}
