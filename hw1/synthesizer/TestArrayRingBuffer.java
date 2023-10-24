package synthesizer;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<Integer> (10);
        assertTrue(arb.isEmpty());
        arb.enqueue(2);
        arb.enqueue(3);
        assertFalse(arb.isEmpty());
        assertEquals(2, (int)arb.dequeue());
        assertEquals(3, (int)arb.dequeue());
        assertTrue(arb.isEmpty());
        arb.enqueue(4);
        arb.enqueue(6);
        arb.enqueue(7);
        arb.enqueue(6);
        assertEquals(4, (int)arb.dequeue());
        arb.enqueue(7);
        arb.enqueue(6);
        arb.enqueue(7);
        arb.enqueue(7);
        arb.enqueue(6);
        arb.enqueue(7);
        arb.enqueue(7);
        assertTrue(arb.isFull());
        assertEquals(6, (int)arb.dequeue());
        for (int x:arb) {
            System.out.print(x + " ");
        }
        System.out.print("\n");
        for (int x:arb) {
            System.out.print(x + " ");
        }
        System.out.print("\n");
        System.out.println(arb.fillCount());
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
