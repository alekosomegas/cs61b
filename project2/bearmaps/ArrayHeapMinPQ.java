package bearmaps;

import java.lang.IllegalArgumentException;
import java.util.NoSuchElementException;

import static bearmaps.PrintHeapDemo.printFancyHeapDrawing;
import static bearmaps.PrintHeapDemo.printSimpleHeapDrawing;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T>{
	private PriorityNode[] data;
	private int n;
	
	public ArrayHeapMinPQ(int capacity) {
		if (capacity < 0)
			throw new IllegalArgumentException();
		data = new ArrayHeapMinPQ.PriorityNode[capacity +1];
		n = 0;
	}
	
	public ArrayHeapMinPQ() {
		this(1);
	}
	
	
	@Override
	public void add(T item, double priority) {
		// check for resize
		if (n == data.length - 1) resize(2 * data.length);
		
		// check if exists, using priority
		if (includes(1, item, priority))
			throw new IllegalArgumentException();
		// add item, increase size and percolate according to priority
		data[++n] = new PriorityNode(item, priority);
		swim(n);
	}
	
	private boolean includes(int nodeIndex, T item, double priority) {
		if (nodeIndex > n) return false;
		if (data[nodeIndex].getItem().equals(item))
			return true;
		// if priority is less no point in looking further
		if (data[nodeIndex].getPriority() < priority) {
			// check left and right child
			return includes(nodeIndex *2,    item, priority) ||
				   includes(nodeIndex *2 +1, item, priority);
		}
		return false;
	}
	
	@Override
	public boolean contains(T item) {
		for (int i=1; i <= n; i++) {
			if (item.equals(data[i].getItem()))
				return true;
		}
		return false;
	}
	
	@Override
	public T getSmallest() {
		return data[1].getItem();
	}
	
	@Override
	public T removeSmallest() {
		if (n == 0) {
			throw new NoSuchElementException();
		}
		T smallest = getSmallest();
		// swap root with last node
		swap(1, n);
		// remove last node(root) by decreasing n, anything bigger than n is not accessible
		n--;
		// check if data needs resizing
		if ((double) n / data.length < 0.25) {
			resize(n * 2);
		}
		// sink root
		sink(1);
		return smallest;
	}
	
	@Override
	public int size() {
		return n;
	}
	private int indexOf(T item, double priority, int nodeIndex) {
		if (nodeIndex > n) return -1;
		if (data[nodeIndex].getItem().equals(item)) {
			return nodeIndex;
		}
		if (data[nodeIndex].getPriority() < priority) {
			// check left child
			int l = indexOf(item, priority, nodeIndex*2);
			if (l != -1) return l;
			// check right child
			int r = indexOf(item, priority, nodeIndex*2+1);
			if (r != -1) return r;
		}
		return -1;
	}
	
	@Override
	public void changePriority(T item, double priority) {
		// find
		int nodeIndex = -1;
		for (int i=1; i < n+1; i++) {
			if (item.equals(data[i].getItem())) {
				nodeIndex = i;
			}
		}
		if (nodeIndex == -1) {
			throw new NoSuchElementException();
		}
		
		// if same priority do nothing
		double prevPriority = data[nodeIndex].getPriority();
		if (prevPriority == priority) return;
		// change priority
		data[nodeIndex].setPriority(priority);
		// sink or swim
		sink(nodeIndex);
		swim(nodeIndex);
	}
	
	private class PriorityNode implements Comparable<PriorityNode> {
		private T item;
		private double priority;
		
		PriorityNode(T e, double p) {
			this.item = e;
			this.priority = p;
		}
		
		T getItem() {
			return item;
		}
		
		double getPriority() {
			return priority;
		}
		
		void setPriority(double priority) {
			this.priority = priority;
		}
		
		@Override
		public int compareTo(PriorityNode other) {
			if (other == null) {
				return -1;
			}
			return Double.compare(this.getPriority(), other.getPriority());
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public boolean equals(Object o) {
			if (o == null || o.getClass() != this.getClass()) {
				return false;
			} else {
				return ((PriorityNode) o).getItem().equals(getItem());
			}
		}
		
		@Override
		public int hashCode() {
			return item.hashCode();
		}
		
		@Override
		public String toString() {
			return "ITEM: " + item + " , PRIORITY: " + priority;
		}
	}
	
	private void swim(int i) {
		while (i > 1 &&
			data[parent(i)].getPriority() > data[i].getPriority()) {
				swap(parent(i), i);
				i = parent(i);
		}
	}
	
	private void sink(int k) {
		// start with left child
		int j = k * 2;
		// if k not valid or has no children return
		if (j > n || k >= n) return;
		// only check left one, right does not exist
		if (j+1 > n) {
			if (data[k].getPriority() > data[j].getPriority()) {
				swap(k, j);
				return;
			}
		}
		// if both exist, find smallest from children
		double smallest = Math.min(data[j].getPriority(), data[j+1].getPriority());
		int smallestChild = data[j].getPriority() <= data[j+1].getPriority() ? j : j+1;
		if (data[k].getPriority() > smallest) {
			swap(k, smallestChild);
			sink(smallestChild);
		}
	}
	
	private void swap(int a, int b) {
		PriorityNode swap = data[a];
		data[a] = data[b];
		data[b] = swap;
	}
	
	private int parent(int i) {
		return i/2;
	}
	
	private void resize(int capacity) {
		PriorityNode[] temp = new ArrayHeapMinPQ.PriorityNode[capacity];
		for (int i=0; i <= n; i++) {
			temp[i] = data[i];
		}
		data = temp;
	}
	
	public void printHeap() {
		printFancyHeapDrawing(data);
	}
}
