package deque;

import java.util.ArrayList;
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
        List<T> returnList = new ArrayList<>();
        if(size == 0) {
            return returnList;
        }
        Node<T> p = sentinel.next;
        while(p != sentinel) {
            returnList.add(p.item);
            p = p.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if(size == 0) {
            return null;
        }
        T item = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return item;
    }

    @Override
    public T removeLast() {
        if(size == 0) {
            return null;
        }
        T item = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size -= 1;
        return item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        Node<T> p = sentinel;
        for(int i=0; i<=index; i++) {
            p = p.next;
        }
        return p.item;
    }

    @Override
    public T getRecursive(int index) {
        if(index < 0 || index >= size) {
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }

    private T getRecursiveHelper(int index, Node<T> p) {
        if(index == 0) {
            return p.item;
        }
        index -= 1;
        return getRecursiveHelper(index, p.next);
    }

    public static void main(String[] args) {

    }
}

