package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> cmp;
    public MaxArrayDeque(Comparator<T> c) {
        cmp = c;
    }

    public T max() {
        if(this.size == 0) return null;
        T max = this.get(0);
        for(T item : this) {
            if(cmp.compare(max, item) <= 0) {
                max = item;
            }
        }
        return max;
    }

    public T max(Comparator<T> c) {
        if(this.size == 0) return null;
        T max = this.get(0);
        for(T item : this) {
            if(c.compare(max, item) <= 0) {
                max = item;
            }
        }
        return max;
    }
}
