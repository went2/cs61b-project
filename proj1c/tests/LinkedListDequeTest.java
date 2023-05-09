import deque.ArrayDeque;
import deque.Deque;
import deque.LinkedListDeque;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class LinkedListDequeTest {

    public static void main(String[] args) {

    }

    @Test
    public void testIterator() {
        Deque<String> deque1 = new LinkedListDeque<>();
        for (String s1 : deque1) {
            System.out.println(s1);
        }

        deque1.addLast("front");
        deque1.addLast("middle");
        deque1.addLast("back");
        for (String s2 : deque1) {
            System.out.println(s2);
        }
        assertThat(deque1).containsExactly("front", "middle", "back");
    }

    @Test
    public void testEqualDeques() {
        Deque<String> deque1 = new LinkedListDeque<>();
        Deque<String> deque2 = new LinkedListDeque<>();

        assertWithMessage("empty deques equal")
                .that(deque1).isEqualTo(deque2);

        deque1.addLast("front");
        deque1.addLast("middle");
        deque1.addLast("back");

        deque2.addLast("front");
        deque2.addLast("middle");
        deque2.addLast("back");

        assertThat(deque1).isEqualTo(deque2);

        deque1.removeFirst();
        assertThat(deque1).isNotEqualTo(deque2);

        deque1.removeLast();
        deque1.addFirst("front");
        deque1.addLast("back");
        assertThat(deque1).isEqualTo(deque2);
    }

    @Test
    public void testToString() {
        Deque<String> deque1 = new LinkedListDeque<>();
        assertThat(deque1.toString()).isEqualTo("[]");

        deque1.addLast("front");
        deque1.addLast("middle");
        deque1.addLast("back");

        assertThat(deque1.toString()).isEqualTo("[front, middle, back]");
    }
}
