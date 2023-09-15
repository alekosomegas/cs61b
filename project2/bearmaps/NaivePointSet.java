package bearmaps;

import java.util.ArrayList;
import java.util.List;

public class NaivePointSet implements PointSet{
	private ArrayList<Point> points;
	
	public NaivePointSet(List<Point> points) {
		assert points.size() > 0;
		this.points = new ArrayList<>(points);
	}
	
	/**
	 * Returns the closest point to the inputted coordinates.
	 * This should take \(\theta(N)\) time where \(N\) is the number of points.
	 */
	@Override
	public Point nearest(double x, double y) {
		Point target = new Point(x, y);
		int closestIndex = 0;
		double minDistance = Double.POSITIVE_INFINITY;
		for (int i=0; i < points.size(); i++) {
			double d = Point.distance(points.get(i), target);
			if (d < minDistance) {
				minDistance = d;
				closestIndex = i;
			}
		}
		return points.get(closestIndex);
	}
	
}
