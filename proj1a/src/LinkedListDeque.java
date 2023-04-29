import java.util.List;

public class LinkedListDeque<T> implements Deque<T> {
    int size;
    Node<T> sentinel;

    public static class Node<T> {
        Node<T> prev;
        T item;
        Node<T> next;

        public Node(T item) {
            this.item = item;
        }
        public Node() {}
        public Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    public LinkedListDeque() {
        sentinel = new Node<>();
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        Node<T> newNode = new Node<>(x, sentinel, sentinel.next);

        if(size == 0) { // both first and last
            sentinel.prev = newNode;
        } else {
            sentinel.next.prev = newNode;
        }
        sentinel.next = newNode;

        size += 1;
    }

    @Override
    public void addLast(T x) {
        if(size == 0) {
            this.addFirst(x);
        } else {
            Node<T> newNode = new Node<>(x, sentinel.prev, sentinel);
            sentinel.prev.next = newNode;
            sentinel.prev = newNode;
            size += 1;
        }
    }

    @Override
    public List<T> toList() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public T removeFirst() {
        return null;
    }

    @Override
    public T removeLast() {
        return null;
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T getRecursive(int index) {
        return null;
    }

    public static void main(String[] args) {
        Deque<Integer> lld = new LinkedListDeque<>();
        lld.addLast(99);
        lld.addFirst(1);
        lld.addFirst(3);
        lld.addFirst(5);
        lld.addLast(88);
        lld.addLast(77);
        System.out.println(size);
    }
}
