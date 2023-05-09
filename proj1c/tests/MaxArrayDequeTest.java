import deque.MaxArrayDeque;
import org.junit.jupiter.api.*;
import deque.Deque;
import deque.ArrayDeque;
import deque.LinkedListDeque;

import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDequeTest {
    public static void main(String[] args) {

    }

    @Test
    public void testIntegerComparing() {
        Comparator<Integer> intCmp = (o1, o2) -> o1 - o2;
        Comparator<Integer> intCmp2 = (o1, o2) -> o2 - o1;

        MaxArrayDeque<Integer> deque1 = new MaxArrayDeque<>(intCmp);

        assertThat(deque1.max()).isEqualTo(null);

        deque1.addFirst(1);
        assertThat(deque1.max()).isEqualTo(1);
        deque1.addFirst(2);
        deque1.addFirst(-23);
        deque1.addFirst(40);

        assertThat(deque1.max()).isEqualTo(40);
        assertThat(deque1.max(intCmp2)).isEqualTo(-23);
    }

    @Test
    public void testStringComparing() {
        Comparator<String> strCmp = String::compareTo;
        MaxArrayDeque<String> deque1 = new MaxArrayDeque<>(strCmp);

        deque1.addLast("alex");
        assertThat(deque1.max()).isEqualTo("alex");

        deque1.addLast("bob");
        deque1.addLast("cindy");
        deque1.addLast("dart");
        assertThat(deque1.max()).isEqualTo("dart");
    }
}
