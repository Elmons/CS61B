import org.junit.Test;
import org.junit.Assert.*;

public class test {
    @Test
    public void testLinkedListDeque(){
        ArrayDeque<String> deque = new ArrayDeque<String>();
        String a = "hello ", b = "world!", c = "I ", d = "am ", e = "Bajiao!";
        System.out.println("===========Test==============");
        System.out.println(deque.isEmpty());
        deque.printDeque();
        deque.addFirst(a);
        deque.printDeque();
        deque.addFirst(b);
        deque.printDeque();
        System.out.println(deque.get(2));
        deque.printDeque();
        deque.addFirst(c);
        deque.printDeque();
        deque.addFirst(d);
        deque.printDeque();
        System.out.println(deque.isEmpty());
        deque.addFirst(e);
        deque.printDeque();
        deque.addFirst(a);
        deque.printDeque();
        System.out.println(deque.get(0));
        deque.printDeque();
        deque.addLast(b);
        deque.printDeque();
        System.out.println(deque.get(2));
        deque.addLast(c);
        deque.printDeque();
        deque.addLast(e);
        deque.printDeque();
        deque.removeFirst();
        System.out.println(deque.get(4));
        deque.removeFirst();
        System.out.println(deque.get(2));
        deque.removeLast();
        deque.removeFirst();
        deque.removeLast();
        System.out.println(deque.size());
        deque.removeLast();
        deque.removeLast();
        deque.printDeque();
        deque.removeLast();
        deque.printDeque();
        deque.removeLast();
        System.out.println(deque.size());
        System.out.println(deque.isEmpty());
        // getrecursive
    }
}
