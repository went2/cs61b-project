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
        // 需处理数组容量扩充与越界
        if(size == arr.length) {
            resizing(size * RFACTOR);
        } else {
            arr[nextFirst] = x;
        }
        // 向左移动索引，越过数组左边界时，会出现-1，可额外加 arr.length 再求模
        nextFirst  = (nextFirst - 1 + arr.length) % arr.length;
        size += 1;
    }

    @Override
    public void addLast(T x) {
        // 在 nextLast 索引处设置新值，更新 nextLast 与 size
        // 需处理数组容量扩充与越界
        if(size == arr.length) {
            resizing(size * RFACTOR);
        } else {
            arr[nextLast] = x;
        }
        // 使用求模处理数组索引越右边界
        nextLast = (nextLast + 1) % arr.length;
        size += 1;
    }

    private void resizing(int capacity) {
        T[] newArr = (T[]) new Object[capacity];
        for(int i = (nextFirst + 1) % arr.length, n = 0; n < arr.length; n+=1, i = (i + 1) % arr.length) {
            newArr[n] = arr[i];
        }
        arr = newArr;
        // initiate nextFirst and nextLast
        nextFirst = arr.length - 1;
        nextLast = arr.length + 1;
    }

    @Override
    public List<T> toList() {
        return null;
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
        size -= 1;
        return arr[nextFirst];
    }

    @Override
    public T removeLast() {
        if(isEmpty()) {
            return null;
        }
        nextLast = (nextLast - 1 + arr.length) % arr.length;
        size -= 1;
        return arr[nextLast];
    }

    @Override
    public T get(int index) {
        return null;
    }
}
