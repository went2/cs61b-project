import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.Random;

import static com.google.common.truth.Truth.assertThat;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;
    private int size;

    public BSTMap() {
    }

    @Override
    public void put(K key, V value) {
        if(root == null) {
            root = new Node(key, value);
            size += 1;
        } else {
            this.putHelper(root, key, value);
        }
    }

    private Node putHelper(Node node, K key, V value) {
        if(node == null) {
            size += 1;
            return new Node(key, value);
        }
        int cmp = key.compareTo(node.key);

        if(cmp < 0) { node.left = putHelper(node.left, key, value); }
        else if(cmp > 0)    { node.right = putHelper(node.right, key, value); }
        else  { node.value = value; }

        return node;
    }

    @Override
    public V get(K key) {
        return getHelper(root, key);
    }

    private V getHelper(Node node, K key) {
        if(node == null) { return null; }
        int cmp = key.compareTo(node.key);

        if(cmp < 0) { return getHelper(node.left, key); }
        else if (cmp > 0) { return getHelper(node.right, key); }
        else { return node.value; }
    }

    @Override
    public boolean containsKey(K key) {
        Node currentNode = root;
        boolean result = false;
        while(currentNode != null) {
            int cmp = key.compareTo(currentNode.key);
            if(cmp < 0) { currentNode = currentNode.left; }
            else if(cmp > 0) { currentNode = currentNode.right; }
            else { result = true; break; }
        }
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new TreeSet<>();
        addKeysToSet(root, set);
        return set;
    }

    private void addKeysToSet(Node node, Set<K> set) {
        if(node == null) { return; }
        set.add(node.key);
        addKeysToSet(node.left, set);
        addKeysToSet(node.right, set);
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTIterator<>();
    }

    public void printInOrder() {
        printNode(root);
    }

    // 使用递归从小到大打印 BST
    private void printNode(Node node) {
        if(node == null) { return; }
        printNode(node.left);
        System.out.println(node.key);
        printNode(node.right);
    }

    private class Node {
        K key;
        V value;
        Node left;
        Node right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private class BSTIterator<K> implements Iterator<K> {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public K next() {
            return null;
        }
    }

    public static void main(String[] args) {
        BSTMap<Integer, Integer> bm = new BSTMap<>();
        Random rand = new Random();
        for(int i=0; i<20; i++) {
            bm.put(rand.nextInt(20), null);
        }
        System.out.println("size: " + bm.size());
        bm.printInOrder();
        bm.clear();
        System.out.println("size: " + bm.size());


    }
}
