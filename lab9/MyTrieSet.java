import java.util.HashMap;
import java.util.List;

public class MyTrieSet implements TrieSet61B {
	private Node root;
	private int n;
	private class Node {
		public HashMap<Character, Node> map;
		public boolean isKey;
		public Node() {
			map = new HashMap<>();
		}
		public Node(Character c, boolean isKey) {
			this();
			map.put(c, new Node());
			this.isKey = isKey;
		}
	}
	
	public MyTrieSet() {
		root = new Node();
	}
	
	@Override
	public void clear() {
		root = new Node();
	}
	
	@Override
	public boolean contains(String key) {
		if (key == null) throw new IllegalArgumentException("argument to contains() is null");
		return get(root, key, 0) != null;
	}
	
	private Node get(Node x, String key, int d) {
		if (x == null) return null;
		if (d == key.length()) return x;
		char c = key.charAt(d);
		return get(x.map.get(c), key, d+1);
	}
	
	@Override
	public void add(String key) {
		if (key == null || key.length() < 1) {
			return;
		}
		Node curr = root;
		for (int i = 0, n = key.length(); i < n; i++) {
			char c = key.charAt(i);
			if (!curr.map.containsKey(c)) {
				curr.map.put(c, new Node(c, false));
			}
			curr = curr.map.get(c);
		}
		curr.isKey = true;
	}
	
	@Override
	public List<String> keysWithPrefix(String prefix) {
		// start at root, find the next node at char prefix 0,
		// continue for prefix length
		// from there look for nodes for isKey true and add to the list
		return null;
	}
	
	@Override
	public String longestPrefixOf(String key) {
		return null;
	}
}
