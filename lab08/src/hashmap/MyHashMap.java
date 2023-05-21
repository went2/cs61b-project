package hashmap;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Simon Fisher
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Collection<Node>[] buckets;
    private int N;
    private int capacity = 16;
    private double loadFactor = 0.75;

    /** Constructors */
    public MyHashMap() {
        buckets = createTable(capacity);
        initBuckets(buckets);
    }

    public MyHashMap(int initialCapacity) {
        capacity = initialCapacity;
        buckets = createTable(capacity);
        initBuckets(buckets);
    }

    /**
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.capacity = initialCapacity;
        this.loadFactor = loadFactor;
        buckets = createTable(capacity);
        initBuckets(buckets);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection
     *
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table.
     * This table can be an array of Collection objects
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    //
    private void initBuckets(Collection<Node>[] table) {
        // init each bucket
        int length = table.length;
        for(int i=0; i<length; i++) {
            table[i] = createBucket();
        }
    }

    @Override
    public void put(K key, V value) {

        int index = getIndexFromKey(key);
        Collection<Node> collection = buckets[index];
        for(Node node : collection) {
            if(node.key.equals(key)) { // update
                node.value = value;
                return;
            }
        }
        // insert a new key
        collection.add(createNode(key, value));
        N += 1;

        if(isExceedCapacity()) {
            resizing();
        }
    }

    // resize the array and rehash all keys
    private void resizing() {
        // resizing
        capacity = capacity * 2;
        Collection<Node>[] newBuckets = createTable(capacity);
        initBuckets(newBuckets);

        // rehashing all keys and put them in new buckets
        for (Collection<Node> collection : buckets) {
            for (Node node : collection) {
                int index = getIndexFromKey(node.key);
                newBuckets[index].add(new Node(node.key, node.value));
            }
        }
        buckets = newBuckets;
    }

    @Override
    public V get(K key) {
        int index = getIndexFromKey(key);
        Collection<Node> bucket = buckets[index];
        for(Node node : bucket) {
            if(node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    // return the index of buckets for target key
    private int getIndexFromKey(K key) {
        return Math.floorMod(key.hashCode(), capacity);
    }

    private boolean isExceedCapacity() {
        int M = buckets.length;
        return (double) N / M <= loadFactor;
    }

    @Override
    public boolean containsKey(K key) {
        int index = getIndexFromKey(key);
        Collection<Node> bucket = buckets[index];

        for(Node node : bucket) {
            if(node.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public void clear() {
        N = 0;
        buckets = createTable(capacity);
        initBuckets(buckets);
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
