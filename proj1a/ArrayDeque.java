public class ArrayDeque<T> {
    private static final int INITSIZE = 8;
    private static final double FACTOR = 0.25;
    private int head, tail;
    private int size, maxSize;
    private float utilrate;
    private T[] items;

    public ArrayDeque() {
        head = 0; //指向下一个头插位置
        tail = 1; //指向下一个尾插位置
        size = 0;
        maxSize = INITSIZE;
        items = (T[])new Object[INITSIZE];
        utilrate = 0;
    }

    public void upsize() {
        maxSize = 2 * maxSize;
        this.copytonew( maxSize / 2);
    }

    public void downsize() {
        maxSize = maxSize / 2;
        this.copytonew(maxSize * 2);
    }

    public void copytonew(int oldsize) {
        T[] newItems =(T[]) new Object[maxSize];
        int index = (head + 1) % oldsize;
        for (int i = 0 ; i < size ; i++) {
            newItems[i] = items[index];
            index = (index + 1) % oldsize;
        }
        head = maxSize - 1;
        tail = size;
        items = newItems;
    }

    public void addFirst(T item) {
        if (size == maxSize) {
            upsize();
        }
        items[head] = item;
        if(head == tail){
            tail = (tail + 1) % maxSize;
        }
        head = (head + maxSize - 1) % maxSize;
        size += 1;
    }

    public void addLast(T item) {
        if (size == maxSize) {
            upsize();
        }
        items[tail] = item;
        if(head == tail){
            head = (head + maxSize - 1) % maxSize;
        }
        tail = (tail + 1) % maxSize;
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int index = (head + 1) % maxSize;
        for (int i = 0 ; i < size ; i++) {
            System.out.print(items[index] + " ");
            index = (index + 1) % maxSize;
        }
        System.out.print("\n");
    }

    public float calfactor(){
        return (float)size/maxSize;
    }

    public T removeFirst() {
        T item = items[(head + 1) % maxSize];
        head = (head + 1) % maxSize;
        size -= 1;
        if (calfactor() < FACTOR && maxSize > INITSIZE) {
            downsize();
        }
        return item;
    }

    public T removeLast() {
        T item = items[(tail - 1) % maxSize];
        tail = (tail - 1) % maxSize;
        size -= 1;
        if (calfactor() < FACTOR && maxSize > INITSIZE) {
            downsize();
        }
        return item;
    }

    public T get(int index) {
        return items[(head + 1 + index) % maxSize];
    }
}
