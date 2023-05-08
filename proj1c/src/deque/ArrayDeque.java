package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque<T> implements Deque<T>{
    int size;
    private int nextFirst;
    private int nextLast;
    private T[] arr;
    private final int RFACTOR = 2;

    public static void main(String[] args) {
        Deque<Integer> ad = new ArrayDeque<>();
    }

    public ArrayDeque() {
        size = 0;
        arr = (T[]) new Object[8];
        nextFirst = arr.length - 1;
        nextLast = 0;
    }

    @Override
    public void addFirst(T x) {
        // 在 nextFirst 索引处设置新值，更新 nextFirst 与 size
        if(size == arr.length) { // 数组扩容，并更新索引
            resizing(size * RFACTOR);
        }

        arr[nextFirst] = x;
        // 插入完成后左移一个索引。左移时可能会越过数组左边界，额外加 arr.length 再求模
        nextFirst  = (nextFirst - 1 + arr.length) % arr.length;
        size += 1;
    }

    @Override
    public void addLast(T x) {
        // 在 nextLast 索引处设置新值，更新 nextLast 与 size
        if(size == arr.length) { // 数组扩容，并更新索引
            resizing(size * RFACTOR);
        }
        arr[nextLast] = x;

        // 插入完成后右移一个索引，待下一次 addLast
        nextLast = (nextLast + 1) % arr.length;
        size += 1;
    }

    // 数组扩容和缩小的实现一样，将当前所有元素一次放到新数组从头开始处
    // 只是新数组容量不同
    private void resizing(int capacity) {
        T[] newArr = (T[]) new Object[capacity];
        // 不变式：从 nextFirst + 1 位置开始向右读取元素，读取 size 长度，即为按照 Deque 中顺序保存元素
        // 将读取出来的元素放到新数组的开头
        // i 原数组索引，用取模处理越界，n 控制读取次数与新数组的索引
        for(int i = (nextFirst + 1) % arr.length, n = 0; n < capacity; n+=1, i = (i + 1) % arr.length) {
            newArr[n] = arr[i];
        }

        // initiate nextFirst and nextLast
        nextFirst = newArr.length - 1;
        nextLast = arr.length;
        arr = newArr;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for(int i = (nextFirst + 1) % arr.length, n = 0; n < size; n+=1, i = (i + 1) % arr.length) {
            returnList.add(arr[i]);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        // nextFirst 右移一位，返回该位元素
        if(isEmpty()) {
            return null;
        }
        nextFirst = (nextFirst + 1) % arr.length;
        T returnValue = arr[nextFirst];
        size -= 1;

        checkResizingDown();
        return returnValue;
    }

    @Override
    public T removeLast() {
        if(isEmpty()) {
            return null;
        }
        nextLast = (nextLast - 1 + arr.length) % arr.length;
        T returnValue = arr[nextLast];
        size -= 1;

        checkResizingDown();

        return returnValue;
    }

    private void checkResizingDown() {
        if(arr.length > 16 && ((double) size / arr.length) < 0.25) {
            resizing(arr.length / RFACTOR);
        }
    }

    @Override
    public T get(int index) {
        if(index < 0 || index >= size) {
            return null;
        }
        int targetIdx = (nextFirst + 1 + index) % arr.length;
        return arr[targetIdx];
    }

    @Override
    public T getRecursive(int index) {
        return get(index);
    }

    @Override
    public Iterator<T> iterator() {
        return new ADequeIterator();
    }

    private class ADequeIterator implements Iterator<T> {
        int index;

        public ADequeIterator() {
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index != size;
        }

        @Override
        public T next() {
            int i = (nextFirst + 1 + index) % arr.length;
            index = (index + 1) % arr.length;
            return arr[i];
        }
    }
}
