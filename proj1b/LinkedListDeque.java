public class LinkedListDeque<T> implements Deque<T> {
    private class Node {
        public T e;
        public Node pre;
        public Node next;
        public Node(T x) {
            e = x;
            pre = null;
            next = null;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        size = 0;
        sentinel = new Node(null);
        sentinel.next = sentinel;
        sentinel.pre = sentinel;
    }
    @Override
    public void addFirst(T item) {
        Node x = new Node(item);
        x.pre = sentinel;
        x.next = sentinel.next;
        sentinel.next.pre = x;
        sentinel.next = x;
        size += 1;
    }
    @Override
    public void addLast(T item) {
        Node x = new Node(item);
        x.next = sentinel;
        x.pre = sentinel.pre;
        sentinel.pre.next = x;
        sentinel.pre = x;
        size += 1;
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
    public void printDeque() {
        Node p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.e + " ");
            p = p.next;
        }
        System.out.print("\n");
    }
    @Override
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        T item = sentinel.next.e;
        sentinel.next = sentinel.next.next;
        sentinel.next.pre = sentinel;
        size -= 1;
        return item;
    }
    @Override
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        T item = sentinel.pre.e;
        sentinel.pre = sentinel.pre.pre;
        sentinel.pre.next = sentinel;
        size -= 1;
        return item;
    }
    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        Node cur = sentinel;
        while (index >= 0) {
            cur = cur.next;
            index--;
        }
        return cur.e;
    }

    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }

    private T getRecursiveHelper(int index, Node p) {
        if (index == 0) {
            return p.e;
        } else {
            return getRecursiveHelper(index - 1, p.next);
        }
    }
}
