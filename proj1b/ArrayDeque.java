public class ArrayDeque<T> implements Deque<T> {
    private static final int INITSIZE = 8;
    private static final double FACTOR = 0.25;
    private int head, tail;
    private int size, maxSize;
    private T[] items;

    public ArrayDeque() {
        head = 0; //指向下一个头插位置
        tail = 1; //指向下一个尾插位置,这个地方太重要了，实现了sentinel的分离与代码统一化。
        size = 0;
        maxSize = INITSIZE;
        items = (T[]) new Object[INITSIZE];
    }

    private void upsize() {
        maxSize = 2 * maxSize;
        this.copytonew(maxSize / 2);
    }

    private void downsize() {
        maxSize = maxSize / 2;
        this.copytonew(maxSize * 2);
    }

    private void copytonew(int oldsize) {
        T[] newItems = (T[]) new Object[maxSize];
        int index = (head + 1) % oldsize;
        for (int i = 0; i < size; i++) {
            newItems[i] = items[index];
            index = (index + 1) % oldsize;
        }
        head = maxSize - 1;
        tail = size;
        items = newItems;
    }
    @Override
    public void addFirst(T item) {
        if (size == maxSize) {
            upsize();
        }
        items[head] = item;
        head = (head + maxSize - 1) % maxSize;
        size += 1;
    }
    @Override
    public void addLast(T item) {
        if (size == maxSize) {
            upsize();
        }
        items[tail] = item;
        tail = (tail + 1) % maxSize;
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
        int index = (head + 1) % maxSize;
        for (int i = 0; i < size; i++) {
            System.out.print(items[index] + " ");
            index = (index + 1) % maxSize;
        }
        System.out.print("\n");
    }

    private float calfactor() {
        return (float) size / maxSize;
    }
    @Override
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        T item = items[(head + 1) % maxSize];
        head = (head + 1) % maxSize;
        size -= 1;
        if (calfactor() < FACTOR && maxSize > INITSIZE) {
            downsize();
        }
        return item;
    }
    @Override
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        T item = items[(tail - 1 + maxSize) % maxSize];
        tail = (tail - 1 + maxSize) % maxSize;
        size -= 1;
        if (calfactor() < FACTOR && maxSize > INITSIZE) {
            downsize();
        }
        return item;
    }
    @Override
    public T get(int index) {
        return items[(head + 1 + index) % maxSize];
    }
}
