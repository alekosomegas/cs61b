public class Test {
    public static void main(String[] args) {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        lld1.addFirst(10);
        lld1.addFirst(22);
        lld1.addFirst(32);

        LinkedListDeque<String> lld2 = new LinkedListDeque<String>("Tests");
        lld2.addFirst("t2");
        lld2.addFirst("t3");
    }
}
