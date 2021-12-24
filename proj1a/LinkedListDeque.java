public class LinkedListDeque<T> {

    private class Node {
        public Node prev;
        public T item;
        public Node next;

        public Node(Node p, T i, Node n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    private Node sentinel;
    private int size;

    /** Creates an empty linked list deque. */
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public LinkedListDeque(T x) {
        sentinel = new Node(null, null, null);
        sentinel.next = new Node(sentinel, x, sentinel);
        sentinel.prev = sentinel.next;
        size += 1;
    }
    /** Adds an item of type T to the front of the deque. */
    public void addFirst(T item) {
        Node last = sentinel.prev;
        Node oldFirst = sentinel.next;
        Node first = new Node(sentinel, item, oldFirst);

        sentinel.next = first;
        oldFirst.prev = first;
        size += 1;
    }

//    /** Adds an item of type T to the back of the deque. */
//    public void addLast(T item) {
//        ;
//    }
//
//    /** Returns true if deque is empty, false otherwise. */
//    public boolean isEmpty() {
//        ;
//    }
//
//    /** Returns the number of items in the deque. */
//    public int size() {
//        ;
//    }
//
//    /** Prints the items in the deque from first to last,
//     * separated by a space. Once all the items have been printed,
//     * print out a new line. */
//    public void printDeque() {
//        ;
//    }
//
//    /** Removes and returns the item at the front of the deque.
//     * If no such item exists, returns null. */
//    public T removeFirst() {
//        ;
//    }
//
//    /** Removes and returns the item at the back of the deque.
//     *  If no such item exists, returns null. */
//    public T removeLast() {
//        ;
//    }
//
//    /** Gets the item at the given index, where 0 is the front,
//     * 1 is the next item, and so forth. If no such item exists,
//     * returns null. Must not alter the deque! */
//    public T get(int index) {
//        ;
//    }



}
