import java.util.List;

public class LinkedListDeque<T> implements Deque<T> {
    int size;
    Node<T> sentinel;

    public static class Node<T> {
        Node<T> prev;
        T item;
        Node<T> next;
    }
    public static void main(String[] args) {
        Deque<Integer> lld = new LinkedListDeque<>();
    }

    public LinkedListDeque() {
        sentinel = new Node<>();
        size = 0;
    }

    @Override
    public void addFirst(T x) {

    }

    @Override
    public void addLast(T x) {

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
}
