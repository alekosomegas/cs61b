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
		this.root = new Node(median);
		buildKDTree(split(sortedPoints, median), root, 'Y');
	}
	
	private class Node {
		Point point;
		Node[] children;
		public Node(Point point) {
			this.point = point;
			children = new Node[2];
		}
	}
	private List<Point> sortBy(char by, List<Point> points) {
		List<Point> copy = points.stream().collect(Collectors.toList());
		if (by == 'X') {
			copy.sort(Comparator.comparingDouble(Point::getX));
		} else {
			copy.sort(Comparator.comparingDouble(Point::getY));
		}
		return copy;
	}
	
	private Point getMedian(List<Point> points) {
		return points.get((int) Math.floor(points.size()/2));
	}
	
	private List<Point>[] split(List<Point> square, Point median) {
		List<Point>[] subSquares = new List<>[2];
		subSquares[0] = square.subList(0, square.indexOf(median));
		subSquares[1] = square.subList(square.indexOf(median)+1, square.size());
		return subSquares;
	}
	
	private void buildKDTree(List<Point>[] squares, Node current, char sort) {
		if (squares[0].isEmpty() && squares[1].isEmpty()) return;
		for (int i = 0; i < 2; i++) {
			List<Point> currSquare = sortBy(sort, squares[i]);
			Point median = getMedian(currSquare);
			current.children[i] = new Node(median);
			List<Point>[] subSquares = split(squares[i], median);
			char nextSort = sort == 'X' ? 'Y' : 'X';
			buildKDTree(subSquares, current.children[i], nextSort);
		}
	}
	
	@Override
	public Point nearest(double x, double y) {
		return null;
	}
}
