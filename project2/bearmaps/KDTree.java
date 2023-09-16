package bearmaps;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KDTree implements PointSet {
	Node root;
	
	public KDTree(@NotNull List<Point> points) {
		assert points.size() > 0;
		List<Point> sortedPoints = sortBy('X', points);
		Point median = getMedian(sortedPoints);
		this.root = new Node(median, 'X');
		buildKDTree(split(sortedPoints, median), root, 'Y');
	}
	
	private class Node {
		Point point;
		Node[] children;
		char axis;
		public Node(Point point, char axis) {
			this.point = point;
			this.axis = axis;
			children = new Node[2];
		}
	}
	private List<Point> sortBy(char by, List<Point> points) {
		List<Point> copy = points.stream().collect(Collectors.toList());
		if (by == 'X') {
			points.sort(Comparator.comparingDouble(Point::getX));
		} else {
			points.sort(Comparator.comparingDouble(Point::getY));
		}
		return copy;
	}
	
	private Point getMedian(List<Point> points) {
		if (points.size() == 1) return points.get(0);
		return points.get((int) Math.floor(points.size()/2) -1) ;
	}
	
	private List<Point>[] split(List<Point> square, Point median) {
		List<Point>[] subSquares = new List[2];
		subSquares[0] = square.subList(0, square.indexOf(median));
		subSquares[1] = square.subList(square.indexOf(median)+1, square.size());
		return subSquares;
	}
	
	private void buildKDTree(List<Point>[] squares, Node current, char sort) {
		if (squares[0].isEmpty() && squares[1].isEmpty()) return;
		for (int i = 0; i < 2; i++) {
			if (squares[i].isEmpty()) continue;
			List<Point> currSquare = sortBy(sort, squares[i]);
			Point median = getMedian(currSquare);
			current.children[i] = new Node(median, sort);
			List<Point>[] subSquares = split(squares[i], median);
			char nextSort = sort == 'X' ? 'Y' : 'X';
			buildKDTree(subSquares, current.children[i], nextSort);
		}
	}
	
	@Override
	public Point nearest(double x, double y) {
		Point goal = new Point(x,y);
		Node best = nearest(root, goal, root);
		return best.point;
	}

	// index 0 is good, 1 is bad
	private Node[] getSides(Node current, Point goal) {
		char axis = current.axis;
		int good, bad;
		Node[] sides = new Node[2];
		if (axis == 'X') {
			good = goal.getX() <= current.point.getX() ? 0 : 1;
		} else {
			good = goal.getY() <= current.point.getY() ? 0 : 1;
		}
		bad = good == 1 ? 0 : 1;
		sides[0] = current.children[good];
		sides[1] = current.children[bad];
		return sides;
	}
	
	private boolean isWorthCheckingBadSide(Node current, Point goal, double bestDistance) {
		char axis = current.axis;
		if (axis == 'X') {
			return Math.pow(Math.abs(goal.getX() - current.point.getX()),2) <= bestDistance ;
		} else {
			return Math.pow(Math.abs(goal.getY() - current.point.getY()),2) <= bestDistance;
		}
	}
	private Node nearest(Node current, Point goal, Node best) {
		if (current == null) return best;
		double best_distance = Point.distance(best.point, goal);
		if (Point.distance(current.point, goal) < best_distance) {
			best = current;
			best_distance = Point.distance(current.point, goal);
		}
		Node[] sides = getSides(current, goal);
		Node goodSide = sides[0];
		best = nearest(goodSide, goal, best);
		if (isWorthCheckingBadSide(current, goal, best_distance)) {
			Node badSide = sides[1];
			best = nearest(badSide, goal, best);
		}
		return best;
	}
}
