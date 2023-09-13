import java.time.Period;
import java.util.*;

public class MyHashMap<K, V> implements Map61B {
	private double loadFactor;
	private int initialSize;
	private ArrayList<LinkedList<Entry>> entries;
	private HashSet<Object> keys;
	
	public MyHashMap() {
		this(16, 0.75);
	}
	
	public MyHashMap(int initialSize) {
		this(initialSize, 0.75);
	}
	
	public MyHashMap(int initialSize, double loadFactor) {
		this.initialSize = initialSize;
		this.loadFactor = loadFactor;
		entries = new ArrayList<>(initialSize);
		for (int i=0; i< initialSize; i++) {
			entries.add(new LinkedList<>());
		}
		keys = new HashSet<>();
	}
	
	
	@Override
	public void clear() {
		entries.clear();
		keys.clear();
		for (int i=0; i< initialSize; i++) {
			entries.add(new LinkedList<>());
		}
	}
	
	@Override
	public boolean containsKey(Object key) {
		return keys.contains(key);
	}
	
	@Override
	public Object get(Object key) {
		if (!containsKey(key)) return null;
		int keyHashIndex = Math.abs(key.hashCode() % entries.size());
		LinkedList<Entry> bucket = entries.get(keyHashIndex);
		return bucket.stream().filter(e -> e.key.equals(key)).findFirst().get().value;
	}
	
	@Override
	public int size() {
		int size = 0;
		for (LinkedList<Entry> bucket : entries) {
			size += bucket.size();
		}
		return size;
	}
	
	@Override
	public void put(Object key, Object value) {
		double currentLoadFactor = (double) size() / entries.size() ;
		if (currentLoadFactor > loadFactor) grow();
		int keyHashIndex = Math.abs(key.hashCode() % entries.size());
		
		LinkedList<Entry> bucket = entries.get(keyHashIndex);
		// update existing value
		if (bucket.stream().anyMatch(e -> e.key.equals(key))) {
			Entry entry = bucket.stream().filter(e -> e.key.equals(key)).findFirst().get();
			entry.value = value;
		} else {
			// add a new key-value pair
			bucket.push(new Entry(key, value));
		}
		keys.add(key);
	}
	
	@Override
	public Set keySet() {
		return keys;
	}
	
	@Override
	public Object remove(Object key) {
		if (!containsKey(key)) return null;
		int keyHashIndex = Math.abs(key.hashCode() % entries.size());
		LinkedList<Entry> bucket = entries.get(keyHashIndex);
		Entry entry = bucket.stream().filter(e -> e.key.equals(key)).findFirst().get();
		bucket.remove(entry);
		keys.remove(key);
		return entry;
	}
	
	@Override
	public Object remove(Object key, Object value) {
		return remove(key);
	}
	
	@Override
	public Iterator iterator() {
		return null;
	}
	
	private void grow() {
		MyHashMap<K, V> newMyHashMap = new MyHashMap<>(entries.size() * 2, loadFactor);
		for (Object k : keys) {
			Object v = get(k);
			newMyHashMap.put(k, v);
		}
		this.entries = newMyHashMap.entries;
	}
	
	private class Entry {
		Object key;
		Object value;
		
		Entry(Object key, Object value) {
			this.key = key;
			this.value = value;
		}
		
//		Entry get(Object k) {
//			if (k != null && k.equals(key)) {
//				return this;
//			}
//			return null;
//		}
	}
}
