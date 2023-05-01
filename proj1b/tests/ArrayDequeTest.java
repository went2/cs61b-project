import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDequeTest {
    @Test
    public void testCreateDeque() {
        Deque<Integer> dq1 = new ArrayDeque<>();
        assertWithMessage("a newly created Deque is empty")
                .that(dq1.size()).isEqualTo(0);

        assertWithMessage("isEmpty should tell true")
                .that(dq1.isEmpty()).isTrue();
    }

    @Test
    public void testAddFirst() {
        Deque<Integer> dq1 = new ArrayDeque<>();
        dq1.addFirst(1);
        assertWithMessage("addFirst once enlarge the size by 1")
                .that(dq1.size()).isEqualTo(1);
        dq1.addFirst(2);
        assertWithMessage("addFirst twice enlarge the size by 2")
                .that(dq1.size()).isEqualTo(2);
        dq1.addFirst(3);
        dq1.addFirst(4);

        assertWithMessage("addFirst makes the right order")
                .that(dq1.toList()).containsExactly(4,3,2,1).inOrder();
    }

    @Test
    public void testAddLast() {
        Deque<Integer> dq1 = new ArrayDeque<>();
        dq1.addLast(1);
        assertWithMessage("addFirst once enlarge the size by 1")
                .that(dq1.size()).isEqualTo(1);
        dq1.addLast(2);
        assertWithMessage("addFirst twice enlarge the size by 2")
                .that(dq1.size()).isEqualTo(2);
        dq1.addLast(3);
        dq1.addLast(4);

        assertWithMessage("addLast makes the right order")
                .that(dq1.toList()).containsExactly(1,2,3,4).inOrder();
    }

    @Test
    public void testAddFirstWithAddLast() {
        Deque<Integer> dq1 = new ArrayDeque<>();
        dq1.addFirst(1);
        dq1.addLast(2);
        dq1.addFirst(3);
        dq1.addLast(4);
        dq1.addFirst(5);
        dq1.addLast(6);
        dq1.addFirst(7);
        dq1.addLast(8);
        assertWithMessage("Adding 8 elements should make size 8")
                .that(dq1.size()).isEqualTo(8);
        assertWithMessage("addLast and addFirst makes the right order")
                .that(dq1.toList()).containsExactly(7,5,3,1,2,4,6,8).inOrder();
    }

    @Test
    public void testResizing() {
        Deque<Integer> dq1 = new ArrayDeque<>();
        dq1.addFirst(1);
        dq1.addLast(2);
        dq1.addFirst(3);
        dq1.addLast(4);
        dq1.addFirst(5);
        dq1.addLast(6);
        dq1.addFirst(7);
        dq1.addLast(8);

        dq1.addFirst(9);
        dq1.addLast(10);
        dq1.addFirst(11);
        dq1.addLast(12);
        assertWithMessage("Adding 12 elements should make size 12")
                .that(dq1.size()).isEqualTo(12);
        assertWithMessage("and not empty")
                .that(dq1.isEmpty()).isFalse();
        assertWithMessage("Resizing works if all the elements are in right order")
                .that(dq1.toList()).containsExactly(11,9,7,5,3,1,2,4,6,8,10,12).inOrder();
    }

    @Test
    public void testRemoveFirstAndRemoveLast() {
        Deque<Integer> dq1 = new ArrayDeque<>();
        dq1.addFirst(1);
        dq1.addLast(2);
        dq1.addFirst(3);
        dq1.addLast(4);
        dq1.addFirst(5);
        dq1.addLast(6);
        dq1.addFirst(7);
        dq1.addLast(8);
        // before resizing
        assertWithMessage("should be 7")
                .that(dq1.removeFirst()).isEqualTo(7);
        assertWithMessage("should be 8")
                .that(dq1.removeLast()).isEqualTo(8);
        assertWithMessage("should be 5")
                .that(dq1.removeFirst()).isEqualTo(5);
        assertWithMessage("should be 6")
                .that(dq1.removeLast()).isEqualTo(6);
        assertWithMessage("size should be 4")
                .that(dq1.size()).isEqualTo(4);
        dq1.removeFirst();
        dq1.removeFirst();
        dq1.removeFirst();
        dq1.removeFirst();
        assertWithMessage("Deque should be empty")
                .that(dq1.isEmpty()).isTrue();

        assertWithMessage("removeFirst from empty Deque return null")
                .that(dq1.removeFirst()).isEqualTo(null);
        assertWithMessage("removeLast from empty Deque return null")
                .that(dq1.removeLast()).isEqualTo(null);
    }

    @Test
    public void testRemoveFirstAndRemoveLast2() {
        Deque<Integer> dq1 = new ArrayDeque<>();
        dq1.addLast(1);dq1.addLast(2);dq1.addLast(3);dq1.addLast(4);
        dq1.addLast(5);dq1.addLast(6);dq1.addLast(7);dq1.addLast(8);
        dq1.addLast(9);dq1.addLast(10);

        assertWithMessage("size should be 10")
                .that(dq1.size()).isEqualTo(10);
        assertWithMessage("should be 1")
                .that(dq1.removeFirst()).isEqualTo(1);
        assertWithMessage("should be 10")
                .that(dq1.removeLast()).isEqualTo(10);
        assertWithMessage("should be 9")
                .that(dq1.removeLast()).isEqualTo(9);
        assertWithMessage("size should be 7")
                .that(dq1.size()).isEqualTo(7);

        dq1.addFirst(1);dq1.addLast(9);dq1.addLast(10);
        assertWithMessage("size should be 10 again")
                .that(dq1.size()).isEqualTo(10);
        assertWithMessage("elements should be in order")
                .that(dq1.toList()).containsExactly(1,2,3,4,5,6,7,8,9,10).inOrder();

        // resizing down
        dq1.addLast(11);dq1.addLast(12);dq1.addLast(13);dq1.addLast(14);dq1.addLast(15);
        dq1.addLast(16);dq1.addLast(17);dq1.addLast(18);dq1.addLast(19);dq1.addLast(20);
        assertWithMessage("after resizing twice, last should be 20")
                .that(dq1.get(dq1.size() - 1)).isEqualTo(20);
        assertWithMessage("after resizing twice, 17th should be 18")
                .that(dq1.get(17)).isEqualTo(18);
        assertWithMessage("after resizing twice, size should be 20")
                .that(dq1.size()).isEqualTo(20);
        dq1.removeLast();dq1.removeLast();dq1.removeLast();dq1.removeLast();dq1.removeLast();
        dq1.removeLast();dq1.removeLast();dq1.removeLast();dq1.removeLast();dq1.removeLast();
        dq1.removeLast();
        dq1.removeLast();
        dq1.removeLast();
        dq1.removeLast();
        dq1.removeLast();
        dq1.removeLast();

        assertWithMessage("elements should be in order")
                .that(dq1.toList()).containsExactly(1,2,3,4).inOrder();
    }

    @Test
    public void testGet() {
        Deque<Integer> dq1 = new ArrayDeque<>();
        dq1.addLast(1);dq1.addLast(2);dq1.addLast(3);dq1.addLast(4);
        dq1.addLast(5);dq1.addLast(6);

        assertWithMessage("should be 1")
                .that(dq1.get(0)).isEqualTo(1);
        assertWithMessage("should be 6")
                .that(dq1.get(5)).isEqualTo(6);
        assertWithMessage("should be 4")
                .that(dq1.get(3)).isEqualTo(4);

        // resizing
        dq1.addLast(7);dq1.addLast(8);dq1.addLast(9);dq1.addLast(10);
        assertWithMessage("should still be 1")
                .that(dq1.get(0)).isEqualTo(1);
        assertWithMessage("should be 10 now")
                .that(dq1.get(dq1.size() - 1)).isEqualTo(10);

        dq1.addFirst(11);
        assertWithMessage("should be 11")
                .that(dq1.get(0)).isEqualTo(11);
        assertWithMessage("size should be 11")
                .that(dq1.size()).isEqualTo(11);

        dq1.removeFirst();dq1.removeFirst();dq1.removeFirst();dq1.removeFirst();
        assertWithMessage("first should be 4")
                .that(dq1.get(0)).isEqualTo(4);
        assertWithMessage("last should be 10")
                .that(dq1.get(dq1.size() - 1)).isEqualTo(10);
        assertWithMessage("elements should be in order")
                .that(dq1.toList()).containsExactly(4,5,6,7,8,9,10).inOrder();

        assertWithMessage("out of bounds returns null")
                .that(dq1.get(7)).isEqualTo(null);
        assertWithMessage("negative index returns null")
                .that(dq1.get(-10)).isEqualTo(null);
    }

}
