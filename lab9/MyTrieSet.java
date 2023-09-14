import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyTrieSet implements TrieSet61B {
	private Node root;
	private int n;
	private class Node {
		public HashMap<Character, Node> map;
		public boolean isKey;
		char val;
		public Node() {
			map = new HashMap<>();
		}
		public Node(Character c, boolean isKey) {
			this();
			map.put(c, new Node());
			this.val = c;
			this.isKey = isKey;
		}
		public boolean isLeaf() {
			return map.isEmpty();
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
		Node curr = root;
		for (int i=0; i < prefix.length(); i++) {
			char c = prefix.charAt(i);
			curr = curr.map.get(c);
		}
		// from there look for nodes for isKey true and add to the list
		ArrayList<String> words = new ArrayList<>();
		for (Node child : curr.map.values()) {
			findWords(child, words, prefix);
		}
		return words;
	}
	
	private List<String> findWords(Node x, ArrayList<String> words, String path) {
		path = path + x.val;
		if (x.isKey) {
			words.add(path);
		}
		for (Node child : x.map.values()) {
			findWords(child, words, path);
		}
		return words;
	}
	
	@Override
	public String longestPrefixOf(String key) {
		return null;
	}
}
