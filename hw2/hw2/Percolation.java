package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF record;
    private int[][] grid; // 0 -- blocked, 1 -- open, 2 -- full
    private int sizeOpen;
    private int len;
    public Percolation(int N) { // create N-by-N grid, with all sites initially blocked
        if (N <= 0) {
            throw new IllegalArgumentException("N should be not negative!");
        }
        record = new WeightedQuickUnionUF(N * N);
        grid = new int[N][N];
        len = N;
        sizeOpen = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = 0;
            }
        }
    };

    private boolean validIndex(int row, int col) {
        if (row > len - 1 || col > len - 1 || row < 0 || col < 0) {
            return false;
        }
        return true;
    }

    public void open(int row, int col) { // open the site (row, col) if it is not open already
        if (!validIndex(row, col)) {
            throw new IndexOutOfBoundsException("Out of index");
        }
        if (isOpen(row, col)) {
            return;
        }
        if (row == 0) {
            grid[row][col] = 2;
        } else {
            grid[row][col] = 1;
        }
        sizeOpen++;
        connect(row, col);
    }

    private int convert2to1(int row, int col) {
        return row * len + col;
    }

    private int[]convert1to2(int p) {
        int x = p / len;
        int y = p % len;
        return new int[]{x, y};
    }

    private void connect(int row, int col) {
        int[] x = new int[]{1, -1, 0, 0};
        int[] y = new int[]{0, 0, 1, -1};
        for (int i = 0; i < 4; i++) {
            int newRow = row + x[i];
            int newCol = col + y[i];
            if (validIndex(newRow, newCol) && isOpen(newRow, newCol)) {
                int p = convert2to1(row, col);
                int q = convert2to1(newRow, newCol);
                p = record.find(p);
                q = record.find(q);
                if (isFull(row, col) || isFull(newRow, newCol)) {
                    int[] pos = convert1to2(p);
                    grid[pos[0]][pos[1]] = 2;
                    pos = convert1to2(q);
                    grid[pos[0]][pos[1]] = 2;
                }
                record.union(p, q);
            }
        }
    }

    public boolean isOpen(int row, int col) { // is the site (row, col) open?
        if (!validIndex(row, col)) {
            throw new IndexOutOfBoundsException("Out of index");
        }
        return grid[row][col] > 0;
    }
    public boolean isFull(int row, int col) { // is the site (row, col) full?
        if (!validIndex(row, col)) {
            throw new IndexOutOfBoundsException("Out of index");
        }
        int p = convert2to1(row, col);
        p = record.find(p);
        int[] pos = convert1to2(p);
        return grid[pos[0]][pos[1]] == 2;
    }

    public int numberOfOpenSites() { // number of open sites
        return sizeOpen;
    }

    public boolean percolates() { // does the system percolate?
        for (int i = 0; i < len; i++) {
            if (isFull(len - 1, i)) {
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args){
        return;
    }
}
